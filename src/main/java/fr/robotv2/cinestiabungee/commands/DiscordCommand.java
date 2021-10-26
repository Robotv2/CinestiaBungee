package fr.robotv2.cinestiabungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.plugin.Command;

public class DiscordCommand extends Command {

    private final BaseComponent message;
    private final TextComponent empty;

    public DiscordCommand() {
        super("discord");

        empty = new TextComponent(" ");
        TextComponent text = new TextComponent("Cliquez ici pour rejoindre le serveur discord.");
        text.setColor(ChatColor.AQUA);
        text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/Bq5CeUZYfv"));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Rejoindre le serveur discord").color(ChatColor.WHITE).create()));
        message = text;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(empty);
        sender.sendMessage(message);
        sender.sendMessage(empty);
    }
}
