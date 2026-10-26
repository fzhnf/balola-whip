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
0.2.0 - the whip now hands out stamina instead of extending buffs.

- Balola Whip: a lash makes the target "Whipped" - 5 stamina per second for 4s (quick) or 8s (held).
- Whipped does not stack, only refresh: last hit wins, so the buff lapses 4-8s after the last lash.
- The whip deals no damage and never wore armour, unchanged.
- Removed the ProlongPositiveEffects interaction; the mod is now pure assets + vanilla ApplyEffect.
- Fixed: the whip could be stacked to 100. It now stacks to 1, like any other tool with durability.
- Fixed: three whip sound events had invalid pitch ranges, which could stop a world from loading.
- Longer chain: the model was re-spaced on its chain joints, so the whip reaches further than 0.1.0.
- 200 durability, 1 per connecting hit.
- Crafted at the Workbench (Tools) from 4 rope, 2 sticks, 2 light leather.
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
