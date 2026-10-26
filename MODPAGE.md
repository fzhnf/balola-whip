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
A light whip that deals no damage, but forces stamina back into whatever it hits.
```

**Description** (English, long form):

```
Balola Whip adds a single item to Hytale: a light one-handed whip that is not a weapon.

A quick lash deals no damage and makes the target "Whipped" for 4 seconds. Holding the swing for about 1.4 seconds and then releasing applies the same effect for 8 seconds. While Whipped, the target regains 5 stamina per second — the greater stamina potion's rate, without drinking anything. A lash never stacks, only refreshes, so the buff always lapses 4 to 8 seconds after the last hit:

to keep someone going, you have to keep whipping them.

In practice it is a support tool: keep a sprinting friend or a boss-tanking ally topped up on stamina, or top up your own bar between fights. It works on any entity, so it cuts both ways in PvP.

The whip inherits the vanilla steel flail's attack chain, so hold-to-charge, the swing animations, chain trails and knockback all behave exactly like the base game. Durability is 200, one point per connecting hit.

Crafting (Workbench, Tools tab, 3 seconds):
  - Deco Rope x4
  - Stick x2
  - Light Leather x2

Requires Hytale 0.7.4 or newer (server version range >=0.7.4 <0.8.0). Server-side plugin plus an asset pack bundled in the jar; no client install needed beyond enabling the mod for a world.
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
