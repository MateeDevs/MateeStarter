# RemoteDependencies

Central package for sharing remote dependencies between local swift packages.

# Issue

Any swift package that depends on any product of `RemoteDependencies` has available all dependencies declared in `RemoteDependencies`.
This is a knows SPM issue as swift package depending on product of `RemoteDependencies` should have only the dependencies of the given product.

## Overview

This package manages all remote dependencies and their versions. 

### Usage

Any swift package requiring any remote dependency should add this package to its dependencies and the corresponding product.
For exaple, to add dependency on Factory add the following to your `Package.swift`:

```swift
dependencies: [
    .package(name: "RemoteDependencies", path: "*relative path to this package*")
],
targets: [
    .target(
        name: "*your target name*",
        dependencies: [
            .product(name: "FactoryDep", package: "RemoteDependencies")
        ]
    )
]
```

### Adding a new dependency

If you want to add a new dependency, you should:
1. Add the dependency in the `RemoteDependencies` package.
2. Create new target in the `RemoteDependencies` package for the new dependency. Like this:
```swift
.target(
    name: "*dependency name*Dep",
    dependencies: [
        .product(name: "Factory", package: "Factory")
    ]
)
```
3. Create a new product in the `RemoteDependencies` package for the new dependency. Like this:
```swift
.library(name: "*dependency name*Dep", targets: ["*dependency name*Dep"])
```
