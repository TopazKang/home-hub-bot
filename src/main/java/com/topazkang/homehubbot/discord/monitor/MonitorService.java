package com.topazkang.homehubbot.discord.monitor;

import com.topazkang.homehubbot.bridge.BridgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private final BridgeService bridgeService;

    public Optional<NodeInfo> getStatusInfo() {
        return bridgeService.getStatusInfo();
    }

}
