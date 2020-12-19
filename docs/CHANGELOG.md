# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project does not adhere to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).
This project uses MCVERSION-MAJORMOD.MAJORAPI.MINOR.PATCH.

## [1.16.4-4.0.0.3] - 2020.12.18
### Changed
- Updated Russian localization
- Updated Soulbinding enchantment
  - Soulbinding logic now utilizes an enchantment tag, "forge:soulbound", for advanced compatibility
    with other mods that add a similar enchantment
  - Additional Soulbinding enchantment config options have been added to properly remove the
    enchantment from loot tables and villager trades when disabled

## [1.16.3-4.0.0.2] - 2020.10.05
### Fixed
- Fixed items not respecting drop overrides
- Fixed NPE with Cosmetic Armor Reworked integration

## [1.16.3-4.0.0.1] - 2020.10.03
### Fixed
- Fixed NPE with Curios integration [#46](https://github.com/TheIllusiveC4/CorpseComplex/issues/46)

## [1.16.3-4.0.0.0] - 2020.09.11
### Added
- Added Russian localization (thanks BardinTheDwarf!)
### Changed
- Updated to Minecraft 1.16.3

## [3.0] - 2020.07.08
### Changed
- Ported to 1.16.1 Forge