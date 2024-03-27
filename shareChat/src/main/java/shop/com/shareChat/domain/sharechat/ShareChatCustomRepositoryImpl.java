package shop.com.shareChat.domain.sharechat;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.sharechat.ShareChaListResDto;
import shop.com.shareChat.dto.sharechat.ShareChatMyListResDto;

import java.time.LocalDateTime;
import java.util.List;

import static shop.com.shareChat.domain.sharechat.QSharechat.sharechat;
import static shop.com.shareChat.domain.userSharchat.QUserSharechat.userSharechat;
import static shop.com.shareChat.domain.mypage.QMypage.mypage;

@RequiredArgsConstructor
@Repository
public class ShareChatCustomRepositoryImpl implements ShareChatCustomRepository{
    private final JPAQueryFactory query;

    @Override
    public List<ShareChaListResDto> getTimeByDayforWeek(Long userId, LocalDateTime date, int state) {
        BooleanBuilder builder = getFilter( date, state);
        return query.select(
                        Projections.constructor(
                                ShareChaListResDto.class,
                                sharechat.startTime,
                                sharechat.endTime,
                                sharechat.date
                        )
                ).from(sharechat)
                .where(builder)
                .fetch();

    }

    @Override
    public List<ShareChatMyListResDto> getMyList(User user, int state) {
        return query.select(
                Projections.constructor(
                        ShareChatMyListResDto.class,
                        sharechat.startTime,
                        sharechat.endTime,
                        sharechat.date,
                        userSharechat.receiver.nickname,
                        mypage.title
                )
        ).from(sharechat)
                .leftJoin( sharechat, userSharechat.sharechat)
                .leftJoin( mypage.user, userSharechat.receiver)
                .where(sharechat.state.eq(state))
                .fetch();
    }

    public BooleanBuilder getFilter( LocalDateTime date, int state){

        BooleanBuilder builder = new BooleanBuilder();
        builder.and( QSharechat.sharechat.date.eq(date));

        if ( state != 0 && state !=2 ) { // 예약 취소, 예약 대기
            builder.and( QSharechat.sharechat.state.eq( state));
        }
        return builder;
    }

}
