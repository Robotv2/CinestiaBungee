package fr.robotv2.cinestiabungee.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.plugin.Command;

public class BoutiqueCommand extends Command {

    private final BaseComponent message;
    private final TextComponent empty;

    public BoutiqueCommand() {
        super("boutique", null, "store", "buy");

        empty = new TextComponent(" ");
        TextComponent text = new TextComponent("Cliquez ici pour vous rendre sur la boutique.");
        text.setColor(ChatColor.YELLOW);
        text.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://store.cinestia.fr"));
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Pour vous rendre sur la boutique").color(ChatColor.WHITE).create()));
        message = text;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(empty);
        sender.sendMessage(message);
        sender.sendMessage(empty);
    }
}
