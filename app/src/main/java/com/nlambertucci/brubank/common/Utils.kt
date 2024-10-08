package com.nlambertucci.brubank.common

import android.view.View
import com.google.gson.Gson
import com.nlambertucci.brubank.domain.model.Movie

/**
 * Extension that convert Movies in a Json
 */
fun Movie.toJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}

/**
 * Extension that convert Strings in a Movie
 */
fun String.toMovie(): Movie {
    val gson = Gson()
    return gson.fromJson(this, Movie::class.java)
}

/**
 * Extension for all View-type components that allows handling their visibility using booleans.
 **/
var View.isVisible: Boolean
    get() {
        return this.visibility == View.VISIBLE
    }
    set(value) {
        this.visibility = if (value) View.VISIBLE else View.GONE
    }