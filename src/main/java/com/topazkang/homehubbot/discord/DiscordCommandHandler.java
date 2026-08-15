package com.topazkang.homehubbot.discord;


import com.topazkang.homehubbot.discord.join.JoinService;
import com.topazkang.homehubbot.discord.monitor.MonitorService;
import com.topazkang.homehubbot.discord.monitor.NodeInfo;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

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
                NodeInfo info = monitorService.getStatusInfo();

                String message = null;
                if (info != null) {
                    message = """
                            접속자 수: %d
                            현재 프레임: %d
                            평균 프레임: %d
                            실행 시간: $d
                            """.formatted(
                            info.playerCount(),
                            info.serverFps(),
                            info.averageFps(),
                            info.uptime()
                    );
                }
                else{
                    message = "/join을 통해 서버를 띄워주세요.";
                }

                event.reply(message).queue();
            }
        }
    }
}
