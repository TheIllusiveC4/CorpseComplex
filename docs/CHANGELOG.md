# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project does not adhere to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).
This project uses MCVERSION-MAJORMOD.MAJORAPI.MINOR.PATCH.

## [2.0-beta5](https://github.com/TheIllusiveC4/CorpseComplex/compare/99e39f89c5fde4186bc3f33ee7f622b89a986c4c...master) - 2020.06.15
### Added
- Added item overrides [#38](https://github.com/TheIllusiveC4/CorpseComplex/issues/38)

## [2.0-beta4](https://github.com/TheIllusiveC4/CorpseComplex/compare/396ea2a0606ec0e5cc0ee2b3c8c05b52646601cf...99e39f89c5fde4186bc3f33ee7f622b89a986c4c) - 2020.06.14
### Added
- Added difficulty as a condition for overrides [#31](https://github.com/TheIllusiveC4/CorpseComplex/issues/31)
- Added player name or UUID as a condition for overrides [#35](https://github.com/TheIllusiveC4/CorpseComplex/issues/35)
### Changed
- Overrides now must meet all conditions instead of just one

## [2.0-beta3](https://github.com/TheIllusiveC4/CorpseComplex/compare/147d0d940bb1b7c359a3eb41be1e37d84c0f2ffb...396ea2a0606ec0e5cc0ee2b3c8c05b52646601cf) - 2020.05.02
### Fixed
- Fixed NPE crash when relogging before respawning [#34](https://github.com/TheIllusiveC4/CorpseComplex/issues/34)

## [2.0-beta2](https://github.com/TheIllusiveC4/CorpseComplex/compare/72c73c5a02631e57f1ac170c5ca37549ec82264c...147d0d940bb1b7c359a3eb41be1e37d84c0f2ffb) - 2020.04.17
### Fixed
- Fixed NPE crash related to the Soulbinding enchantment [#32](https://github.com/TheIllusiveC4/CorpseComplex/issues/32)

## [2.0-beta1](https://github.com/TheIllusiveC4/CorpseComplex/compare/1.12.x...72c73c5a02631e57f1ac170c5ca37549ec82264c) - 2020.03.31
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