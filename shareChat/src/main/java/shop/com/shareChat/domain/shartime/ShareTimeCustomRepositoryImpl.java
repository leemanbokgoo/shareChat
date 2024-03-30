package shop.com.shareChat.domain.shartime;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.sharechat.ShareChaListResDto;
import shop.com.shareChat.dto.sharetime.ShareTimeListResDto;

import java.time.LocalDate;
import java.util.List;

import static shop.com.shareChat.domain.sharechat.QSharechat.sharechat;
import static shop.com.shareChat.domain.shartime.QSharetime.sharetime;
import static shop.com.shareChat.domain.userSharchat.QUserSharechat.userSharechat;

@RequiredArgsConstructor
@Repository
public class ShareTimeCustomRepositoryImpl implements ShareTimeCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<ShareTimeListResDto> getList(User user) {
        return query.select(
                        Projections.constructor(
                                ShareTimeListResDto.class,
                                sharetime.startTime,
                                sharetime.endTime,
                                sharetime.shareChatTime,
                                sharetime.day
                        )
                ).from(sharetime)
                .leftJoin(userSharechat.sharechat, sharechat)
                .fetchJoin()
                .where(sharetime.user.eq(user))
                .fetch();

    }

    @Override
    public  List<ShareChaListResDto> getTimeByDayforWeek(User user, DayOfWeek day) {
        return query.select(
                        Projections.constructor(
                                ShareChaListResDto.class,
                                sharetime.startTime,
                                sharetime.endTime,
                                sharetime.shareChatTime
                        )
                ).from(sharetime)
                .where(sharetime.user.eq(user), sharetime.day.eq(day))
                .fetch();
    }



}
