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
                                VStack(alignment: .leading) {
                                    Text("This is item number \(index)")
                                        .font(.largeTitle)
                                        .padding([.top, .horizontal])

                                    Text("And this is it's body")
                                        .padding([.bottom, .horizontal])
                                }
                                .frame(maxWidth: .infinity)
                                .background(.gray.opacity(0.5))
                                .clipShape(.rect(cornerRadius: 8))
                                .padding(.horizontal)
                            }
                        }
                        .tabItem {
                            Image(systemName: image(index: tab))
                            Text(text(index: tab))
                        }
                        .tag(tab)
                        
                    }
                }
                .navigationTitle("Title")
                .navigationBarTitleDisplayMode(.inline)
                .toolbarBackground(.hidden, for: .navigationBar)
                .toolbar {
                    ToolbarItemGroup(placement: .topBarLeading) {
                        Button("First", systemImage: "magnifyingglass") {
                            
                        }
                        .tint(.red)
                    }
                
                    ToolbarItemGroup(placement: .topBarTrailing) {
                        Button("Second", systemImage: "person") {
                            
                        }
                    }
                    
                    ToolbarItemGroup(placement: .topBarTrailing) {
                        Button("Second", systemImage: "house") {
                            
                        }
                        .tint(.yellow)
                    }
                }
            }
        } else {
            // Fallback on earlier versions
        }
    }
    
    func image(index: Int) -> String {
        switch index {
        case 0: return "house"
        case 1: return "magnifyingglass"
        default: return "person"
        }
    }
    
    func text(index: Int) -> String {
        switch index {
        case 0: return "Numbers"
        case 1: return "Sentences"
        default: return "Images"
        }
    }
}
