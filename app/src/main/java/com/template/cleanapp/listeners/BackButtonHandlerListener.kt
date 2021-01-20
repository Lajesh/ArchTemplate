package com.template.cleanapp.listeners

/**
 * Back button handler interface. Add/remove listener functionality
 * Created by Lajesh Dineshkumar on 10/31/2019.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
interface BackButtonHandlerListener {
    fun addBackpressListener(listner: BackPressListener)
    fun removeBackpressListener(listner: BackPressListener)
}