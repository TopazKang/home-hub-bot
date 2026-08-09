package com.topazkang.homehubbot.discord;


import com.topazkang.homehubbot.discord.join.JoinService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordCommandHandler extends ListenerAdapter {

    private final JoinService joinService;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        switch(event.getName()){
            case "join" ->
                event.reply(joinService.createJoinLink(event.getUser().getId(), event.getUser().getName())).queue();
            case "status" ->
                event.reply("status reply").queue();
        }
    }
}
