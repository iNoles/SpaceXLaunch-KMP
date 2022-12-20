//
//  ColorDetail.swift
//  SpaceXLaunch
//
//  Created by Jonathan Steele on 12/3/22.
//

import SwiftUI

struct ColorDetail: View {
    var color: Color
    
    var body: some View {
        Text(color.description)
    }
}

struct ColorDetail_Previews: PreviewProvider {
    static var previews: some View {
        ColorDetail(color: Color.purple)
    }
}
