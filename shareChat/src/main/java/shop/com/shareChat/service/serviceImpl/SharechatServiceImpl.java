package shop.com.shareChat.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.com.shareChat.domain.sharechat.ShareChatRepository;
import shop.com.shareChat.domain.sharechat.Sharechat;
import shop.com.shareChat.domain.shartime.ShareTimeRepository;
import shop.com.shareChat.domain.user.DayOfWeek;
import shop.com.shareChat.domain.user.User;
import shop.com.shareChat.domain.user.UserRepository;
import shop.com.shareChat.domain.userSharchat.UserSharechat;
import shop.com.shareChat.domain.userSharchat.UserSharechatRepository;
import shop.com.shareChat.dto.sharechat.*;
import shop.com.shareChat.dto.usersharechat.UserShareChatReqDto;
import shop.com.shareChat.ex.CustomApiException;
import shop.com.shareChat.ex.ErrorCode;
import shop.com.shareChat.service.SharechatService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SharechatServiceImpl implements SharechatService {

    private final ShareChatRepository shareChatRepository;
    private final ShareTimeRepository shareTimeRepository;
    private final UserRepository userRepository;
    private final UserSharechatRepository userSharechatRepository;

    @Transactional
    @Override
    public ShareChatResDto save(ShareChatRepDto repDto, String receiverName, Long userId) {

        Sharechat sharechat = shareChatRepository.save(repDto.toEntity());

        if( sharechat != null ){

            // 중간 테이블 저장
            User receiver = userRepository.findByUsername(receiverName)
                    .orElseThrow(()-> new CustomApiException(ErrorCode.USER_NOT_FOUND));

            User user = userRepository.findById(userId)
                    .orElseThrow(()-> new CustomApiException(ErrorCode.USER_NOT_FOUND));

            UserShareChatReqDto userShareChatReqDto = UserShareChatReqDto.builder()
                            .user(user)
                            .receiver(receiver)
                            .sharechat(sharechat)
                            .build();
            userSharechatRepository.save(userShareChatReqDto.toEntity());
        }

        return new ShareChatResDto(sharechat);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Map<String, String>> getList(Long userId, DayOfWeek day, LocalDate date) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.USER_NOT_FOUND));

        // 해당 요일의 쉐어챗 날짜 설정
        List<ShareChaListResDto> allShareChatTime = shareTimeRepository.getTimeByDayforWeek(user, day);

        if ( !allShareChatTime.isEmpty() ){

            // 쉐어챗 1회당 시간
            int timePerSession = allShareChatTime.get(0).getShareChatTime();

            // 해당 멘토링 날짜의 신청된 멘토링 시간 조회
            List<ShareChaListResDto> getDayOfShareChatList = shareChatRepository.getDayOfShareCht( user, date );
            // 해당 시간대를 시간별로 쪼갬 및 타입변환
            List<Map<String, String>> allShareTime = splitTime(allShareChatTime ,timePerSession);
            List<Map<String, String>> shareChat = splitTime(getDayOfShareChatList ,timePerSession);

            // set 으로 변환
            Set<Map<String, String>> setAllShareTime  = new LinkedHashSet<>(allShareTime);
            Set<Map<String, String>> setShareChat = new LinkedHashSet<>(shareChat);

            // 중복 값 삭제
            setAllShareTime.removeAll(setShareChat);

            // 중복값 삭제 = 신청되지않은 멘토링만 남았다는 뜻.
            for (Map<String, String> map : setAllShareTime) {
                map.put("state", "1");
            }

            // 기존 멘토링 set에 값 추가
            for (Map<String, String> map : setShareChat) {
                map.put("state", "0");
            }

            setAllShareTime.addAll(setShareChat);
            List<Map<String, String>> list = new ArrayList<>(setAllShareTime);

            return list;
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public List<ShareChatMyListResDto> getMyList(String username, int state) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new CustomApiException(ErrorCode.USER_NOT_FOUND));
        return shareChatRepository.getMyList(user,state);
    }
    @Transactional
    @Override
    public boolean deleteShareChat(Long shareChatId) {

        Sharechat sharechat = shareChatRepository.findById(shareChatId)
                .orElseThrow(()-> new CustomApiException(ErrorCode.SHARECHAT_NOT_FOUND));

        UserSharechat userSharechat = userSharechatRepository.findBySharechat(sharechat)
                .orElseThrow(()-> new CustomApiException(ErrorCode.SHARECHAT_NOT_FOUND));

        userSharechatRepository.delete(userSharechat);
        shareChatRepository.delete(sharechat);
        return true;
    }

    // 쉐어챗 설정 시간 -> 쉐어챗 시간대로 나누는 함수
    public List<Map<String, String>> splitTime(List<ShareChaListResDto> responses, int mentoringTime ){

        List<Map<String,String>> mentorTime = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for ( ShareChaListResDto item : responses) {

            //현재날짜와 startTime을 이용해서 yy-mm-dd hh-mm 형식 맞춤
            LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), item.getStartTime());

            // starTime이 endTim보다 이전인 동안 while
            while (startTime.isBefore(LocalDateTime.of(LocalDate.now(), item.getEndTime()))) {
                LocalDateTime endTime = startTime.plusMinutes(mentoringTime);
                Map<String, String> map = new HashMap<>();
                map.put("startTime", startTime.format(formatter));
                map.put("endTime", endTime.format(formatter));
                // 1회당 시간만큼 더하기 //초기화
                startTime = endTime;
                mentorTime.add(map);
            }

            // 마지막 시간대까지 추가
            Map<String, String> map = new HashMap<>();

            LocalTime endTime = item.getEndTime();
            LocalTime endStatTime = endTime.minusMinutes(mentoringTime);

            map.put("startTime", endStatTime.format(formatter));
            map.put("endTime", endTime.format(formatter));

            mentorTime.add(map);

        }

        return mentorTime;
    }
}
