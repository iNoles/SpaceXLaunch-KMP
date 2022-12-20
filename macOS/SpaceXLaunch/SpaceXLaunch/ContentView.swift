//
//  ContentView.swift
//  SpaceXLaunch
//
//  Created by Jonathan Steele on 12/3/22.
//

import SwiftUI
import shared
import KMPNativeCoroutinesAsync

struct ContentView: View {
    private let titles = ["Latest", "Upcoming"]
    
    @State private var selection: Launch.Doc? = nil // Nothing selected by default.
    @State private var selectedTab = 0
    @State private var launchDocs = [Launch.Doc]()
    
    var body: some View {
        VStack {
            HStack {
                Spacer()
                Picker("", selection: $selectedTab) {
                    ForEach(0 ..< titles.count, id: \.self) { index in
                        Text(self.titles[index]).tag(index)
                    }
                }
                .pickerStyle(SegmentedPickerStyle())
                .padding(.top, 8)
                Spacer()
            }
            .padding(.horizontal, 100)
            Divider()
            
            let filter = filterLaunchByUpcoming(doc: launchDocs)
            let sorted = filter.sorted { first, second in
                return first.flightNumber > second.flightNumber
            }
            
            NavigationSplitView {
                List(sorted, id: \.id, selection: $selection) { launch in
                    NavigationLink(launch.name, value: launch)
                }
            } detail: {
                if let launch = selection {
                    LaunchDetail(doc: launch)
                } else {
                    Text("Pick a launch")
                }
            }
        }.task {
            do {
                let stream = asyncStream(for: SpaceXAPI().fetchAllLaunchesNative())
                for try await docs in stream {
                    launchDocs = docs!
                }
            } catch {
                print("Failed with error: \(error)")
            }
        }
    }
    
    func filterLaunchByUpcoming(doc: [Launch.Doc]) -> [Launch.Doc] {
        switch(selectedTab) {
        case 0:
            return doc.filter { $0.upcoming == false }
        case 1:
            return doc.filter { $0.upcoming == true }
        default:
            return doc
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
