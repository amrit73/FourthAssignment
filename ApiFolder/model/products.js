const mongoose = require('mongoose');
const Items = mongoose.model('Items', {
    itemName: {
        type: String
    },
    itemPrice: {
        type: String
    },
    itemDescription: {
        type: String
    },
    itemImageName: {
        type: String
    }
})

module.exports = Items