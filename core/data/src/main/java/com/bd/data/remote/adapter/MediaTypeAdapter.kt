package com.bd.data.remote.adapter

import com.bd.data.remote.model.MediaDto
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class MediaTypeAdapter : TypeAdapter<MediaDto>() {

    override fun write(out: JsonWriter?, value: MediaDto?) {
        throw Exception("Not supported")
    }

    override fun read(input: JsonReader): MediaDto? {
        val gson = Gson()
        val jsonElement = gson.fromJson<JsonElement>(input, JsonElement::class.java)
        val jsonObject = jsonElement.asJsonObject

        return when (val mediaType = jsonObject.get("media_type")?.asString) {
            "movie" -> gson.fromJson(jsonObject, MediaDto.Movie::class.java)
            "tv" -> gson.fromJson(jsonObject, MediaDto.TVShow::class.java)
            else -> throw JsonParseException("Unknown media type: $mediaType")
        }
    }
}
