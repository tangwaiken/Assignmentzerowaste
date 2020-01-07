package com.example.assignment

class FoodEntity(id: String, itemImage: String, owner: String, itemName: String, description: String, pickupTime: String, location: String) {

    var itemImage: String
        internal set
    var owner: String
        internal set
    var itemName: String
        internal set
    var description: String
        internal set
    var pickupTime: String
        internal set
    var location: String
        internal set

    init {
        this.itemImage = itemImage
        this.owner = owner
        this.itemName = itemName
        this.description = description
        this.pickupTime = pickupTime
        this.location = location
    }
    constructor():this("","","","","","",""){

    }
}
