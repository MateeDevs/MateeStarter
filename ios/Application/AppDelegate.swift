//
//  Created by Petr Chmelar on 16/07/2018.
//  Copyright © 2018 Matee. All rights reserved.
//

#if ALPHA
import Atlantis
#endif

import DependencyInjection
import Factory
import KeychainProvider
import OSLog
import SharedDomain
import UIKit
import UIToolkit
import UserDefaultsProvider
import Utilities
import WidgetKit

final class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        
        setupEnvironment()
        
        // Clear keychain on first run
        clearKeychain()
        
        // Setup firebase for debug
        firebaseDebugSetup()
        
        // Setup Cache capacity
        setupCacheCapacity()
        
        // Register for remote notifications
        application.registerForRemoteNotifications()
        
        // Setup appearance
        setupAppearance()
        
        return true
    }
    
    // MARK: Setup environment
    private func setupEnvironment() {
        #if ALPHA
        Atlantis.start()
        Environment.api = .alpha
        Logger.app.info("ALPHA environment")
        #elseif PRODUCTION
        Environment.api = .production
        Logger.app.info("PRODUCTION environment")
        #endif
        
        #if DEBUG
        Environment.build = .debug
        #else
        Environment.build = .release
        #endif
    }
    
    // MARK: Clear keychain
    private func clearKeychain() {
        do {
            let _: Bool = try Container.shared.userDefaultsProvider().read(.hasRunBefore)
        } catch UserDefaultsProviderError.valueForKeyNotFound {
            do {
                try Container.shared.keychainProvider().deleteAll()
                try Container.shared.userDefaultsProvider().update(.hasRunBefore, value: true)
            } catch {}
        } catch {}
    }
    
    // MARK: Firebase debug setup
    private func firebaseDebugSetup() {
        // Enable Firebase Analytics debug mode for non production environments
        // Idea taken from: https://stackoverflow.com/a/47594030/6947225
        if Environment.api != .production {
            var args = ProcessInfo.processInfo.arguments
            args.append("-FIRAnalyticsDebugEnabled")
            ProcessInfo.processInfo.setValue(args, forKey: "arguments")
        }
    }
    
    // MARK: Cache setup
    private func setupCacheCapacity() {
        URLCache.shared.memoryCapacity = 10_000_000 // ~10 MB memory space
        URLCache.shared.diskCapacity = 1_000_000_000 // ~1GB disk cache space
    }
    
    // MARK: Setup appearance
    private func setupAppearance() {
        // Navigation bar
        let appearance = UINavigationBarAppearance()
        appearance.backgroundColor = UIColor(AppTheme.Colors.navBarBackground)
        appearance.titleTextAttributes = [NSAttributedString.Key.foregroundColor: UIColor(AppTheme.Colors.navBarTitle)]
        UINavigationBar.appearance().standardAppearance = appearance
        UINavigationBar.appearance().scrollEdgeAppearance = appearance
        UINavigationBar.appearance().tintColor = UIColor(AppTheme.Colors.navBarTitle)

        // Tab bar
        UITabBar.appearance().tintColor = UIColor(AppTheme.Colors.primaryColor)

        // UITextField
        UITextField.appearance().tintColor = UIColor(AppTheme.Colors.primaryColor)
    }
}
