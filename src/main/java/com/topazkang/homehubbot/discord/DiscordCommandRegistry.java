package com.topazkang.homehubbot.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

@Component
public class DiscordCommandRegistry {
    public void register(JDA jda){
        jda.updateCommands()
                .addCommands(
                        Commands.slash("join","게임 서버에 접속"),
                        Commands.slash("status", "게임 노드 상태를 조회")
                ).queue();
    }
}
