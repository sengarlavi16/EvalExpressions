package com.community.evalexpressions.api.model

import com.google.gson.annotations.SerializedName

//Request Api Parameters
data class EvaluateRequest(
    @SerializedName("expr")
    val expressions: List<String>,
    @SerializedName("precision")
    val precision: String
)