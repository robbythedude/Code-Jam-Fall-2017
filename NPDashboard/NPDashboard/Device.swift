//
//  Device.swift
//  NPDashboard
//
//  Created by Michael Bickerton on 10/13/17.
//  Copyright © 2017 Team SmartThings. All rights reserved.
//

import Foundation
import UIKit

class Device {
    var name: String
    var image: UIImage
    
    init(_ name: String, _ image: UIImage) {
        self.name = name
        self.image = image
    }
}
