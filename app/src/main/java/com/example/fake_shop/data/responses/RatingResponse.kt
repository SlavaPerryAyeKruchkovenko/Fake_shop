package com.example.fake_shop.data.responses

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("rate") val rate: Double,
    @SerializedName("count") val title: Int,
)
