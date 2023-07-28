package com.community.evalexpressions.api.viewmodel

import androidx.lifecycle.*
import com.community.evalexpressions.api.ApiService
import com.community.evalexpressions.api.model.EvaluateRequest
import com.community.evalexpressions.api.model.EvaluateResponse
import com.community.evalexpressions.database.HistoryData
import com.community.evalexpressions.utils.CustomProgressDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class EvaluateViewModel : ViewModel() {

    //initializing all the needed classes and parameters
    private val apiBaseUrl = "https://api.mathjs.org/v4/"
    private val apiService: ApiService

    private val _evaluate = MutableLiveData<EvaluateResponse>()
    val evaluate: LiveData<EvaluateResponse>
        get() = _evaluate

    private val _history = MutableLiveData<List<HistoryData>>()
    val history: LiveData<List<HistoryData>>
        get() = _history

    private val historyItems = mutableListOf<HistoryData>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    //running and initializing the api using retrofit
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun evaluateExpressions(expressions: List<String>, progressDialog: CustomProgressDialog) {
        //loading progress bar
        _isLoading.postValue(true)
        progressDialog.showDialog()

        viewModelScope.launch(Dispatchers.IO) {

            //sending the request
            val request = EvaluateRequest(expressions, "")
            try{
                val response = apiService.evaluateExpressions(request)

                //evaluating the request
                if (response.error == null) {

                    _evaluate.postValue(response)

                    //passing in history database
                    for (i in expressions.indices) {
                        val expression = expressions[i]
                        val result = response.results[i]
                        historyItems.add(HistoryData(expression, expression, result))
                    }

                    _history.postValue(historyItems.toList())
                    _isLoading.postValue(false)
                    progressDialog.dismiss()
                } else {
                    _evaluate.postValue(response)
                }
            }catch (e:Exception){
                //handling the exception and error
                _isLoading.postValue(false)
                progressDialog.dismiss()
                _evaluate.postValue(EvaluateResponse(emptyList(), e.message))
            }
        }
    }
}