package com.profilizer.common.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException
import java.util.*

class DateJsonAdpater : JsonAdapter<Date>() {

    override fun toJson(writer: JsonWriter?, value: Date?) {
        writer?.value(value?.time)
    }

    @Synchronized
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Date? {
        val string = reader.nextString()
        string.let {
            return Date(string.toLong())
        }
    }
}