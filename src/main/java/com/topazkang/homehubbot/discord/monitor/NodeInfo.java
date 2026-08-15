package com.topazkang.homehubbot.discord.monitor;

public record NodeInfo(int playerCount,
                       int serverFps,
                       double averageFps,
                       long uptime) {

}
