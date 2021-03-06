package com.mtjin.nomoneytrip.views.localpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepository
import com.mtjin.nomoneytrip.utils.TAG
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LocalPageViewModel(private val repository: LocalPageRepository) : BaseViewModel() {
    private val _tourIntroduceList = MutableLiveData<ArrayList<TourIntroduce>>()
    private val _restaurantIntroduceList = MutableLiveData<ArrayList<TourIntroduce>>()
    private val _productList = MutableLiveData<ArrayList<Product>>()

    val tourIntroduceList: LiveData<ArrayList<TourIntroduce>> get() = _tourIntroduceList
    val restaurantIntroduceList: LiveData<ArrayList<TourIntroduce>> get() = _restaurantIntroduceList
    val productList: LiveData<ArrayList<Product>> get() = _productList

    fun requestTourIntroduces(areaCode: Int) {
        compositeDisposable.add(
            repository.requestTourIntroduces(areaCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ introduces ->
                    _tourIntroduceList.value = introduces as ArrayList<TourIntroduce>
                }, {
                    Log.d(TAG, "requestTourIntroduces() -> $it")
                })
        )
    }

    fun requestRestaurantIntroduces(areaCode: Int) {
        compositeDisposable.add(
            repository.requestRestaurantIntroduces(areaCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ introduces ->
                    _restaurantIntroduceList.value = introduces as ArrayList<TourIntroduce>
                }, {
                    Log.d(TAG, "requestRestaurantIntroduces() -> $it")
                })
        )
    }

    fun requestProducts(city: String) {
        compositeDisposable.add(
            repository.requestProducts(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ products ->
                    _productList.value = products as ArrayList<Product>
                }, {
                    Log.d(TAG, "requestRestaurantIntroduces() -> $it")
                })
        )
    }
}