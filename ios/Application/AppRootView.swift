//
//  Created by Tomáš Batěk on 22.10.2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import NavigatorUI
import Sample
import SampleComposeMultiplatform
import SampleComposeNavigation
import SampleSharedViewModel
import SwiftUI
import UIToolkit

struct AppRootView: View {
    
    @StateObject private var sampleViewModel = SampleViewModel()
    
    private let navigator = Navigator(
        configuration: NavigationConfiguration()
    )
    
    var body: some View {
        TabView {
            sampleTab
            
            sampleSharedViewModelTab
            
            sampleComposeMultiplatformTab
            
            sampleComposeNavigationTab
        }
        .environment(\.navigator, navigator)
    }
    
    @ViewBuilder
    private var sampleTab: some View {
        SampleView(viewModel: sampleViewModel)
            .tabItem {
                Image(uiImage: AppTheme.Images.person)
                Text(MR.strings().bottom_bar_item_1.toLocalized())
            }
    }
    
    @ViewBuilder
    private var sampleSharedViewModelTab: some View {
        SampleSharedViewModelRootView()
            .tabItem {
                Image(uiImage: AppTheme.Images.personCirle)
                
                Text(MR.strings().bottom_bar_item_2.toLocalized())
            }
    }
    
    @ViewBuilder
    private var sampleComposeMultiplatformTab: some View {
        SampleComposeMultiplatformView()
            .tabItem {
                Image(uiImage: AppTheme.Images.personSquare)
                
                Text(MR.strings().bottom_bar_item_3.toLocalized())
            }
    }
    
    @ViewBuilder
    private var sampleComposeNavigationTab: some View {
        SampleComposeNavigationView()
            .tabItem {
                Image(uiImage: AppTheme.Images.personTwo)
                
                Text(MR.strings().bottom_bar_item_4.toLocalized())
            }
    }
}
