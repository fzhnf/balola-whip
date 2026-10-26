# Balola Whip

A Hytale mod that adds the **Balola Whip** — a light whip that deals no damage, but forces stamina
back into whatever it hits.

## What it does

- **Quick lash** (tap): no damage, applies **Whipped** for **4 s**
- **Held lash** (hold ≥ 1.39 s, then release): applies **Whipped** for **8 s**, still no damage
- **Whipped** refills 5 stamina per second (the greater stamina potion's rate, without the bottle)
- Every lash resets the timer, so the buff lapses 4–8 s after the last hit — you have to keep whipping
- Last hit wins: a quick lash on a held-whipped target drops it back to 4 s
- Deals **no damage at all**, so it never wears the target's armour
- 200 durability, 1 point per connecting hit

Mechanic-wise it is the vanilla steel flail: `Interactions.Primary` points at the stock
`Club_Flail_Attack` chain (`Charging` → `Chaining` → `Swing_Left`/`Swing_Right`, or the charged spin
swing), so the animations, trails, hit detection and knockback are all inherited. Only the three
`*_Damage` interaction vars are overridden, and they are plain vanilla `ApplyEffect` steps — no Java
is involved in the mechanic.

## Crafting

Workbench → Tools, 3.0 s:

| ingame item | qty |
|---|---|
| `Deco_Rope` | 4 |
| `Ingredient_Stick` | 2 |
| `Ingredient_Leather_Light` | 2 |

## Layout

```
src/main/java/dev/timagutmensch/whip/
  BalolaWhipPlugin.java                                empty plugin shell (manifest needs a Main)
src/main/resources/
  Server/Item/Items/Tool/Whip/Tool_Whip_Balola.json    item + interaction vars + recipe
  Server/Entity/Effects/Balola/Balola_Whipped.json      the Whipped effect
  Server/Languages/en-US/items.lang                    display name and description
  Common/Items/Tools/Whip/Balola_Whip.blockymodel      model
  Common/Items/Tools/Whip/Balola_Whip_Texture.png      texture (64×64)
```

## Building

```bash
python3 tools/check-assets.py   # catches what Hytale rejects at boot, before you ship it
./gradlew build
cp build/libs/BalolaWhip-0.2.0.jar <Hytale>/UserData/Mods/
```

Then enable the mod for a world (world settings → Mods). Java 25, targets Hytale 0.7.4.

## Notes

- The `*_Damage` vars are vanilla `ApplyEffect` interactions: `EffectId: Balola_Whipped`, `Entity:
  Target`, `Duration` 4 (quick) or 8 (held). `OverlapBehavior: Overwrite` on the asset is what makes a
  lash reset the timer instead of stacking.
- `StatModifiers` in an entity effect is re-applied every `DamageCalculatorCooldown` seconds as an
  additive stat change, which is what vanilla stamina potions and food use for regen — that is the
  whole buff, there is no per-tick code.
- The item keeps the base-game flail icon until a render of the whip replaces it.
- Model node names (`Handle`, `Chain`, `Chain2`–`Chain4`) must stay as-is: both the inherited
  animations and the chain trail effects address them by name.
