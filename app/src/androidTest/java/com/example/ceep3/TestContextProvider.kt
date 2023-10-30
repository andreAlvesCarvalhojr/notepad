package com.example.ceep3

import android.content.Context
import com.example.ceep3.listener.ContextProvider
import androidx.test.platform.app.InstrumentationRegistry

class TestContextProvider: ContextProvider {
    override fun getContext(): Context {
        return InstrumentationRegistry.getInstrumentation().targetContext
    }
}