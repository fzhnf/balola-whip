# Changelog

All notable changes to this project are documented here.
Format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/);
this project uses [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.0] - 2026-10-26

After some decisions, to make it more "aligned" with the whipping person, the whipped entity now hands
out stamina instead of extending buffs. Requires Hytale 0.7.4 (server range `>=0.7.4 <0.8.0`).

### Gameplay

- A lash makes the target **Whipped** — 5 stamina per second for **4 s** (quick) or **8 s** (held).
- **Whipped** does not stack, only refresh: last hit wins, so the buff lapses 4–8 s after the last lash.
- The whip now deals no damage, and so never wore the target's armour.
- The whip also no longer breaks blocks or plants — entities only.

### Design

- Whip sounds replaced. Swings are cloth-foley whooshes instead of the flail's chain rattle, the held
  swing adds a crack over the charging whoosh, and the impacts land as cloth snap plus rope crack, with
  a wooden thud behind the held hit.
- Longer chain: the model was re-spaced on its `Chain`/`Chain2`/`Chain3` joints rather than by stretching
  the links, so the whip reaches further than 0.1.0. Whether that reads better at full swing or worse is
  still up for debate.

### Fixed

- The whip stacked to 100. Hytale only defaults an item to stack size 1 when it carries a `Tool`,
  `Weapon`, `Armor`, `BuilderTool` or `BlockSelectorToolData` block; the whip has none of those, so it
  got the fallback 100 and a durability-bearing item could be stacked. `MaxStack: 1` is now explicit.
- Three sound events (`SFX_Whip_Charge`, `SFX_Whip_Charge_Local`, `SFX_Whip_Impact_Quick`) had pitch
  ranges that did not contain 0, which Hytale rejects at load — that cascaded into `Whip_Attack` and
  the quick-lash interactions and refused to load the world at all.

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
