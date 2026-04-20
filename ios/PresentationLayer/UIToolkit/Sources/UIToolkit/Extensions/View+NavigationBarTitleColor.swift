//
//  Created by Lukáš Matuška on 14.04.2026
//  Copyright © 2026 Matee. All rights reserved.
//

import SwiftUI

extension View {
    @ViewBuilder
    func navigationBarTitleColor(_ color: Color?) -> some View {
        if let color {
            self.background(NavBarTitleColorSetter(color: UIColor(color)))
        } else {
            self
        }
    }
}

private struct NavBarTitleColorSetter: UIViewControllerRepresentable {
    let color: UIColor

    func makeUIViewController(context: Context) -> NavBarColorVC {
        NavBarColorVC(color: color)
    }

    func updateUIViewController(_ vc: NavBarColorVC, context: Context) {
        vc.color = color
        vc.applyColor()
    }
}

private final class NavBarColorVC: UIViewController {
    var color: UIColor

    init(color: UIColor) {
        self.color = color
        super.init(nibName: nil, bundle: nil)
    }

    @available(*, unavailable)
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func didMove(toParent parent: UIViewController?) {
        super.didMove(toParent: parent)
        applyColor()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        applyColor()
    }

    func applyColor() {
        var host = parent
        while let current = host, current.navigationController == nil {
            host = current.parent
        }

        guard let host, let navBar = host.navigationController?.navigationBar else {
            return
        }

        let appearance = UINavigationBarAppearance(barAppearance: navBar.standardAppearance)
        appearance.titleTextAttributes[.foregroundColor] = color
        host.navigationItem.standardAppearance = appearance
        host.navigationItem.scrollEdgeAppearance = appearance
    }
}
