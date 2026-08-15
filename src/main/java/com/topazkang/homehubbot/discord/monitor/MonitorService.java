package com.topazkang.homehubbot.discord.monitor;

import com.topazkang.homehubbot.bridge.BridgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonitorService {

    private final BridgeService bridgeService;

    public NodeInfo getStatusInfo() {
        return bridgeService.getStatusInfo();
    }

}
