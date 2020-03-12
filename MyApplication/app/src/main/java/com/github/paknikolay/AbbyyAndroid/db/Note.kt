package com.github.paknikolay.AbbyyAndroid.db;

import java.util.Date

class Note {
    var id: Long
    var data: Date
    var text: String
    var drawableId: Int
    constructor() {
        id = 0;
        data = Date();
        text = "shiba"
        drawableId = 0;
    }
    constructor(_id : Long, _data : Date, _text : String, _drawableId : Int) {
        id = _id;
        data = _data;
        text = _text;
        drawableId = _drawableId;
    }
}


