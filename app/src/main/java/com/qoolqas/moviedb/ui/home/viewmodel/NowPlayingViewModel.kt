package com.qoolqas.moviedb.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.nowplaying.NowPlayingResponse
import com.qoolqas.moviedb.model.nowplaying.NowPlayingResultsItem
import com.qoolqas.moviedb.model.popular.PopularMovieResponse
import com.qoolqas.moviedb.model.popular.PopularResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NowPlayingViewModel : ViewModel() {
    private val popular = MutableLiveData<List<NowPlayingResultsItem>>()
    private val api: String = BuildConfig.API_KEY

    fun init(page: Int){
        loadNowPlaying(page)
    }
    private fun loadNowPlaying(page: Int) {
        Client().getApi().getNowPlaying(api, page)
            .enqueue(object : Callback<NowPlayingResponse> {
                override fun onFailure(call: Call<NowPlayingResponse>, t: Throwable) {
                    Log.d("failure popular", t.message.toString())
                }

                override fun onResponse(
                    call: Call<NowPlayingResponse>,
                    response: Response<NowPlayingResponse>
                ) {
                    if (response.isSuccessful){
                        val respons:NowPlayingResponse? = response.body()
                        popular.postValue(respons?.results)
                    }else{
                        Log.d("else" ,"Failure")
                    }

                }

            })
    }
    fun liveNowPlaying(): LiveData<List<NowPlayingResultsItem>> {
           return popular
    }
}