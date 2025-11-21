//
//  Created by Tomáš Batěk on 23.10.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import SwiftUI
import WidgetKit

@main
struct MateeStarterApp: App {
    
    @Environment(\.scenePhase) private var scenePhase
    
    @UIApplicationDelegateAdaptor private var appDelegate: AppDelegate
    
    var body: some Scene {
        WindowGroup {
            AppRootView()
        }
        .onChange(of: scenePhase) { phase in
            switch phase {
            case .background:
                WidgetCenter.shared.reloadAllTimelines()
            default: break
            }
        }
    }
}
