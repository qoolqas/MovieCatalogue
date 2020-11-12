package com.qoolqas.moviedb.ui.discover.genre

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.discover.DiscoverMovieResponse
import com.qoolqas.moviedb.model.discover.DiscoverResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchGenreViewModel : ViewModel() {
    private var discover = MutableLiveData<MutableList<DiscoverResultsItem>>(mutableListOf())
    val api: String = BuildConfig.API_KEY

    fun init(page: Int, language: String, genre: String) {
        loadDiscover(page, language, genre)
    }

    fun loadDiscover(page: Int,language: String,genre: String) {
        Client().getApi().getSearchByGenre(api, page,language, genre)
            .enqueue(object : Callback<DiscoverMovieResponse> {
                override fun onFailure(call: Call<DiscoverMovieResponse>, t: Throwable) {
                    Log.d("failure discover", t.message.toString())
                }

                override fun onResponse(
                    call: Call<DiscoverMovieResponse>,
                    response: Response<DiscoverMovieResponse>
                ) {
                    if (response.isSuccessful) {
                        val respons: DiscoverMovieResponse? = response.body()
                        discover.postValue(discover.value?.apply { addAll(respons?.results!!)})

                    } else {
                        Log.d("else", "Failure")
                    }
                }
            })
    }

    fun observerData(owner: LifecycleOwner,observer: Observer<MutableList<DiscoverResultsItem>>) = discover.observe(owner, observer)

}

