package com.template.cleanapp.presentation.viewmodel

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import com.template.domain.entity.common.ErrorEntity
import com.template.cleanapp.architecture.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/****
 * Base view model. All the common implementation of viewmodel goes here
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-03-03
 * Modified on: 2020-03-03
 *****/
abstract class BaseViewModel : ViewModel(), Observable {

    private val compositeDisposable = CompositeDisposable()

    /**
     * Live data to handle loading
     */
    val loadingEvent = SingleLiveEvent<Boolean>()

    /**
     * Live data to handle error
     */
    val errorEvent = SingleLiveEvent<ErrorEntity.Error>()


    private val callbacks = PropertyChangeRegistry()

    lateinit var sharedViewModel: SharedViewModel


    var toolbarPropertyViewModel: ToolbarPropertyViewModel = ToolbarPropertyViewModel()



    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    internal fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies observers that a specific property has changed. The getter for the
     * property that changes should be marked with the @Bindable annotation to
     * generate a field in the BR class to be used as the fieldId parameter.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    internal fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    /**
     * Method call to handle loading
     */
    fun showLoading(show: Boolean, isDelayed: Boolean = true) {
        if(!show && isDelayed) {
            android.os.Handler().postDelayed({
                loadingEvent.value = false
            }, 400)
        }else{
            loadingEvent.value = show
        }
    }

    fun postLoading(show: Boolean) {
        loadingEvent.postValue(show)
    }

    fun getLoading(): SingleLiveEvent<Boolean> {
        return loadingEvent
    }

    /**
     * Method call to handle error
     */
    fun setError(error: ErrorEntity.Error?) {
        errorEvent.value = error
    }

    /**
     * Method call to add observables in composite disposable
     */
    protected fun Disposable.track() {
        compositeDisposable.add(this)
    }

    /**
     * Overridden method to clean observables
     */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    /**
     * Close button action
     */
    fun onCloseButtonAction() {
        toolbarPropertyViewModel.closeButtonAction.call()
    }

    fun onBackButtonAction() {
        toolbarPropertyViewModel.backButtonAction.call()
    }

}
