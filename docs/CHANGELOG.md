# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project does not adhere to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).
This project uses MCVERSION-MAJORMOD.MAJORAPI.MINOR.PATCH.

## [2.0-beta2](https://github.com/TheIllusiveC4/CorpseComplex/compare/https://github.com/TheIllusiveC4/CorpseComplex/compare/1.12.x...master...master) - 2020.04.17
### Fixed
- Fixed NPE crash related to the Soulbinding enchantment [#32](https://github.com/TheIllusiveC4/CorpseComplex/issues/32)

## [2.0-beta1](https://github.com/TheIllusiveC4/CorpseComplex/compare/1.12.x...https://github.com/TheIllusiveC4/CorpseComplex/compare/1.12.x...master) - 2020.03.31
### Added
- Conditional Overrides: Override any config variable through 'corpsecomplex-overrides' based on death conditions. Conditions currently support damage type, source, dimension, and Game Stages stages.
- Keep Exhaustion config option
- Drop Despawn Time config option
- Curios integration
- Game Stages integration
### Changed
- Curable and incurable effects config collapsed into a single effects list with an optional ";incurable" parameter
- Experience module split up between two loss options: Percent or Per Level
- Inventory settings expanded into a default set and specific sets for each inventory section
- Essential and cursed items config collapsed into a single items list with an optional ;keep/drop/destroy" parameter
### Removed
- Removed Return Scroll features