//
//  DeviceTableViewCell.swift
//  NPDashboard
//
//  Created by Michael Bickerton on 10/13/17.
//  Copyright © 2017 Team SmartThings. All rights reserved.
//

import UIKit

class DeviceTableViewCell: UITableViewCell {

    @IBOutlet weak var nameLabel: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
