package com.github.yasushi.hansel

import android.arch.persistence.room.TypeConverter
import android.net.Uri

// TODO: this is a bit unnecessary...
class UriToStringConverter {
    @TypeConverter
    fun uriToString(uri: Uri): String = uri.toString()

    @TypeConverter
    fun stringToUri(uriString: String): Uri = Uri.parse(uriString)
}