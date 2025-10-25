# Player Attribute Display
This mod adds a panel that displays a list of attribute values.

In order to obtain accurate values, this mod requires client-server communication, so it **MUST** be installed on **BOTH** the client and server in multiplayers environments.

This mod includes a server config and a client config. 
- If the server config is **enabled**, all players will display the same set of attributes. The panel can be disabled for all players as well.
- If the server config is **disabled**, each player can configure their own panel individually.

The default keybind to open the panel is **'Tab'**.

## List Configuration
The panel can display up to 13 attributes at most.

In each entry, the identifier of the attribute must be provided.

You can optionally add a prefix and a unit to the value.

You can configure the number format of the displayed value:
- Raw: Default. The value is displayed as-is.
- Percentage: The value is multiplied by 100 and the % symbol is added.
  
You can configure the decimal place of the value, which ranges from 0 to 5 (inclusive). If it is 0, the value is displayed as a rounded integer.

If the value requires some calculations before being displayed, you can provide a mathematical expression. Use **'x'** to represent the original value. Unfortunately, substituting other dynamic values is not supported at the moment.

## Dependencies
### Required
- [Fzzy Config](https://modrinth.com/mod/fzzy-config)
- [Fabric Language Kotlin](https://modrinth.com/mod/fabric-language-kotlin)
- [Fabric API](https://modrinth.com/mod/fabric-api)

### Optional
- [Mod Menu](https://modrinth.com/mod/modmenu) 
- [Text Placeholder API](https://modrinth.com/mod/placeholder-api) (Required if Mod Menu is installed)

## Supported Languages
- English (United States)
- English (United Kingdom)
- Cantonese (Hong Kong)
- Chinese (Traditional Chinese)
- Chinese (Simplified Chinese)

## Plan
- Allow customisable colours for attribute names and values. (Figuring out how)
- Allow toggling between displaying final values or values with changes. 
- Allow pinning attributes and displaying their current values on screen without a panel. (Less likely)
- Add translations for Korean and Japanese.
- Allow players to add custom attribute name translations using Json.

**This mod is for fabric only. There is currently no plan to make a forge/neoforge version.**
<br>And btw, if you're adding custom attributes, try not to make the names _too_ long, will ya?
