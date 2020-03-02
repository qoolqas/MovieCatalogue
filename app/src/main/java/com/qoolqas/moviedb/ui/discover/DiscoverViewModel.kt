package com.qoolqas.moviedb.ui.discover

import android.util.Log
import androidx.lifecycle.*
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.discover.DiscoverMovieResponse
import com.qoolqas.moviedb.model.discover.DiscoverResultsItem
import com.qoolqas.moviedb.model.popular.PopularMovieResponse
import com.qoolqas.moviedb.model.popular.PopularResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiscoverViewModel : ViewModel() {
    private var discover = MutableLiveData<MutableList<DiscoverResultsItem>>(mutableListOf())
    val api: String = BuildConfig.API_KEY

    fun init(page: Int) {
        loadPopular(page)
    }

    fun loadPopular(page: Int) {
        Client().getApi().getDiscover(api, page)
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

//    fun livePopular(): LiveData<MutableList<DiscoverResultsItem>> {
//        return discover
//    }
    fun observerData(owner: LifecycleOwner,observer: Observer<MutableList<DiscoverResultsItem>>) = discover.observe(owner, observer)

}

