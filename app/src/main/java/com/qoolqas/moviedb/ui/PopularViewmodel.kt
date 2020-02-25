package com.qoolqas.moviedb.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.PopularMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PopularViewmodel : ViewModel() {
    private val popular = MutableLiveData<PopularMovieResponse>()
    val api: String = "8a9be9d05bd9f9d5ca154aaf42504303"

    fun init(page: Int){
        loadPopular(page)
    }
    fun loadPopular(page: Int) {
        Client().getApi().getPopular(api, page)
            .enqueue(object : Callback<PopularMovieResponse> {
                override fun onFailure(call: Call<PopularMovieResponse>, t: Throwable) {
                    Log.d("failure popular", t.message.toString())
                }

                override fun onResponse(
                    call: Call<PopularMovieResponse>,
                    response: Response<PopularMovieResponse>
                ) {
                    if (response.isSuccessful){
                        val respons:PopularMovieResponse? = response.body()
                        popular.postValue(respons)
                    }else{
                        Log.d("else" ,"Failure")
                    }

                }

            })
    }
    fun livePopular(): LiveData<PopularMovieResponse> {
           return popular
    }
}