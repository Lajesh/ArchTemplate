package com.template.data.extension

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * This class will keep all the scheduler extension for different types for RX subjects
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */

// Observable
fun <T> Observable<T>.applyIoScheduler() = applyScheduler(Schedulers.io())

fun <T> Observable<T>.applyComputationScheduler() = applyScheduler(Schedulers.computation())

private fun <T> Observable<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

// Flowable
fun <T> Flowable<T>.applyIoScheduler() = applyScheduler(Schedulers.io())

fun <T> Flowable<T>.applyComputationScheduler() = applyScheduler(Schedulers.computation())

private fun <T> Flowable<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

// Single
fun <T> Single<T>.applyIoScheduler() = applyScheduler(Schedulers.io())

fun <T> Single<T>.applyComputationScheduler() = applyScheduler(Schedulers.computation())

private fun <T> Single<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

// Maybe
fun <T> Maybe<T>.applyIoScheduler() = applyScheduler(Schedulers.io())

fun <T> Maybe<T>.applyComputationScheduler() = applyScheduler(Schedulers.computation())

private fun <T> Maybe<T>.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

// Completable
fun Completable.applyIoScheduler() = applyScheduler(Schedulers.io())

fun Completable.applyComputationScheduler() = applyScheduler(Schedulers.computation())

private fun Completable.applyScheduler(scheduler: Scheduler) =
    subscribeOn(scheduler).observeOn(AndroidSchedulers.mainThread())

data class Duration(val duration: Long, val timeUnit: TimeUnit)

fun <T> Observable<T>.retry(
    predicate: (Throwable) -> Boolean,
    maxRetry: Long,
    delayBeforeRetry: Duration
): Observable<T> =
    retryWhen {
        Observables.zip(
            it.map { if (predicate(it)) it else throw it },
            Observable.interval(delayBeforeRetry.duration, delayBeforeRetry.timeUnit)
        )
            .map { if (it.second >= maxRetry) throw it.first }
    }
