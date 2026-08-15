package com.topazkang.homehubbot.discord;


import com.topazkang.homehubbot.discord.join.JoinService;
import com.topazkang.homehubbot.discord.monitor.MonitorService;
import com.topazkang.homehubbot.discord.monitor.NodeInfo;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DiscordCommandHandler extends ListenerAdapter {

    private final JoinService joinService;
    private final MonitorService monitorService;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        switch(event.getName()){
            case "join" ->
                event.reply(joinService.createJoinLink(event.getUser().getId(), event.getUser().getName())).queue();
            case "status" -> {
                System.out.println("1. status start");

                event.deferReply().queue();
                System.out.println("2. deferred");

                Optional<NodeInfo> info = monitorService.getStatusInfo();
                System.out.println("3. info = " + info);

                String message = info
                        .map(node -> """
                    접속자 수: %d
                    현재 프레임: %d
                    평균 프레임: %.2f
                    실행 시간: %d
                    """.formatted(
                                node.playerCount(),
                                node.serverFps(),
                                node.averageFps(),
                                node.uptime()
                        ))
                        .orElse("/join을 통해 서버를 띄워주세요.");

                System.out.println("4. message = " + message);

                event.getHook()
                        .editOriginal(message)
                        .queue(
                                success -> System.out.println("5. discord reply success"),
                                error -> error.printStackTrace()
                        );
            }
        }
    }
}
