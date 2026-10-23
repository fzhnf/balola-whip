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
A light whip that deals almost no damage, but stretches the positive status effects already on whatever it hits.
```

**Description** (English, long form):

```
Balola Whip adds a single item to Hytale: a light one-handed whip that is not a weapon.

A quick lash does 1 damage and adds 20 seconds to every positive status effect the target already has. Holding the swing for about 1.4 seconds and then releasing turns it into a heavier blow: 5 damage and 50 extra seconds. It only ever prolongs effects that are already there — it never applies a new one, and it ignores debuffs and infinite effects. An effect can be extended by at most 4 minutes beyond its own base duration.

In practice it is a support tool: keep a regeneration or food buff alive through a long fight, or top up an ally's buff without carrying the consumables yourself. In PvP it cuts both ways, because it extends whatever positive effects the target is running.

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

---

## File upload (version 0.1.0)

**File:** `build/libs/BalolaWhip-0.1.0.jar`
**Release type:** Beta
**Game version:** 0.7.4

**Changelog:**

```
First release.

- Balola Whip: quick lash = 1 damage, +20s to positive effects on the target;
  held lash = 5 damage, +50s. Cap +240s past an effect's base duration.
- Never applies effects, skips debuffs and infinite effects.
- 200 durability, 1 per connecting hit.
- Crafted at the Workbench (Tools) from 4 rope, 2 sticks, 2 light leather.
- Own model, texture, item icon and mod icon.
```

---

## After the project exists

The project id is the number in the project URL (`curseforge.com/hytale/mods/<slug>` → use the numeric
id from the Author Console). Then:

```bash
# gradle.properties
curseforgeID = <id>

./gradlew updatePluginManifest build
cp build/libs/BalolaWhip-0.1.0.jar <Hytale>/UserData/Mods/
git commit -am "set curseforge project id" && git push
```

That fills `UpdateChecker.CurseForge` in the manifest, which is what the in-game update check reads.

---

## Checklist before submitting

- [ ] In-game smoke test after the namespace change (mod shows as `TimaGutmensch:BalolaWhip`)
- [ ] Screenshots for the gallery: item in hand, the two swing states, crafting recipe
- [ ] Logo image 512×512
- [ ] `LICENSE` and `CHANGELOG.md` present in the repo (done)
- [ ] Version bumped consistently in `gradle.properties` and `CHANGELOG.md`
