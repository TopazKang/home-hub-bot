package com.topazkang.homehubbot.bridge;

import com.topazkang.homehubbot.discord.join.JoinTicket;
import com.topazkang.homehubbot.discord.monitor.NodeInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.Optional;

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

    public Optional<NodeInfo> getStatusInfo() {
        try {
            NodeInfo info = restClient.get()
                    .uri("/node/info")
                    .retrieve()
                    .body(NodeInfo.class);

            return Optional.ofNullable(info);

        } catch (RestClientException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
