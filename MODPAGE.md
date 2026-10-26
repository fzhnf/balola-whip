# CurseForge submission sheet

Everything the CurseForge project form and file upload ask for, as ready-to-paste text.
Keep this file in sync with `manifest.json` when you bump the version.

---

## Project creation

**Game:** Hytale
**Class:** Mods
**Name:** Balola Whip

**Summary** (short, shows in listings):

```
A whip that deals no damage: every lash gives the target a short stamina refill that lapses unless you keep whipping.
```

**Description** (English, long form):

```
Balola Whip adds one item to Hytale: a light one-handed whip that is not a weapon.

A quick lash deals no damage and makes the target "Whipped" for 4 seconds. Hold the swing for about 1.4 seconds before releasing and it applies the same effect for 8 seconds instead. While Whipped the target regains 5 stamina per second - the greater stamina potion's rate, without drinking anything.

The effect never stacks, it only refreshes: the last hit sets the remaining time, so Whipped always lapses 4 to 8 seconds after your last lash. To keep someone going, you have to keep whipping them.

Who it is for: keeping a sprinting friend or a boss-tanking ally topped up, or stretching your own bar between fights. It works on any entity, players included, so in PvP it cuts both ways.

It only ever targets entities - the whip does not break blocks or plants, and it deals no damage at all, so it never wears down armour.

Feel:
- Swings are cloth whooshes rather than the flail's chain rattle. The held swing adds a crack, and impacts land as cloth snap and rope crack with a wooden thud on the held hit.
- The chain is longer than the base game's flail: the link joints were moved apart rather than the links stretched, so it reaches further.
- Charge timing, animations, trails and knockback are all inherited from the vanilla steel flail chain, so it handles exactly like the base game. Only the damage and the effect are modded.

Durability is 200, one point per connecting hit, and it does not stack.

Crafting (Workbench, Tools tab, 3 seconds):
- Deco Rope x4
- Stick x2
- Light Leather x2

Requires Hytale 0.7.4 or newer (server version range >=0.7.4 <0.8.0). Server-side plugin plus the asset pack in the same jar; nothing to install on the client beyond enabling the mod for a world.
```

**Project License:** MIT

**Main category:** Mods → *pick the closest Hytale category available for items/gameplay*
**Additional categories:** up to 4 — suggested: Items, Tools/Utility, Combat

**Logo Image:** 512×512 render of the whip (same Rotation 90° / Inclination 25° pose as the icons)

**Gallery:** in this order

1. `screenshots-2.png` (1524×954) — whip in hand on a server, long chain and spikes visible, hotbar icon in slot 2
2. `screenshots.png` (937×629) — earlier shot from 0.1.0

---

## Project

**Project id:** `1761566` — set as `curseforgeID` in `gradle.properties`, which is what fills
`UpdateChecker.CurseForge` in the manifest. The in-game update check reads that, so a new file on
CurseForge is picked up by installed copies without republishing the mod.

```bash
./gradlew build
cp build/libs/BalolaWhip-0.2.0.jar <Hytale>/UserData/Mods/
```

---

## File upload (version 0.2.0)

**File:** `build/libs/BalolaWhip-0.2.0.jar`
**Release type:** Beta
**Game version:** 0.7.4

**Changelog:**

```
0.2.0 - after some decisions, to make it more "aligned" with the whipping
person, the whipped entity now hands out stamina instead of extending buffs.

Gameplay changes:
- a lash gives "Whipped" effect to the Whipped entity, it's 5 stamina for 4s,
8s if the whip is charged.
- "Whipped" effect doesn't stack, only refresh.
- The whip now deals no damage and thus never wore armour.
- The whip also no longer breaks blocks or plants, entities only.
- previously can stack to 100. Its now decreased to 1.

Design changes:
- The whip sounds were replaced with cloth whooshes instead of the
flail's chain rattle.
- Longer rope 3d model, but this is either good design choice, or maybe a bad
idea.
```

---

## After uploading a file

```bash
git tag -a v0.2.0 -m "0.2.0: stamina instead of buff extension"
git push origin main --tags
```

Tag after the jar is uploaded, so the tag matches the file people actually downloaded.

---

## Checklist before submitting

- [x] In-game smoke test after the namespace change (mod shows as `TimaGutmensch:BalolaWhip`)
- [x] Screenshots for the gallery
- [ ] Logo image 512×512
- [x] `LICENSE` and `CHANGELOG.md` present in the repo
- [x] Version bumped consistently in `gradle.properties` and `CHANGELOG.md`
- [ ] Item icon re-rendered from the long model at `IconProperties.Scale` 0.36 — the committed
      `Balola_Whip.png` is still the 0.1.0 render, so the inventory icon shows the short whip
- [ ] Mod icon at 256×256 — the re-export handed over was 128×128
