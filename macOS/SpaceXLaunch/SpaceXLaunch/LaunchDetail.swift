//
//  LaunchDetail.swift
//  iosApp
//
//  Created by Jonathan Steele on 10/29/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LaunchDetail: View {
    var doc: Launch.Doc
    
    var body: some View {
        NavigationStack {
            List {
                Section {
                    LaunchHeader(doc: doc)
                }
                
                Section("Payload") {
                    let payloads = doc.payloads.first
                    LaunchContent(title: "Reused", subtitle: payloads!.reused.description)
                    LaunchContent(title: "Manufacturer", subtitle: payloads!.manufacturers.joined())
                    LaunchContent(title: "Customer", subtitle: payloads!.customers.joined())
                    LaunchContent(title: "Nationality", subtitle: payloads!.nationalities.joined())
                    LaunchContent(title: "Orbit", subtitle: payloads!.orbit!)
                    LaunchContent(title: "Periapsis", subtitle: payloads?.periapsisKm?.stringValue ?? "Unknown")
                    LaunchContent(title: "Apoapsis", subtitle: payloads?.apoapsisKm?.stringValue ?? "Unknown")
                    LaunchContent(title: "Inclination", subtitle: payloads?.inclinationDeg?.stringValue ?? "Unknown")
                    LaunchContent(title: "Period", subtitle: payloads?.periodMin?.stringValue ?? "Unknown")
                }
            }
            .listStyle(.inset)
        }
    }
}

struct LaunchHeader: View {
    var doc: Launch.Doc
    
    var body: some View {
        HStack {
            if let small = doc.links.patch.small {
                AsyncImage(url: URL(string: small)) { image in
                    image.image?.resizable().frame(width: 64, height: 64)
                }
            }
            VStack(alignment: .leading) {
                Text(doc.name).font(.headline)
                Text(Date(timeIntervalSince1970: TimeInterval(doc.dateUtc.epochSeconds)), style: .date).font(.subheadline)
                Text(doc.launchpad.name).font(.subheadline)
            }
        }
    }
}

struct LaunchContent: View {
    var title: String
    var subtitle: String
    
    var body: some View {
        HStack {
            Text(title).font(.headline)
            Spacer()
            Text(subtitle).font(.subheadline)
        }
    }
}

struct LaunchDetail_Previews: PreviewProvider {
    static var previews: some View {
        let doc = Launch.Doc(
            cores: [Launch.DocCore(
                core: Launch.DocCoreCore(
                    asdsAttempts: 0,
                    asdsLandings: 0,
                    block: nil,
                    id: "5e9e289df35918033d3b2623",
                    lastUpdate: "Engine failure at T+33 seconds resulted in loss of vehicle",
                    reuseCount: 0,
                    rtlsAttempts: 0,
                    rtlsLandings: 0,
                    serial: "Merlin1A",
                    status: "lost"
                ),
                flight: 1,
                gridfins: false,
                landingAttempt: false,
                landingSuccess: nil,
                landingType: nil,
                landpad: nil,
                legs: false,
                reused: false
            )],
            datePrecision: "hour",
            dateUtc: Kotlinx_datetimeInstant.Companion().parse(isoString: "2006-03-24T22:30:00.000Z"),
            details: "Engine failure at 33 seconds and loss of vehicle",
            failures: [Launch.DocFailure(
                altitude: nil,
                reason: "merlin engine failure",
                time: 33
            )],
            fairings: Launch.DocFairings(
                recovered: false,
                recoveryAttempt: false,
                reused: false
            ),
            flightNumber: 1,
            id: "5eb87cd9ffd86e000604b32a",
            launchLibraryId: nil,
            launchpad: Launch.DocLaunchpad(
                details: "SpaceX's original pad, where all of the Falcon 1 flights occurred (from 2006 to 2009). It would have also been the launch site of the Falcon 1e and the Falcon 9, but it was abandoned as SpaceX ended the Falcon 1 program and decided against upgrading it to support Falcon 9, likely due to its remote location and ensuing logistics complexities.",
                fullName: "Kwajalein Atoll Omelek Island",
                id: "5e9e4502f5090995de566f86",
                latitude: 9.0477206,
                launchAttempts: 5,
                launchSuccesses: 2,
                locality: "Omelek Island",
                longitude: 167.7431292,
                name: "Kwajalein Atoll",
                region: "Marshall Islands",
                status: "retired"
            ),
            links: Launch.DocLinks(
                patch: Launch.DocLinksPatch(
                    small: "https://images2.imgbox.com/94/f2/NN6Ph45r_o.png"
                ),
                presskit: nil,
                webcast: "https://www.youtube.com/watch?v=0a_00nJ_Y88"
            ),
            name: "FalconSat",
            payloads: [Launch.DocPayload(
                apoapsisKm: 500.0,
                customers: ["DARPA"],
                dragon: Launch.DocPayloadDragon(
                    capsule: nil
                ),
                id: "5eb0e4b5b6c3bb0006eeb1e1",
                inclinationDeg: 39.0,
                manufacturers: ["SSTL"],
                massKg: 20.0,
                nationalities: ["United States"],
                orbit: "LEO",
                periapsisKm: 400.0,
                periodMin: nil,
                reused: false
            )],
            rocket: Launch.DocRocket(
                id: "5e9d0d95eda69955f709d1eb",
                name: "Falcon1"
            ),
            staticFireDateUtc: Kotlinx_datetimeInstant.Companion().parse(isoString: "2006-03-17T00:00:00.000Z"),
            success: false,
            upcoming: false,
            window: 0
        )
        LaunchDetail(doc: doc)
    }
}
