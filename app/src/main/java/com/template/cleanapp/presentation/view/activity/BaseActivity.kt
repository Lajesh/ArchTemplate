package com.template.cleanapp.presentation.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.template.cleanapp.presentation.viewmodel.ToolbarPropertyViewModel
import com.template.cleanapp.listeners.BackButtonHandlerListener
import com.template.cleanapp.listeners.BackPressListener
import com.template.cleanapp.presentation.viewmodel.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference
import java.util.*
import kotlin.reflect.KClass

/****
 * All the activity should be extended from this parent class.
 * All the common functionalities across activities should be kept here
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-03-03
 * Modified on: 2020-03-03
 *****/
abstract class BaseActivity<V : ViewModel, D : ViewDataBinding>(clazz: KClass<V>) :
    AppCompatActivity(), BackButtonHandlerListener {

    val viewModel: V by viewModel(clazz)

    lateinit var dataBinding: D

    private val toolbarModel: ToolbarPropertyViewModel by viewModel()

    @get:LayoutRes
    protected abstract val layoutRes: Int

    abstract val bindingVariable: Int

    protected abstract fun getViewModel(): Class<V>

    protected var isUseCustomViewModelFactory: Boolean = true

    private val backClickListenersList = ArrayList<WeakReference<BackPressListener>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataBinding = DataBindingUtil.setContentView(this, layoutRes)
        dataBinding.lifecycleOwner = this

        (viewModel as? BaseViewModel)?.toolbarPropertyViewModel = toolbarModel

        dataBinding.setVariable(bindingVariable, viewModel)
        dataBinding.executePendingBindings()

    }

    /**
     * Showing progress bar over screen
     */
    fun showLoading(it: Boolean?) {
        it?.let { disableTouch ->
            (viewModel as BaseViewModel).showLoading(it)
            if (disableTouch) {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            } else {
                window.clearFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                )
            }
        }
    }

    /**
     * Methods which handles the hardware back button / navigation back view
     */
    override fun onBackPressed() {
        if (!fragmentsBackKeyIntercept()) {
            super.onBackPressed()
        }
    }

    /**
     * Add the back navigation listener here.
     * Call this method from onAttach of your fragment
     * @param listner - back navigation listener
     */
    override fun addBackpressListener(listner: BackPressListener) {
        backClickListenersList.add(WeakReference(listner))
    }

    /**
     * remove the back navigation listener here.
     * Call this method from onDetach of your fragment
     * @param listner - back navigation listener
     */
    override fun removeBackpressListener(listner: BackPressListener) {
        val iterator = backClickListenersList.iterator()
        while (iterator.hasNext()) {
            val weakRef = iterator.next()
            if (weakRef.get() === listner) {
                iterator.remove()
            }
        }
    }

    /**
     * This method checks if any frgament is overriding the back button behavior or not
     * @return true/false
     */
    private fun fragmentsBackKeyIntercept(): Boolean {
        var isIntercept = false
        for (weakRef in backClickListenersList) {
            val backpressListner = weakRef.get()
            if (backpressListner != null) {
                val isFragmIntercept: Boolean = backpressListner.onBackPress()
                if (!isIntercept)
                    isIntercept = isFragmIntercept
            }
        }
        return isIntercept
    }

}
