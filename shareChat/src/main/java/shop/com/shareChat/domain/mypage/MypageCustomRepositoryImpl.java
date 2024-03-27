package shop.com.shareChat.domain.mypage;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.dto.mypage.MypageResDto;

import static shop.com.shareChat.domain.mypage.QMypage.mypage;

@Repository
@RequiredArgsConstructor
public class MypageCustomRepositoryImpl implements MypageCustomRepository {
    private final JPAQueryFactory query;

    @Override
    public MypageResDto getMypage(User user) {
        MypageResDto resDto = query
                .select(
                        Projections.constructor(
                                MypageResDto.class,
                                mypage.id,
                                mypage.job,
                                mypage.title,
                                mypage.intro,
                                mypage.occupation
                        )
                ).from(mypage)
                .where(mypage.user.eq(user))
                .fetchOne();
        return resDto;
    }
}
