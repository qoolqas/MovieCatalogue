package com.qoolqas.moviedb.ui.detail

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.qoolqas.moviedb.BuildConfig
import com.qoolqas.moviedb.connection.Client
import com.qoolqas.moviedb.model.similiar.SimiliarResponse
import com.qoolqas.moviedb.model.similiar.SimiliarResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SimiliarViewModel : ViewModel() {
    private var similiar = MutableLiveData<MutableList<SimiliarResultsItem>>(mutableListOf())
    val api: String = BuildConfig.API_KEY

    fun init(page: Int, id: Int) {
        loadSimiliar(page, id)
    }

    fun loadSimiliar(page: Int, id: Int) {
        Client().getApi().getSimiliar(id, api, page)
            .enqueue(object : Callback<SimiliarResponse> {
                override fun onFailure(call: Call<SimiliarResponse>, t: Throwable) {
                    Log.d("failure discover", t.message.toString())
                }

                override fun onResponse(
                    call: Call<SimiliarResponse>,
                    response: Response<SimiliarResponse>
                ) {
                    if (response.isSuccessful) {
                        val respons: SimiliarResponse? = response.body()
                        similiar.postValue(similiar.value?.apply { addAll(respons?.results!!) })
                        Log.d("postblabla", similiar.toString())

                    } else {
                        Log.d("else", "Failure")
                    }

                }

            })
    }

    //    fun livePopular(): LiveData<MutableList<DiscoverResultsItem>> {
//        return discover
//    }
    fun observerData(owner: LifecycleOwner, observer: Observer<MutableList<SimiliarResultsItem>>) =
        similiar.observe(owner, observer)

}