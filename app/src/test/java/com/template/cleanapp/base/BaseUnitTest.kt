package com.template.cleanapp.base

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import com.template.cleanapp.BuildConfig
import com.template.cleanapp.CleanApplication
import com.template.cleanapp.config.Configuration
import com.template.cleanapp.di.configureTestAppComponent
import com.template.cleanapp.utils.StringResolver
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.AutoCloseKoinTest
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor
import org.powermock.modules.junit4.PowerMockRunner
import kotlin.jvm.Throws

/**
 * Base class for all types of unit test and instrumentation tests
 * All the  initial mocks required for enabling unit testing are done in this class.
 * The class will be using Mockito and PowerMock for mocking purposes
 * Created by Lajesh Dineshkumar on 10/31/2019.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
@RunWith(PowerMockRunner::class)
@PowerMockIgnore("javax.net.ssl.*")
@SuppressStaticInitializationFor("com.enbd.prepaidcard.common.TokenAuthenticator")
@PrepareForTest(
    Log::class,
    Handler::class,
    Looper::class,
    TextUtils::class,
    BuildConfig::class,
    StringResolver::class,
    Configuration::class,
    CleanApplication::class
)
abstract class BaseUnitTest : AutoCloseKoinTest() {

    lateinit var context: Context
    lateinit var resources: Resources
    lateinit var mockWebServer: MockWebServer

    lateinit var testScheduler: TestScheduler
    lateinit var stringResolver: StringResolver

    @Mock
    lateinit var application: CleanApplication

    @Mock
    lateinit var mockContext: Context

    /**
     * Set Mockwebserver url
     */
    fun getMockWebServerUrl() = mockWebServer.url("/").toString()

    @Before
    @Throws(Exception::class)
    open fun setUp() {

        // Initializes the mock environment
        MockitoAnnotations.initMocks(this)

        // Initializes the mock webserver
        mockWebServer = MockWebServer()
        startMockWebserver()

        // Mocks the generic android dependencies such as Context, Looper, etc.
        mockAndroidDependencies()

        // Mocks android logs
        mockLogs()


        // Sets the RXJava schedulers for unit tests
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }

        stopKoin()
        startKoin { modules(configureTestAppComponent(getMockWebServerUrl())) }
    }

    @After
    open fun tearDown() {
        stopMockWebserver()
    }

    /**
     * Method which starts the mockwebserver
     */
    private fun startMockWebserver() {
        mockWebServer.start(8076)
    }

    /**
     * Method which stops the mock webserver
     */
    private fun stopMockWebserver() {
        mockWebServer.shutdown()
    }

    /**
     * This function will mock all the android Log related dependencies
     */
    private fun mockLogs() {
        PowerMockito.mockStatic(Log::class.java)
        val logAnswer = Answer<Void> { invocation ->
            val tag = invocation.arguments[0] as String
            val msg = invocation.arguments[1] as String
            println(invocation.method.name.toUpperCase() + "/[" + tag + "] " + msg)
            null
        }
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "d",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "i",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "w",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "e",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )
        PowerMockito.doAnswer(logAnswer).`when`(
            Log::class.java, "wtf",
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString()
        )

        PowerMockito.doAnswer { invocation ->
            val s = invocation.arguments[0] as String
            s.isEmpty()
        }.`when`(TextUtils::class.java, "isEmpty", ArgumentMatchers.anyString())
    }



    private fun mockAndroidDependencies() {
        context = PowerMockito.mock(Context::class.java)
        resources = PowerMockito.mock(Resources::class.java)
        testScheduler = TestScheduler()
        stringResolver = StringResolver(context)
        PowerMockito.mockStatic(Looper::class.java)
        PowerMockito.mockStatic(Handler::class.java)
        PowerMockito.mockStatic(TextUtils::class.java)
        PowerMockito.mock(StringResolver::class.java)
        PowerMockito.mockStatic(CleanApplication::class.java)
        PowerMockito.`when`(Looper.getMainLooper()).thenReturn(null)
        PowerMockito.`when`(context.resources).thenReturn(resources)
        CleanApplication.setInstance(application)
        PowerMockito.`when`(application.applicationContext).thenReturn(mockContext)
        PowerMockito.`when`(CleanApplication.applicationContext()).thenReturn(context)

        PowerMockito.whenNew(Handler::class.java).withAnyArguments().thenReturn(null)
    }
}
