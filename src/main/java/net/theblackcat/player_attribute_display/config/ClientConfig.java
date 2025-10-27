package net.theblackcat.player_attribute_display.config;

import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedColor;

import java.awt.*;

public class ClientConfig extends ModConfig {
    public boolean showDetail;
    public ValidatedColor nameColour;
    public ValidatedColor valueColour;
    public ValidatedColor positiveDetailColour;
    public ValidatedColor negativeDetailColour;

    public ClientConfig() {
        super("client_config");
        showDetail = false;
        nameColour = new ValidatedColor(55, 55, 55, 255);
        valueColour = new ValidatedColor(114, 114, 114, 255);
        positiveDetailColour = new ValidatedColor(0, 206, 0, 255);
        negativeDetailColour = new ValidatedColor(Color.RED);
    }
}
