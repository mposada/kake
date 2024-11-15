package com.mposadar.kake.cats.data

import com.google.gson.annotations.SerializedName

class CatDataModel(
    @SerializedName("_id")
    val id: String,
    val mimetype: String,
    val size: Int,
    val tags: List<String>
)