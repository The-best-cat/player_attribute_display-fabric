package net.theblackcat.player_attribute_display.event;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import org.lwjgl.glfw.GLFW;

public class ModKeyBindings {
    public static final String CATEGORY = "key.category." + PlayerAttributeDisplay.MOD_ID + ".main";
    public static final String OPEN_SCREEN = "key." + PlayerAttributeDisplay.MOD_ID + ".open_screen";

    public static KeyBinding openScreen;

    public static void Register() {
        openScreen = KeyBindingHelper.registerKeyBinding(new KeyBinding(OPEN_SCREEN, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_TAB, CATEGORY));
    }
}
