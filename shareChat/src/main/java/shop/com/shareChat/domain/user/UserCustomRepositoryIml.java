package shop.com.shareChat.domain.user;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import shop.com.shareChat.dto.user.UserListResDto;
import shop.com.shareChat.dto.user.UserResDto;
import shop.com.shareChat.dto.user.UserSearchReqDto;

import java.util.List;

import static shop.com.shareChat.domain.user.QUser.user;
import static shop.com.shareChat.domain.mypage.QMypage.mypage;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryIml implements UserCustomRepository{
    private final JPAQueryFactory query;

    @Override
    public List<UserListResDto> getSearchUser(Pageable pageable, UserSearchReqDto searchRequestDto) {
        BooleanBuilder builder = getFilter(searchRequestDto);

        List<UserListResDto> list = query.select(
                        Projections.constructor(
                                UserListResDto.class,
                                user.nickname,
                                mypage.job,
                                mypage.career,
                                mypage.intro,
                                mypage.occupation
                        )
                ).from(mypage)
                .where( builder)
                .leftJoin(mypage.user, user)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return list;
    }

    @Override
    public Long getCount(Pageable pageable, UserSearchReqDto searchRequestDto) {
        BooleanBuilder builder = getFilter(searchRequestDto);
        Long count = query
                .select(user.count())
                .from(mypage)
                .leftJoin(mypage.user, user)
                .where(builder)
                .fetchOne();
        return count;
    }

    // 유저 목록 조회
    @Override
    public UserResDto getUser(Long userId) {
        UserResDto userResDto = query
            .select(
                    Projections.constructor(
                            UserResDto.class,
                            user.id,
                            user.nickname,
                            mypage.title,
                            mypage.job,
                            mypage.career,
                            mypage.intro
                    )
            )
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();

        return userResDto;
    }

    public BooleanBuilder getFilter(UserSearchReqDto dto){

        BooleanBuilder builder = new BooleanBuilder();
        builder.and( mypage.state.eq(dto.isState())); // 'state'가 1인 경우만 선택
        BooleanBuilder occupationBuilder = new BooleanBuilder();

        if (dto.getOccupation() != null && dto.getOccupation().length() != 0) {
            String[] occupationArr = dto.getOccupation().split(",");
            for (String occupation : occupationArr) {
                System.out.println(occupation);
                occupationBuilder.or( mypage.occupation.eq(Integer.parseInt(occupation)));
            }

        }

        if (dto.getSearch() != null) {
            String search = "%" + dto.getSearch() + "%";
            occupationBuilder
                    .or(mypage.user.nickname.like(search))
                    .or(mypage.job.like(search))
                    .or(mypage.title.like(search));
            builder.and(occupationBuilder);
        }

        return builder;
    }
}
