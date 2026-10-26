# Changelog

All notable changes to this project are documented here.
Format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/);
this project uses [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.0] - 2026-10-26

Stamina instead of buff-prolonging. Requires Hytale 0.7.4 (server range `>=0.7.4 <0.8.0`).

### Changed

- A lash now applies **Whipped** to the target: 5 stamina per second for **4 s** (quick lash) or
  **8 s** (held lash) — the greater stamina potion's refill rate, no bottle involved.
- **Whipped** refreshes instead of stacking: the last lash sets the remaining time, so the buff lapses
  4–8 s after the last hit and the target has to be whipped again to keep it.

### Removed

- The `ProlongPositiveEffects` interaction and its Java class — the mod is now item JSON plus one effect
  asset, using vanilla `ApplyEffect`.

## [0.1.0] - 2026-10-23

First release. Requires Hytale 0.7.4 (server range `>=0.7.4 <0.8.0`).

### Added

- **Balola Whip** (`Tool_Whip_Balola`) — a light one-handed whip that extends the positive status
  effects already on the entity it hits.
  - Quick lash (tap): **+20 s** to every positive effect on the target, no damage.
  - Held lash (hold ≥ 1.39 s, then release): **+50 s**, no damage.
  - Deals no damage, so it never wears the target's armour (armour durability loss is
    per-damage-cause, not per-damage-point, so 0 damage was not enough).
  - Never applies an effect, only prolongs existing ones; debuffs and infinite effects are skipped.
  - Cap: an effect can be pushed at most **+240 s** past its own base duration.
  - 200 durability, 1 point per connecting hit.
- Custom interaction type `ProlongPositiveEffects` (server-side) that performs the extension.
- Own model, texture, 64×64 item icon and 256×256 mod icon.
- Crafting recipe at the Workbench → Tools tab, 3.0 s:
  `Deco_Rope` ×4, `Ingredient_Stick` ×2, `Ingredient_Leather_Light` ×2.

### Notes

- Attack behaviour is inherited from the vanilla steel flail chain (`Club_Flail_Attack`), so the
  charge/hold detection, swing animations, trails and knockback are the base game's. Only the
  damage values and the effect extension are modded.
