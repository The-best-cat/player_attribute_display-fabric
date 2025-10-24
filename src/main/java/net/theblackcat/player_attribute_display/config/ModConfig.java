package net.theblackcat.player_attribute_display.config;

import me.fzzyhmstrs.fzzy_config.annotations.Translation;
import me.fzzyhmstrs.fzzy_config.api.FileType;
import me.fzzyhmstrs.fzzy_config.api.SaveType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.minecraft.ValidatedIdentifier;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.theblackcat.player_attribute_display.PlayerAttributeDisplay;
import net.theblackcat.player_attribute_display.screen.AttributeData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModConfig extends Config {
    public ValidatedList<AttributeConfigData> data = new ValidatedAny<>(new AttributeConfigData()).toList(
            new AttributeConfigData(GetAttributeId(EntityAttributes.ATTACK_DAMAGE), 1),
            new AttributeConfigData(GetAttributeId(EntityAttributes.ATTACK_SPEED), 0),
            new AttributeConfigData(GetAttributeId(EntityAttributes.SWEEPING_DAMAGE_RATIO), "+", AttributeConfigData.NumberFormat.PERCENTAGE, 1, "", ""),
            new AttributeConfigData(GetAttributeId(EntityAttributes.ARMOR), 0),
            new AttributeConfigData(GetAttributeId(EntityAttributes.ARMOR_TOUGHNESS), 0),
            new AttributeConfigData(GetAttributeId(EntityAttributes.MOVEMENT_SPEED), "", AttributeConfigData.NumberFormat.RAW, 1, "m/s", "x * 43.17")
    );

    protected ModConfig(String id) {
        super(PlayerAttributeDisplay.GetId(id));
    }

    private static Identifier GetAttributeId(RegistryEntry<EntityAttribute> attribute) {
        return Identifier.of(attribute.getIdAsString());
    }

    public List<AttributeData> GetData() {
        List<AttributeData> data = new ArrayList<>(this.data.size());
        for (var d : this.data) {
            Identifier id = d.attributeId.get();
            int dp = d.decimalPlaces.get();
            data.add(new AttributeData(
                    d.prefix,
                    id,
                    d.format == AttributeConfigData.NumberFormat.PERCENTAGE,
                    dp,
                    d.unit,
                    d.expression
            ));
        }
        return data;
    }

    @Override
    public @NotNull FileType fileType() {
        return FileType.JSON;
    }

    @Override
    public @NotNull SaveType saveType() {
        return SaveType.OVERWRITE;
    }

    @Translation(prefix = PlayerAttributeDisplay.MOD_ID + ".config.config_data")
    public static class AttributeConfigData {
        public ValidatedIdentifier attributeId;
        public String prefix;
        public NumberFormat format;
        public ValidatedInt decimalPlaces;
        public String unit;
        public String expression;

        public enum NumberFormat implements EnumTranslatable {
            RAW,
            PERCENTAGE;

            @Override
            public @NotNull String prefix() {
                return PlayerAttributeDisplay.MOD_ID + ".config.config_data.number_format";
            }
        }

        public AttributeConfigData() {
            this(Identifier.ofVanilla(""), "", NumberFormat.RAW, 0, "", "");
        }

        public AttributeConfigData(Identifier id, int dp) {
            this(id, "", NumberFormat.RAW, dp, "", "");
        }

        public AttributeConfigData(Identifier id, String prefix, NumberFormat format, int dp, String unit, String expression) {
            attributeId = ValidatedIdentifier.ofRegistry(id, Registries.ATTRIBUTE);
            this.prefix = prefix;
            this.format = format;
            decimalPlaces = new ValidatedInt(dp, 5, 0);
            this.unit = unit;
            this.expression = expression;
        }
    }
}