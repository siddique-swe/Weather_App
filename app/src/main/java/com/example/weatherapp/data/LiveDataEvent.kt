package com.example.weatherapp.data

data class LiveDataEvent<out T>(private  val content : T){
    private  var hasBeenHandled = false

    fun getContentInfoNoHandled() : T? {
        return if (hasBeenHandled){
            null
        }else {
            hasBeenHandled = true
            content
        }
    }
}
