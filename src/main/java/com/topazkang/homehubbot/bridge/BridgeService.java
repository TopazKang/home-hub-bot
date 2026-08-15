package com.topazkang.homehubbot.bridge;

import com.topazkang.homehubbot.discord.join.JoinTicket;
import com.topazkang.homehubbot.discord.monitor.NodeInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class BridgeService {

    private final RestClient restClient;

    public BridgeService(@Value("${agent.link}") String baseUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public void joinNode(String ip, JoinTicket ticket) {

        Map<String, Object> request = Map.of(
                "userId", ticket.userId(),
                "userName", ticket.userName(),
                "ip", ip
        );

        restClient.post()
                .uri("/node/join")
                .body(request)
                .retrieve()
                .toBodilessEntity();
    }

    public NodeInfo getStatusInfo() {
        return restClient.get()
                .uri("/node/info")
                .retrieve()
                .body(NodeInfo.class);
    }
}
