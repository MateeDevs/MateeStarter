fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## iOS

### ios build_alpha

```sh
[bundle exec] fastlane ios build_alpha
```

Create a new Alpha build

### ios build_beta

```sh
[bundle exec] fastlane ios build_beta
```

Create a new Beta build

### ios build_production

```sh
[bundle exec] fastlane ios build_production
```

Create a new Production build

### ios upload_testflight_alpha

```sh
[bundle exec] fastlane ios upload_testflight_alpha
```

Upload the Alpha build to the TestFlight

### ios upload_testflight_beta

```sh
[bundle exec] fastlane ios upload_testflight_beta
```

Upload the Beta build to the TestFlight

### ios upload_testflight_production

```sh
[bundle exec] fastlane ios upload_testflight_production
```

Upload the Production build to the TestFlight

### ios test_alpha

```sh
[bundle exec] fastlane ios test_alpha
```

Run tests on Alpha

### ios test_beta

```sh
[bundle exec] fastlane ios test_beta
```

Run tests on Beta

### ios test_production

```sh
[bundle exec] fastlane ios test_production
```

Run tests on Production

### ios tag

```sh
[bundle exec] fastlane ios tag
```

Create and push git tag

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
