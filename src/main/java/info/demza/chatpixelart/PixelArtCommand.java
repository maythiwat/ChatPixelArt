package info.demza.chatpixelart;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PixelArtCommand implements CommandExecutor {

    public void printException(CommandSender sender, PixelArtException exception) {
        sender.sendMessage(ChatColor.RED + exception.getMessage());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (ChatPixelArt.getInstance().getConfig().getBoolean("require-op") && !sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return false;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("show")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "/pixelart show [image-name]");
                return false;
            }
            if (!ChatPixelArt.getInstance().getConfig().contains("images." + args[1])) {
                sender.sendMessage(ChatColor.RED + "Image not found");
                return false;
            }
            String raw = ChatPixelArt.getInstance().getConfig().getString("images." + args[1]);
            try {
                BufferedImage image = PixelArtUtils.base64Image(raw);
                BaseComponent[] bc = PixelArtUtils.bufferToComponent(image);
                sender.spigot().sendMessage(bc);
            } catch (PixelArtException e) {
                printException(sender, e);
            }
            return true;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("parse")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "/pixelart parse [base64-encoded-image]");
                return false;
            }
            String raw = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
            try {
                BufferedImage image = PixelArtUtils.base64Image(raw);
                BaseComponent[] bc = PixelArtUtils.bufferToComponent(image);
                sender.spigot().sendMessage(bc);
            } catch (PixelArtException e) {
                printException(sender, e);
            }
            return true;
        }

        if (args.length >= 1 && args[0].equalsIgnoreCase("reload")) {
            ChatPixelArt.getInstance().reloadConfig();
            int count = ChatPixelArt.getInstance().getConfig().getConfigurationSection("images").getKeys(false).size();
            sender.sendMessage(ChatColor.GREEN + "Configuration Reloaded (" + count + " Loaded)");
            return true;
        }

        sender.sendMessage(ChatColor.RED + "/pixelart [show/parse/reload]");
        return false;
    }

}
