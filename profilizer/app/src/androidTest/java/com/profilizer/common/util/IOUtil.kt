package com.profilizer.common.util

import android.content.Context

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

object IOUtil {

    @Throws(IOException::class)
    private fun readStream(`is`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        reader.use { reader ->
            val sb = StringBuilder()
            reader.forEachLine { line ->
                sb.append(line).append("\n")
            }
            return sb.toString()
        }
    }

    @Throws(IOException::class)
    fun readAsset(context: Context, filePath: String): String {
        val stream = context.resources.assets.open(filePath)

        stream.use { stream ->
            return readStream(stream)
        }
    }
}// no-instance
