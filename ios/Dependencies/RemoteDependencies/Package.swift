// swift-tools-version: 5.10
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "RemoteDependencies",
    platforms: [.iOS(.v15)],
    products: [
        .library(name: "FactoryDep", targets: ["FactoryDep"]),
        .library(name: "SpyableDep", targets: ["SpyableDep"]),
        .library(name: "KeychainAccessDep", targets: ["KeychainAccessDep"]),
        .library(name: "FirebaseDep", targets: ["FirebaseDep"])
    ],
    dependencies: [
        .package(url: "https://github.com/hmlongco/Factory.git", .upToNextMajor(from: "2.3.0")),
        .package(url: "https://github.com/Matejkob/swift-spyable", from: "0.1.0"),
        .package(url: "https://github.com/kishikawakatsumi/KeychainAccess.git", .upToNextMajor(from: "4.0.0")),
        .package(url: "https://github.com/firebase/firebase-ios-sdk.git", .upToNextMajor(from: "10.0.0"))
    ],
    targets: [
        .target(
            name: "FactoryDep",
            dependencies: [
                .product(name: "Factory", package: "Factory")
            ]
        ),
        .target(
            name: "SpyableDep",
            dependencies: [
                .product(name: "Spyable", package: "swift-spyable")
            ]
        ),
        .target(
            name: "KeychainAccessDep",
            dependencies: [
                .product(name: "KeychainAccess", package: "KeychainAccess")
            ]
        ),
        .target(
            name: "FirebaseDep",
            dependencies: [
                .product(name: "FirebaseAnalyticsWithoutAdIdSupport", package: "firebase-ios-sdk"),
                .product(name: "FirebaseCrashlytics", package: "firebase-ios-sdk")
            ]
        )
    ]
)
