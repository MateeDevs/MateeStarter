//
//  Created by Julia Jakubcova on 25/11/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

public class SwiftUISampleComposeMultiplatformViewFactory: ComposeSampleComposeMultiplatformViewFactory {
    
    public init() {}
    
    public func createPlatformSpecificCheckboxView(
        text: String,
        checked: Bool,
        onCheckedChanged: @escaping (KotlinBoolean) -> Void
    ) -> KotlinPair<UIViewController, any PlatformSpecificCheckboxViewDelegate> {
        let observable = PlatformSpecificCheckboxViewObservable(checked: checked, onCheckedChanged: onCheckedChanged, text: text)
        let viewController: UIViewController = UIHostingController(rootView: CheckboxView(observable: observable))
        
        return KotlinPair(first: viewController, second: observable)
    }
    
    public func createPlatformSpecificBottomBar(
        items: [String],
        selected: String,
        onSelectedChanged: @escaping (String) -> Void,
        onSizeChanged: @escaping (KotlinFloat, KotlinFloat) -> Void
    ) -> KotlinPair<UIViewController, any PlatformSpecificBottomBarDelegate> {
        let observable = PlatformSpecificBottomBarObservable(
            items: items,
            selected: selected,
            onSelectedChanged: onSelectedChanged,
            onSizeChanged: onSizeChanged
        )
        let viewController: UIViewController = TransparentHostingController(
            rootView: BottomBarView(observable: observable)
        )
        
        return KotlinPair(first: viewController, second: observable)
    }
    
    public func createScreenWithPlatformSpecificBottomBar(
        tabs: [String: UIViewController],
        selectedTab: String,
        onSelectedTabChanged: @escaping (String) -> Void
    ) -> KotlinPair<UIViewController, any ScreenWithPlatformSpecificBottomBarDelegate> {
        let observable = ScreenWithPlatformSpecificBottomBarObservable(
            tabs: tabs,
            selectedTab: selectedTab,
            onSelectedTabChanged: onSelectedTabChanged
        )
        let viewController: UIViewController = UIHostingController(
            rootView: ScreenWithBottomBarView(observable: observable)
        )
        
        return KotlinPair(first: viewController, second: observable)
    }
}
