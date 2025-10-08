//
//  Created by Julia Jakubcova on 08/10/2025
//  Copyright © 2025 Matee. All rights reserved.
//

import SwiftUI

public struct TestScreen: View {
    
    @State private var selectedTab = 0
    
    public init() {}
    
    public var body: some View {
        if #available(iOS 16.0, *) {
            NavigationStack {
                TabView(selection: $selectedTab) {
                    ForEach((0...2), id: \.self) { tab in
                        ScrollView {
                            ForEach((0...20), id: \.self) { index in
                                Text("This is item number \(index)")
                                    .font(.largeTitle)
                                Text("And this is it's body")
                            }
                        }
                        .tabItem {
                            Image(systemName: "xmark")
                            Text("Tab \(tab)")
                        }
                        .tag(tab)
                        
                    }
                }
                .navigationTitle("Title")
                .navigationBarTitleDisplayMode(.inline)
                .toolbarBackground(.hidden, for: .navigationBar)
                .toolbar {
                    ToolbarItemGroup(placement: .topBarLeading) {
                        Button("First", systemImage: "search") {
                            
                        }
                        .tint(.red)
                    }
                
                    ToolbarItemGroup(placement: .topBarTrailing) {
                        Button("Second", systemImage: "person") {
                            
                        }
                    }
                    
                    ToolbarItemGroup(placement: .topBarTrailing) {
                        Button("Second", systemImage: "person") {
                            
                        }
                    }
                }
            }
        } else {
            // Fallback on earlier versions
        }
    }
}
