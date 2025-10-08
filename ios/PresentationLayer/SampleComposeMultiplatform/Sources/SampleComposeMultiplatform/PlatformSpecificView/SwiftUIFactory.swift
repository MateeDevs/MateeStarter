//
//  Created by Julia Jakubcova on 25/11/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

public class SwiftUIViewFactory: ComposeViewFactory {
    
    public init() {}
    
    public func createNativeScaffold(
        toolbar: Toolbar?,
        tabs: [TabItem],
        selectedTabPosition: Int32,
        onTabSelected: @escaping (KotlinInt) -> Void,
        content: @escaping (KotlinInt?) -> UIViewController
    ) -> KotlinPair<UIViewController, any NativeScaffoldDelegate> {
        let observable = NativeScaffoldObservable(
            toolbar: toolbar,
            tabs: tabs,
            selectedTab: selectedTabPosition,
            onSelectedTabChanged: { onTabSelected(KotlinInt(value: $0)) },
            content: { position in content(position.map { KotlinInt(value: $0) }) }
        )
        let viewController: UIViewController = UIHostingController(
            rootView: NativeScaffoldView(observable: observable)
        )
        
        return KotlinPair(first: viewController, second: observable)
    }
}
