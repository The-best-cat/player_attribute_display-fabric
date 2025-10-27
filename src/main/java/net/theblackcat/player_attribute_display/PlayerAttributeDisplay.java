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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.theblackcat.player_attribute_display.config.ServerConfig;
import net.theblackcat.player_attribute_display.network.PacketRegistry;
import net.theblackcat.player_attribute_display.network.PacketRegistryServer;
import net.theblackcat.player_attribute_display.network.records.AttributeDataResult;
import net.theblackcat.player_attribute_display.screen.AttributeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
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

    public static List<AttributeDataResult> GetAttributeValues(ServerPlayerEntity player, List<AttributeData> data) {
        HashSet<Identifier> id = new HashSet<>(data.size());
        List<AttributeDataResult> values = new ArrayList<>(data.size());

        for (AttributeData d : data) {
            AttributeDataResult.InvalidReason reason = AttributeDataResult.InvalidReason.NONE;

            var expression = Expression.tryParse(d.expression);
            var entry = Registries.ATTRIBUTE.getEntry(d.attributeId);
            if (!id.add(d.attributeId)) {
                reason = AttributeDataResult.InvalidReason.REPEATED_ATTRIBUTE;
            } else if (entry.isEmpty()) {
                reason = AttributeDataResult.InvalidReason.ATTRIBUTE_NOT_EXIST;
            } else if (!player.getAttributes().hasAttribute(entry.get())) {
                reason = AttributeDataResult.InvalidReason.ATTRIBUTE_NOT_FOUND;
            } else if (d.decimalPlaces < 0) {
                reason = AttributeDataResult.InvalidReason.INVALID_DECIMAL_PLACES;
            } else if (!d.expression.isBlank() && !expression.isValid()) {
                reason = AttributeDataResult.InvalidReason.INVALID_MATHS_EXPRESSION;
            }

            boolean bl2 = d.expression.isBlank();
            double baseValue = 0d, currentValue = 0d;

            if (reason != AttributeDataResult.InvalidReason.ATTRIBUTE_NOT_FOUND && entry.isPresent()) {
                baseValue = GetAttributeValue(player, entry.get(), bl2 ? null : expression.get(), true, d.percentage);
                currentValue = GetAttributeValue(player, entry.get(), bl2 ? null : expression.get(), false, d.percentage);
            }

            values.add(new AttributeDataResult(
                    entry.map(ref -> ref.value().getTranslationKey()).orElse(null),
                    d.prefix,
                    baseValue,
                    currentValue,
                    d.percentage,
                    d.decimalPlaces,
                    d.unit,
                    reason
                )
            );
        }
        return values;
    }

    private static double GetAttributeValue(PlayerEntity player, RegistryEntry<EntityAttribute> attribute, Expression expression, boolean base, boolean percentage) {
        double value = base ? player.getAttributeBaseValue(attribute) : player.getAttributeValue(attribute);
        if (expression != null) {
            value = expression.evalSafe(Map.of('x', value), value);
        }
        value = percentage ? value * 100f : value;
        return value;
    }
}