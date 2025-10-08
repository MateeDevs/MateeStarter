//
//  Created by Julia Jakubcova on 02/10/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import KMPShared
import SwiftUI
import UIToolkit

struct ScreenWithBottomBarView: View {
    @ObservedObject var observable: ScreenWithPlatformSpecificBottomBarObservable
    
    init(observable: ScreenWithPlatformSpecificBottomBarObservable) {
        self.observable = observable
    }
    
    var body: some View {
        if #available(iOS 16.0, *) {
            NavigationStack {
                TabView(selection: $observable.selectedTab) {
                    ForEach(observable.tabs, id: \.position) { tab in
                        Group {
                            if #available(iOS 26.0, *) {
                                ComposeViewController { tab.content }
                                    .ignoresSafeArea()
                            } else {
                                ComposeViewController { tab.content }
                            }
                            
                            //                            ScrollView {
                            //                                ForEach((0...20), id: \.self) { _ in
                            //                                    Text("doiaw jmo dcposekoif eswop[ijj fe ss")
                            //                                    Color.red
                            //                                        .frame(height: 100)
                            //                                }
                            //                            }
                        }
                        .tabItem {
                            if let uiImage = tab.icon.toUIImage() {
                                Image(uiImage: uiImage)
                            }
                            Text(tab.title)
                        }
                        .tag(tab.position)
                        
                    }
                }
                .navigationTitle("Items")
//                .toolbarTitleDisplayMode(.inline)
                .navigationBarTitleDisplayMode(.inline)
                .toolbarBackground(.hidden, for: .navigationBar)
                .toolbar {
                    ToolbarItem(placement: .topBarLeading) {
                        Button("Cancel", systemImage: "xmark") {
                            // cancel action
                        }
                        .tint(.red)
                    }
                    
                    ToolbarItem(placement: .topBarTrailing) {
                        Button("Done", systemImage: "checkmark") {
                            // done action
                        }
                        .badge(3)
                    }
                }
            }
        } else {
            // Fallback on earlier versions
        }
    }
}
