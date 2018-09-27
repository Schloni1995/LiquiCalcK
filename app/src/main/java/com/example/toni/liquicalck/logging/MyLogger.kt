package com.example.toni.liquicalck.logging

import android.util.Log

/**
 * Created by Toni on 07.03.2018.
 */

class MyLogger {
    fun outInfo(tag: String, message: Array<String>) {
        var completeMsg = ""
        for (msg in message) {
            completeMsg += "[$msg];"
        }

        Log.i(tag, completeMsg)
    }

    fun outInfo(tag: String, message: String) {
        Log.i(tag, message)
    }
}
