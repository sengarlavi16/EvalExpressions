package com.community.evalexpressions.api

import com.community.evalexpressions.api.model.EvaluateRequest
import com.community.evalexpressions.api.model.EvaluateResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    /* Evaluate Expression Api*/
    @POST("evaluate")
    suspend fun evaluateExpressions(@Body request: EvaluateRequest): EvaluateResponse

}