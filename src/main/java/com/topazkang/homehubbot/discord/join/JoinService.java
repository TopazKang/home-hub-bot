package com.topazkang.homehubbot.discord.join;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class JoinService {

    @Value("${app.baselink}")
    private String baseLink;

    private final Map<String, JoinTicket> joinTokenCache
            = new ConcurrentHashMap<>();

    /**
     * join처리를 위한 1회용 링크 발행 메서드
     * @param discordUserId 디스코드 user id
     * @param discordUserName 디스코드 user 명
     * @return baselink/join/토큰 형태의 링크
     */
    public String createJoinLink(String discordUserId, String discordUserName) {

        // 토큰
        String token = UUID.randomUUID().toString();
        // 티켓
        JoinTicket ticket = new JoinTicket(discordUserId,   discordUserName, LocalDateTime.now().plusMinutes(5));

        // 캐시
        joinTokenCache.put(token,ticket);

        // 링크 발행
        String link = baseLink+"join/"+token;

        return link;
    }

    /**
     * 사용자의 링크 클릭 후, 토큰을 기반으로 join처리 메서드
     * @param token 사용자 접속 링크에서 추출한 토큰
     * @param ip 접속 사용자의 고유 ip
     */
    public void processJoinLink(String token, String ip){
        if (token == null){
            throw new RuntimeException("토큰 없음");
        }

        JoinTicket ticket = validateJoinLink(token);

        System.out.println(ticket+"/"+ip);
        //TODO: 브릿지 메서드 추가 예정
    }

    /**
     * 토큰을 기반으로 티켓을 추출하여 검증 후 반환하는 메서드
     * @param token 사용자가 접속한 링크의 토큰
     * @return 추출한 ticket
     */
    public JoinTicket validateJoinLink(String token){

        JoinTicket ticket = joinTokenCache.get(token);

        if (ticket == null){
            throw new RuntimeException("유효하지 않은 토큰");
        }

        if (ticket.expiredAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("사용 기한 초과");
        }

        // 검증 후 반환 전에 캐시에서 티켓 제거
        joinTokenCache.remove(token);

        return ticket;
    }
}
