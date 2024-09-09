//
//  Created by Petr Chmelar on 07.02.2023
//  Copyright © 2023 Matee. All rights reserved.
//

struct BuildConfiguration {
    let workspace: String
    let scheme: String
    let outputName: String
    let appIdentifier: String
    let testFlightGroups: [String]
    let testFlightLink: String
    let slackChannel: String
}

extension BuildConfiguration {
    static let alpha = BuildConfiguration(
        workspace: "MateeStarter.xcworkspace",
        scheme: "MateeStarter_Alpha",
        outputName: "A-MateeStarter",
        appIdentifier: "cz.matee.matee-starter.alpha",
        testFlightGroups: ["App Store Connect Users", "Public"],
        testFlightLink: "https://testflight.apple.com/join/ZdqgI57L",
        slackChannel: "#ci"
    )
    static let beta = BuildConfiguration(
        workspace: "MateeStarter.xcworkspace",
        scheme: "MateeStarter_Beta",
        outputName: "B-MateeStarter",
        appIdentifier: "cz.matee.matee-starter.beta",
        testFlightGroups: ["App Store Connect Users", "Public"],
        testFlightLink: "https://testflight.apple.com/join/aff9SFVc",
        slackChannel: "#ci"
    )
    static let production = BuildConfiguration(
        workspace: "MateeStarter.xcworkspace",
        scheme: "MateeStarter",
        outputName: "MateeStarter",
        appIdentifier: "cz.matee.matee-starter",
        testFlightGroups: ["App Store Connect Users", "Public"],
        testFlightLink: "https://testflight.apple.com/join/lP4bFs1a",
        slackChannel: "#ci"
    )
}
