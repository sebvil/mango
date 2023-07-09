package com.sebastianvm.mango

import com.sebastianvm.mango.ui.mvvm.BaseViewModel
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.jupiter.api.extension.RegisterExtension

abstract class BaseTest {

    protected val testScope: TestScope
        get() = coroutineExtension.testScope

    protected val dispatcher: TestDispatcher = coroutineExtension.dispatcher

    fun <VM : BaseViewModel<*, *>> viewModelForTests(generateVM: () -> VM): VM {
        return generateVM().apply {
            vmScope = testScope
        }
    }

    companion object {
        @JvmStatic
        @RegisterExtension
        val coroutineExtension = CoroutineTestExtension()
    }
}
