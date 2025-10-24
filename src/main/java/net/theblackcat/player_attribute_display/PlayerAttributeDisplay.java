package net.theblackcat.player_attribute_display;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.util.Expression;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.theblackcat.player_attribute_display.config.ServerConfig;
import net.theblackcat.player_attribute_display.network.PacketRegistry;
import net.theblackcat.player_attribute_display.network.PacketRegistryServer;
import net.theblackcat.player_attribute_display.screen.AttributeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerAttributeDisplay implements ModInitializer {
	public static final String MOD_ID = "player_attribute_display";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final ServerConfig SERVER_CONFIG = ConfigApiJava.registerAndLoadConfig(ServerConfig::new, RegisterType.BOTH);

	@Override
	public void onInitialize() {
        PacketRegistry.Register();
        PacketRegistryServer.Register();
	}

    public static Identifier GetId(String id) {
        return Identifier.of(MOD_ID, id);
    }

    public static Text Translate(String id, Object... args) {
        return Text.translatable(MOD_ID + "." + id, args);
    }

    public static List<Text> GetAttributeValues(ServerPlayerEntity player) {
        List<Text> values = new ArrayList<>();
        List<AttributeData> data = SERVER_CONFIG.useServerConfig.get() ? SERVER_CONFIG.GetData() : PlayerAttributeDisplayClient.CLIENT_CONFIG.GetData();

        for (AttributeData d : data) {
            MutableText text = Text.empty();
            boolean invalid = false;

            var expression = Expression.tryParse(d.expression);
            var entry = Registries.ATTRIBUTE.getEntry(d.attributeId);
            if (d.decimalPlaces < 0) {
                text.append(PlayerAttributeDisplay.Translate("invalid.invalid_decimal_place"));
                invalid = true;
            }
            else if (entry.isEmpty()) {
                text.append(PlayerAttributeDisplay.Translate("invalid.attribute_not_present"));
                invalid = true;
            } else if (!player.getAttributes().hasAttribute(entry.get())) {
                text.append(PlayerAttributeDisplay.Translate("invalid.dont_have_attribute"));
                invalid = true;
            } else if (!d.expression.isBlank() && !expression.isValid()) {
                text.append(PlayerAttributeDisplay.Translate("invalid.invalid_expression"));
                invalid = true;
            }

            if (invalid) {
                text.formatted(Formatting.DARK_RED);
            } else {
                text = Text.translatable(entry.get().value().getTranslationKey());
                text.append(": ").append(d.prefix);
                text.append(DecimalFormat(d.decimalPlaces, GetAttributeValue(player, entry.get(), d.expression.isBlank() ? null : expression.get(), d.percentage)));
                if (d.percentage) text.append("%");
                text.append(d.unit);
            }

            values.add(text);
        }
        return values;
    }

    private static String DecimalFormat(int dp, double value) {
        if (dp == 0) {
            return String.valueOf(Math.round(value));
        }
        return new DecimalFormat("0." + "#".repeat(Math.max(0, dp))).format(value);
    }

    private static double GetAttributeValue(PlayerEntity player, RegistryEntry<EntityAttribute> attribute, Expression expression, boolean percentage) {
        double value = player.getAttributeValue(attribute);
        if (expression != null) {
            value = expression.evalSafe(Map.of('x', value), value);
        }
        value = percentage ? value * 100f : value;
        return value;
    }
}