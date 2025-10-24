package net.theblackcat.player_attribute_display.config;

import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;

public class ServerConfig extends ModConfig {
    public ValidatedBoolean disableScreen;
    public ValidatedBoolean useServerConfig;

    public ServerConfig() {
        super("server_config");
        disableScreen = new ValidatedBoolean(false);
        useServerConfig = new ValidatedBoolean(false);
    }
}
