# Balola Whip

A Hytale mod that adds the **Balola Whip** — a light whip that barely hurts, but stretches the
positive status effects already on whatever it hits.

## What it does

- **Quick lash** (tap): no damage, **+20 s** to each positive effect on the target
- **Held lash** (hold ≥ 1.39 s, then release): **+50 s**, still no damage
- Deals **no damage at all**, so it never wears the target's armour
- Never *applies* an effect — only extends ones already present
- Debuffs and infinite effects are left alone
- Cap: an effect can be pushed at most **+240 s** past its own base duration
- 200 durability, 1 point per connecting hit

Mechanic-wise it is the vanilla steel flail: `Interactions.Primary` points at the stock
`Club_Flail_Attack` chain (`Charging` → `Chaining` → `Swing_Left`/`Swing_Right`, or the charged spin
swing), so the animations, trails, hit detection and knockback are all inherited. Only the three
`*_Damage` interaction vars are overridden to change damage and add the effect extension.

## Crafting

Workbench → Tools, 3.0 s:

| ingame item | qty |
|---|---|
| `Deco_Rope` | 4 |
| `Ingredient_Stick` | 2 |
| `Ingredient_Leather_Light` | 2 |

## Layout

```
src/main/java/dev/balola/whip/
  BalolaWhipPlugin.java                        registers the interaction codec
  interaction/ProlongPositiveEffectsInteraction.java   the extension logic
src/main/resources/
  Server/Item/Items/Tool/Whip/Tool_Whip_Balola.json    item + interaction vars + recipe
  Server/Languages/en-US/items.lang                    display name and description
  Common/Items/Tools/Whip/Balola_Whip.blockymodel      model
  Common/Items/Tools/Whip/Balola_Whip_Texture.png      texture (64×64)
```

## Building

```bash
./gradlew build
cp build/libs/BalolaWhip-0.1.0.jar <Hytale>/UserData/Mods/
```

Then enable the mod for a world (world settings → Mods). Java 25, targets Hytale 0.7.4.

## Notes

- The interaction is registered as `ProlongPositiveEffects` and referenced from the item's
  `InteractionVars`; it is server-side only and sends nothing custom to the client.
- The item keeps the base-game flail icon until a render of the whip replaces it.
- Model node names (`Handle`, `Chain`, `Chain2`–`Chain4`) must stay as-is: both the inherited
  animations and the chain trail effects address them by name.
