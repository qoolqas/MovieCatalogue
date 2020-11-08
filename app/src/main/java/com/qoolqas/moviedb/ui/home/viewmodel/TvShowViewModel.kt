package com.qoolqas.moviedb.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.popular.PopularResultsItem
import com.qoolqas.moviedb.model.tv.TvPopularItem
import com.qoolqas.moviedb.model.tv.TvPopularResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvShowViewModel : ViewModel() {
    private val popular = MutableLiveData<List<TvPopularItem>>()
    private val api: String = BuildConfig.API_KEY

    fun init(page: Int,language : String){
        loadPopular(page, language)
    }
    private fun loadPopular(page: Int, language : String) {
        Client().getApi().getTvPopular(api, page,language)
            .enqueue(object : Callback<TvPopularResponse> {
                override fun onFailure(call: Call<TvPopularResponse>, t: Throwable) {
                    Log.d("failure popular", t.message.toString())
                }

                override fun onResponse(
                    call: Call<TvPopularResponse>,
                    response: Response<TvPopularResponse>
                ) {
                    if (response.isSuccessful){
                        val respons:TvPopularResponse? = response.body()
                        popular.postValue(respons?.results)
                    }else{
                        Log.d("else" ,"Failure")
                    }

                }

            })
    }
    fun livePopular(): LiveData<List<TvPopularItem>> {
           return popular
    }
}