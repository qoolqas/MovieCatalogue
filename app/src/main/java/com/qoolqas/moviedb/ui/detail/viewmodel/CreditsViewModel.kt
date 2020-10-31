package com.qoolqas.moviedb.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.credits.CastItem
import com.qoolqas.moviedb.model.credits.CreditsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreditsViewModel : ViewModel() {
    private var credit = MutableLiveData<MutableList<CastItem>>(mutableListOf())
    val api: String = BuildConfig.API_KEY

    fun init(language: String, id: Int) {
        loadSimiliar(language, id)
    }

    fun loadSimiliar(language: String, id: Int) {
        Client().getApi().getCredits(id, api, language)
            .enqueue(object : Callback<CreditsResponse> {
                override fun onFailure(call: Call<CreditsResponse>, t: Throwable) {
                    Log.d("failure discover", t.message.toString())
                }

                override fun onResponse(
                    call: Call<CreditsResponse>,
                    response: Response<CreditsResponse>
                ) {
                    if (response.isSuccessful) {
                        val respons: CreditsResponse? = response.body()
                        credit.postValue(credit.value?.apply { addAll(respons?.cast!!) })

                    } else {
                        Log.d("else", "Failure")
                    }

                }

            })
    }
    
    fun observerData(owner: LifecycleOwner, observer: Observer<MutableList<CastItem>>) =
        credit.observe(owner, observer)

}