package com.darkmoose117.scryfall

import org.junit.Assert
import java.io.File

fun Any.loadFileIntoString(fileName: String): String {
    val classLoader = this.javaClass.classLoader
    val file = classLoader.getResource(fileName)?.let { url ->
        File(url.file)
    }
    Assert.assertNotNull(file)
    assert(file!!.exists())

    val jsonAsString = file.readText()
    assert(jsonAsString.isNotBlank())

    return jsonAsString
}