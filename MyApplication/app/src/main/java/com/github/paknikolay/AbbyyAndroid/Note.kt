package com.github.paknikolay.AbbyyAndroid;

import android.content.Context;
import android.graphics.drawable.Drawable
import java.text.SimpleDateFormat
import java.util.Date

class Note ( val id:Long, var data:Date, var text:String, private val context:Context ) {
    constructor( id:Long, context:Context )
            : this
        (
        id,
        //странно, но бд пока у нас нет, так, что пока все в ресурсах
        //находим в ресурсах строку с датой
        SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse( context.resources.getString( context.resources.getIdentifier("date_" + id.toString(), "string", context.packageName ) ) ),
        //находим в ресурсвх строку с текстом
        context.resources.getString( context.resources.getIdentifier("text_" + id.toString(), "string", context.packageName ) ),
        context
        )

    fun GetImage() : Drawable {
        val im_id =  context.resources.getIdentifier("image_" + id.toString(), "drawable", context.packageName)
        return context.resources.getDrawable(im_id)
    }
}
