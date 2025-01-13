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
}
