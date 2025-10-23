//
//  Created by Tomáš Batěk on 23.10.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import SwiftUI

@main
struct MateeStarterApp: App {
    
    @UIApplicationDelegateAdaptor private var appDelegate: AppDelegate
    
    var body: some Scene {
        WindowGroup {
            AppRootView()
        }
    }
}
