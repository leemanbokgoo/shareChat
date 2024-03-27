package shop.com.shareChat.domain.shartime;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.mypage.MypageResDto;
import shop.com.shareChat.dto.sharetime.ShareTimeListResDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PrimitiveIterator;

import static shop.com.shareChat.domain.shartime.QSharetime.sharetime;

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
                .where(sharetime.user.eq(user))
                .fetch();

    }
}
