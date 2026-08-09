package com.topazkang.homehubbot.discord.join;

import java.time.LocalDateTime;

public record JoinTicket(String userId, String userName, LocalDateTime expiredAt) {
}
