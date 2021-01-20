package com.template.cleanapp.presentation.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.template.cleanapp.R
import com.template.cleanapp.di.Injectable
import com.template.cleanapp.presentation.viewmodel.ToolbarPropertyViewModel
import com.template.cleanapp.CleanApplication
import com.template.cleanapp.contract.SubscriptionContract
import com.template.cleanapp.listeners.BackButtonHandlerListener
import com.template.cleanapp.listeners.BackPressListener
import com.template.cleanapp.presentation.viewmodel.BaseViewModel
import com.template.cleanapp.presentation.viewmodel.SharedViewModel
import com.template.cleanapp.utils.extensions.popBackStackImmediate
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import kotlin.reflect.KClass

/****
 * Parent for all the UI fragments. All the common things to be kept here
 * Author: Lajesh Dineshkumar
 * Company: Farabi Technologies
 * Created on: 2020-03-03
 * Modified on: 2020-03-03
 *****/
abstract class BaseFragment<V : ViewModel, D : ViewDataBinding>(clazz: KClass<V>) :
    Fragment(),
    Injectable,
    BackPressListener {

    val viewModel: V by viewModel(clazz)

    lateinit var dataBinding: D

    private val sharedModel by sharedViewModel(SharedViewModel::class)

    open lateinit var sharedViewModel: SharedViewModel


    private var isObserverUnSubscribed: Boolean = false

    @get:LayoutRes
    protected abstract val layoutRes: Int

    abstract val bindingVariable: Int

    open val subscriptionContract: SubscriptionContract? = null

    protected var isUseCustomViewModelFactory: Boolean = true

    protected var isBackNavigationEnabled: Boolean = true

    protected var isBackDisabled: Boolean = false

    protected var isExitFlowOnCloseAction: Boolean = false

    lateinit var callback: OnBackPressedCallback

    private var backButtonHandler: BackButtonHandlerListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (viewModel as BaseViewModel).sharedViewModel = sharedModel
        sharedViewModel = sharedModel
        handleObserver()
    }

    /**
     * Method to override the back press behaviour on indivitual fragment
     */
    override fun onBackPress(): Boolean {
        if (!isBackDisabled) {
                activity?.let {
                    if (it.supportFragmentManager.backStackEntryCount == 0)
                        it.finish()
                    else
                        (it as AppCompatActivity).popBackStackImmediate()
                }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        backButtonHandler?.addBackpressListener(this)
    }

    override fun onPause() {
        super.onPause()
        backButtonHandler?.removeBackpressListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonHandler = context as BackButtonHandlerListener
    }

    override fun onDetach() {
        super.onDetach()
        backButtonHandler?.removeBackpressListener(this)
        backButtonHandler = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupToolbar(getToolbarProperty())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isObserverUnSubscribed)
            handleObserver()
        dataBinding.setVariable(bindingVariable, viewModel)
        dataBinding.executePendingBindings()
    }

    private fun handleObserver() {
        isObserverUnSubscribed = false
        subscriptionContract?.subscribeNavigationEvent()
        subscriptionContract?.subscribeNetworkResponse()
        (viewModel as BaseViewModel).errorEvent.observe(
            this,
            Observer {
               showErrorDialog(it.errorMessage as String)
            }
        )
    }

    /**
     * Method to show service related error messages
     */
    private fun showErrorDialog(message: String?) {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle(CleanApplication.getInstance()?.getString(R.string.title_error))
            .setMessage(message)
            .setPositiveButton(CleanApplication.getInstance()?.getString(R.string.action_ok), null)
            .create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }



    protected open fun setupToolbar(toolbarProperty: ToolbarPropertyViewModel?) {
        // Customize from child classes
    }

    protected fun getToolbarProperty(): ToolbarPropertyViewModel? {
        return (viewModel as BaseViewModel).toolbarPropertyViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isObserverUnSubscribed = true
        subscriptionContract?.unsubscribe()
        getToolbarProperty()?.showBack?.removeObservers(this)
        getToolbarProperty()?.showClose?.removeObservers(this)
        (viewModel as BaseViewModel).loadingEvent.removeObservers(this)
        (viewModel as BaseViewModel).errorEvent.removeObservers(this)
    }

    /**
     * Setting the layer type to HARDWARE for better animation performance
     */
    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        var animation = super.onCreateAnimation(transit, enter, nextAnim)
        if (animation == null && nextAnim != 0) {
            animation = AnimationUtils.loadAnimation(activity, nextAnim)

            if (animation != null) {
                view?.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                animation.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationRepeat(p0: Animation?) {
                        // Nothing goes here
                    }

                    override fun onAnimationEnd(p0: Animation?) {
                        view?.setLayerType(View.LAYER_TYPE_NONE, null)
                    }

                    override fun onAnimationStart(p0: Animation?) {
                        // Nothing goes here
                    }
                })
            }
        }

        return animation
    }

}
