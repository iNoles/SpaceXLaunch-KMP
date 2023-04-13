//
//  LaunchList.swift
//  iosApp
//
//  Created by Jonathan Steele on 10/30/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared
import KMPNativeCoroutinesAsync

struct LaunchList: View {
    @State private var selectorIndex = 0
    @State private var launchDocs = [Launch.Doc]()
    private let titles = ["Latest", "Upcoming"]
    
    var body: some View {
        NavigationStack {
            VStack {
                Picker("Launches", selection: $selectorIndex) {
                    ForEach(0 ..< titles.count, id: \.self) { index in
                        Text(self.titles[index]).tag(index)
                    }
                }.pickerStyle(SegmentedPickerStyle())
                
                let filter = filterLaunchByUpcoming(doc: launchDocs)
                let sorted = filter.sorted { first, second in
                    return first.flightNumber > second.flightNumber
                }
                List(sorted, id: \.id) { launch in
                    NavigationLink(destination: LaunchDetail(doc: launch)) {
                        LaunchRow(doc: launch)
                    }
                }
            }.navigationTitle("SpaceX Launches")
        }.task {
            do {
                let stream = asyncSequence(for: SpaceXAPI().fetchAllLaunchesNative())
                for try await docs in stream {
                    launchDocs = docs!
                }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    }
    
    func filterLaunchByUpcoming(doc: [Launch.Doc]) -> [Launch.Doc] {
        switch(selectorIndex) {
        case 0:
            return doc.filter { $0.upcoming == false }
        case 1:
            return doc.filter { $0.upcoming == true }
        default:
            return doc
        }
    }
}

struct LaunchList_Previews: PreviewProvider {
    static var previews: some View {
        LaunchList()
    }
}
