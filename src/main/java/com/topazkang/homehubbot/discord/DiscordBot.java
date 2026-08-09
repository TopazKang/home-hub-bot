package com.topazkang.homehubbot.discord;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordBot {

    // Discord 슬래시 명령어 호출용 의존성 주입
    private final DiscordCommandRegistry commandRegistry;
    private final DiscordCommandHandler commandHandler;

    @Value("${discord.token}")
    private String token;

    private JDA jda;

    @PostConstruct
    public void start() throws InterruptedException{

        jda = JDABuilder.createDefault(token)
                .addEventListeners(commandHandler)
                .build();

        jda.awaitReady();

        commandRegistry.register(jda);
    }
}
