#!/usr/bin/env python3
"""Create a descriptor-aware SRG-member -> Mojang-member TSRG map for JCM 1.20.1.

The legacy Forge JAR retains Mojang class names but uses SRG names for members.
This script joins MCP's obfuscated->SRG mapping with Mojang's named->obfuscated
mapping, retaining the owning class and method descriptor so overloaded methods
are never renamed by a global identifier substitution.
"""

from __future__ import annotations

import re
import sys
from collections import defaultdict
from pathlib import Path


PRIMITIVES = {
    "void": "V", "boolean": "Z", "byte": "B", "char": "C", "short": "S",
    "int": "I", "long": "J", "float": "F", "double": "D",
}


def remove_generics(value: str) -> str:
    result, depth = [], 0
    for character in value:
        if character == "<":
            depth += 1
        elif character == ">":
            depth -= 1
        elif depth == 0:
            result.append(character)
    return "".join(result)


def descriptor(value: str, named_to_obf: dict[str, str]) -> str:
    value = remove_generics(value.strip())
    dimensions = 0
    while value.endswith("[]"):
        dimensions += 1
        value = value[:-2]
    base = PRIMITIVES.get(value)
    if base is None:
        base = "L" + named_to_obf.get(value, value.replace(".", "/")) + ";"
    return "[" * dimensions + base


def split_parameters(value: str) -> list[str]:
    if not value:
        return []
    output, current, depth = [], [], 0
    for character in value:
        if character == "<":
            depth += 1
        elif character == ">":
            depth -= 1
        if character == "," and depth == 0:
            output.append("".join(current))
            current = []
        else:
            current.append(character)
    output.append("".join(current))
    return output


def load_tsrg(path: Path):
    class_by_obf: dict[str, str] = {}
    members: dict[str, dict[tuple[str, str | None], str]] = defaultdict(dict)
    owner = None
    for raw in path.read_text(encoding="utf-8").splitlines()[1:]:
        if not raw or raw.startswith("#"):
            continue
        if not raw.startswith("\t"):
            parts = raw.split()
            if len(parts) >= 2:
                owner = parts[0]
                class_by_obf[owner] = parts[1]
            continue
        if owner is None or raw.startswith("\t\t"):
            continue
        parts = raw.strip().split()
        if len(parts) >= 2 and not parts[1].startswith("("):  # field: obf-name srg-name [id]
            members[owner][(parts[0], None)] = parts[1]
        elif len(parts) >= 3:  # method: obf-name obf-desc srg-name [id]
            members[owner][(parts[0], parts[1])] = parts[2]
    return class_by_obf, members


def main(tsrg_path: Path, mojmap_path: Path, output_path: Path) -> None:
    obf_to_srg, tsrg_members = load_tsrg(tsrg_path)
    named_to_obf: dict[str, str] = {}
    class_entries: list[tuple[str, str]] = []
    for line in mojmap_path.read_text(encoding="utf-8").splitlines():
        if line and not line.startswith(" ") and " -> " in line and line.endswith(":"):
            named, obf = line[:-1].split(" -> ", 1)
            named_to_obf[named] = obf.replace(".", "/")
            class_entries.append((named, obf.replace(".", "/")))

    # First pass must have every class name before member descriptors are decoded.
    current_named = current_obf = None
    output: dict[str, list[tuple[str, str | None, str]]] = defaultdict(list)
    method_pattern = re.compile(r"^(?:\d+:\d+:)?(.+?) ([^ (]+)\((.*)\) -> (\S+)$")
    field_pattern = re.compile(r"^(?:\d+:\d+:)?(.+?) ([^ ]+) -> (\S+)$")
    for raw in mojmap_path.read_text(encoding="utf-8").splitlines():
        if raw and not raw.startswith(" ") and " -> " in raw and raw.endswith(":"):
            current_named, obf = raw[:-1].split(" -> ", 1)
            current_obf = obf.replace(".", "/")
            continue
        if not raw.startswith("    ") or current_named is None or current_obf not in tsrg_members:
            continue
        line = raw.strip()
        method = method_pattern.match(line)
        if method:
            return_type, _named_name, parameters, obf_name = method.groups()
            obf_descriptor = "(" + "".join(descriptor(item, named_to_obf) for item in split_parameters(parameters)) + ")" + descriptor(return_type, named_to_obf)
            srg_name = tsrg_members[current_obf].get((obf_name, obf_descriptor))
            if srg_name and srg_name != "<init>":
                # The published Forge JAR already uses Mojang class names in
                # descriptors.  Keep this (source) signature named; only the
                # member identifier is SRG.
                named_descriptor = "(" + "".join(descriptor(item, {}) for item in split_parameters(parameters)) + ")" + descriptor(return_type, {})
                output[current_named.replace(".", "/")].append((srg_name, None, _named_name + " " + named_descriptor))
            continue
        field = field_pattern.match(line)
        if field:
            _field_type, named_name, obf_name = field.groups()
            srg_name = tsrg_members[current_obf].get((obf_name, None))
            if srg_name:
                output[current_named.replace(".", "/")].append((srg_name, "field", named_name))

    lines = ["tsrg2 named named"]
    count = 0
    for owner in sorted(output):
        entries = output[owner]
        if not entries:
            continue
        lines.append(f"{owner} {owner}")
        seen = set()
        for srg_name, kind, mapped in entries:
            if kind == "field":
                line = f"\t{srg_name} {mapped}"
            else:
                named_name, desc = mapped.split(" ", 1)
                line = f"\t{srg_name} {desc} {named_name}"
            if line not in seen:
                lines.append(line)
                seen.add(line)
                count += 1
    output_path.write_text("\n".join(lines) + "\n", encoding="utf-8")
    print(f"Wrote {count} member mappings for {len(output)} classes to {output_path}")


if __name__ == "__main__":
    if len(sys.argv) != 4:
        raise SystemExit("usage: generate_jcm_member_map.py <joined.tsrg> <client.txt> <out.tsrg>")
    main(Path(sys.argv[1]), Path(sys.argv[2]), Path(sys.argv[3]))
