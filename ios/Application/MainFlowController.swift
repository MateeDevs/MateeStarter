//
//  Created by Petr Chmelar on 13/04/2019.
//  Copyright © 2019 Matee. All rights reserved.
//

import KMPShared
import Sample
import SampleComposeMultiplatform
import SampleComposeNavigation
import SampleSharedViewModel
import SharedDomain
import SwiftUI
import UIKit
import UIToolkit

enum MainTab: Int {
    case sample = 0
    case sampleSharedViewModel = 1
    case sampleComposeMultiplatform = 2
    case sampleComposeNavigation = 3
}

final class MainFlowController: FlowController {
    
    override func setup() -> UIViewController {
        let main = UITabBarController()
        main.viewControllers = [setupSampleTab(), setupSampleSharedViewModelTab(), setupSampleComposeMultiplatformTab(), setupSampleComposeNavigationTab()]
        return main
    }
    
    private func setupSampleTab() -> UINavigationController {
        let sampleNC = BaseNavigationController(statusBarStyle: .lightContent)
        sampleNC.tabBarItem = UITabBarItem(
            title: MR.strings().bottom_bar_item_1.toLocalized(),
            image: AppTheme.Images.person,
            tag: MainTab.sample.rawValue
        )
        let sampleFC = SampleFlowController(navigationController: sampleNC)
        let sampleRootVC = startChildFlow(sampleFC)
        sampleNC.viewControllers = [sampleRootVC]
        return sampleNC
    }
    
    private func setupSampleSharedViewModelTab() -> UINavigationController {
        let sampleSharedViewModelNC = BaseNavigationController(statusBarStyle: .lightContent)
        sampleSharedViewModelNC.tabBarItem = UITabBarItem(
            title: MR.strings().bottom_bar_item_2.toLocalized(),
            image: AppTheme.Images.personCirle,
            tag: MainTab.sampleSharedViewModel.rawValue
        )
        let sampleSharedViewModelFC = SampleSharedViewModelFlowController(navigationController: sampleSharedViewModelNC)
        let sampleSharedViewModelRootVC = startChildFlow(sampleSharedViewModelFC)
        sampleSharedViewModelNC.viewControllers = [sampleSharedViewModelRootVC]
        return sampleSharedViewModelNC
    }
    
    private func setupSampleComposeMultiplatformTab() -> UINavigationController {
        let sampleComposeMultiplatformNC = BaseNavigationController(statusBarStyle: .lightContent)
        sampleComposeMultiplatformNC.tabBarItem = UITabBarItem(
            title: MR.strings().bottom_bar_item_3.toLocalized(),
            image: AppTheme.Images.personSquare,
            tag: MainTab.sampleComposeMultiplatform.rawValue
        )
        let sampleComposeMultiplatformFC = SampleComposeMultiplatformFlowController(navigationController: sampleComposeMultiplatformNC)
        let sampleComposeMultiplatformRootVC = startChildFlow(sampleComposeMultiplatformFC)
        sampleComposeMultiplatformNC.viewControllers = [sampleComposeMultiplatformRootVC]
        return sampleComposeMultiplatformNC
    }
    
    private func setupSampleComposeNavigationTab() -> UINavigationController {
        let sampleComposeNavigationNC = BaseNavigationController(statusBarStyle: .lightContent)
        sampleComposeNavigationNC.tabBarItem = UITabBarItem(
            title: MR.strings().bottom_bar_item_4.toLocalized(),
            image: AppTheme.Images.personTwo,
            tag: MainTab.sampleComposeNavigation.rawValue
        )
        let sampleComposeNavigationFC = SampleComposeNavigationFlowController(navigationController: sampleComposeNavigationNC)
        let sampleComposeNavigationRootVC = startChildFlow(sampleComposeNavigationFC)
        sampleComposeNavigationNC.viewControllers = [sampleComposeNavigationRootVC]
        return sampleComposeNavigationNC
    }
    
    @discardableResult private func switchTab(_ index: MainTab) -> FlowController? {
        guard let tabController = rootViewController as? UITabBarController,
            let tabs = tabController.viewControllers, index.rawValue < tabs.count else { return nil }
        tabController.selectedIndex = index.rawValue
        return childControllers[index.rawValue]
    }
}
