#!/usr/bin/env python3
"""Pre-flight check for the assets Hytale validates at boot.

A bad range here does not warn: the asset is dropped, every interaction that
references it fails, and the world refuses to load with
"Mod ... failed to load. Check for mod updates or contact the mod author."
"""

import glob
import json
import os
import sys

root = "src/main/resources"
problems = []

# RandomSettings pitch is a semitone offset, and the range must contain 0:
# all 1928 vanilla layers do, Hytale rejects any that don't.
for f in glob.glob(root + "/Server/Audio/SoundEvents/**/*.json", recursive=True):
    for i, layer in enumerate(json.load(open(f)).get("Layers") or []):
        rs = layer.get("RandomSettings") or {}
        mn, mx = rs.get("MinPitch", 0.0), rs.get("MaxPitch", 0.0)
        if mn > 0 or mx < 0:
            problems.append(f"{f} layer {i}: MinPitch {mn} / MaxPitch {mx}")


def refs(f, key):
    """Every string value under `key`, at any depth."""
    stack, out = [json.load(open(f))], []
    while stack:
        o = stack.pop()
        if isinstance(o, dict):
            out += [v for k, v in o.items() if k == key and isinstance(v, str)]
            stack += list(o.values())
        elif isinstance(o, list):
            stack += o
    return out
    return out


effects = {os.path.splitext(os.path.basename(p))[0]
           for p in glob.glob(root + "/Server/Entity/Effects/**/*.json", recursive=True)}
sounds = {os.path.splitext(os.path.basename(p))[0]
          for p in glob.glob(root + "/Server/Audio/SoundEvents/**/*.json", recursive=True)}

for f in glob.glob(root + "/Server/**/*.json", recursive=True):
    for key in ("LocalSoundEventId", "WorldSoundEventId"):
        problems += [f"{f}: no sound event '{v}'" for v in refs(f, key) if v not in sounds]
    problems += [f"{f}: no effect asset '{v}'" for v in refs(f, "EffectId") if v not in effects]

print(f"{len(sounds)} sound events, {len(effects)} effect assets, {len(problems)} problem(s)")
for p in problems:
    print("  -", p)
sys.exit(1 if problems else 0)
