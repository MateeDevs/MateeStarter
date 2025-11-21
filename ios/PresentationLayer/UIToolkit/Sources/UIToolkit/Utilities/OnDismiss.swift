//
//  Created by Tomáš Batěk on 20.11.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import SwiftUI

/// Taken from https://swiftaddict.com/blog/on-dismiss
struct OnDismissRepresentable: UIViewControllerRepresentable {
    let onDismiss: (() -> Void)?

    func makeUIViewController(context: Context) -> ViewController {
        let controller = ViewController()
        controller.handler = onDismiss
        return controller
    }

    func updateUIViewController(_ uiViewController: ViewController, context: Context) {
        uiViewController.handler = onDismiss
    }

    class ViewController: UIViewController {
        var handler: (() -> Void)?
        var wasInNavigation = false
        var wasInPresentation = false

        override func viewDidAppear(_ animated: Bool) {
            super.viewDidAppear(animated)

            wasInNavigation = navigationController != nil
            wasInPresentation = presentingViewController != nil
        }

        override func viewDidDisappear(_ animated: Bool) {
            super.viewDidDisappear(animated)

            var needToCallHandler = parent == nil

            if wasInNavigation, navigationController == nil {
                needToCallHandler = true
            } else if wasInPresentation, presentingViewController == nil {
                needToCallHandler = true
            }

            if needToCallHandler {
                handler?()
            }
        }
    }
}
