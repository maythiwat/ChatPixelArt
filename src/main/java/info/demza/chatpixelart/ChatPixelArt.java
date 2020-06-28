package info.demza.chatpixelart;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatPixelArt extends JavaPlugin {

    static ChatPixelArt instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Bukkit.getPluginCommand("pixelart").setExecutor(new PixelArtCommand());
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static ChatPixelArt getInstance() {
        return instance;
    }
}
