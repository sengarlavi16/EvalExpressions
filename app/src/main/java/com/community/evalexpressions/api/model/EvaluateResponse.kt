package com.community.evalexpressions.api.model

import com.google.gson.annotations.SerializedName

//Api Response
data class EvaluateResponse(
    @SerializedName("result")
    val results: List<String>,
    @SerializedName("error")
    val error: String?)
