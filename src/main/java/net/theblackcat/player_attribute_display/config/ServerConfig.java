package net.theblackcat.player_attribute_display.config;

public class ServerConfig extends ModConfig {
    public boolean disableScreen;
    public boolean useServerConfig;

    public ServerConfig() {
        super("server_config");
        disableScreen = false;
        useServerConfig = false;
    }
}
