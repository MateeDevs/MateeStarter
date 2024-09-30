//
//  Created by Julia Jakubcova on 25/09/2024
//  Copyright © 2024 Matee. All rights reserved.
//

import KMPShared
import SwiftUI

struct ComposeViewController: UIViewControllerRepresentable {
    private let makeScreenViewController: () -> UIViewController
    
    init(makeScreenViewController: @escaping () -> UIViewController) {
        self.makeScreenViewController = makeScreenViewController
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        return makeScreenViewController()
    }
    
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
