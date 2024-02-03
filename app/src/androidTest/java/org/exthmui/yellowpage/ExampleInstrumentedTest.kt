package org.exthmui.yellowpage

import android.content.Context
import android.util.Log
import androidx.annotation.NonNull
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.exthmui.yellowpage.lookup.flyme.FlymeLookup
import org.exthmui.yellowpage.misc.Constants
import org.exthmui.yellowpage.models.PhoneNumberInfo
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val phoneNumberLookups = arrayOf(FlymeLookup())

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.exthmui.yellowpage", appContext.packageName)

        lookupInfoOnline(appContext, "03536661001", 86).apply {
            Log.d("testlookup-final", this.toString())
        }
    }

    private fun lookupInfoOnline(
        context: Context,
        @NonNull number: String,
        countryCode: Long
    ): PhoneNumberInfo? {
        var info: PhoneNumberInfo? = null
        for (phoneNumberLookup in phoneNumberLookups) {
            if (countryCode == 0L || phoneNumberLookup.checkRegion(countryCode)) {
                info = phoneNumberLookup.lookup(context, number)
                Log.d("testlookup", info.toString())
                if (info.type != Constants.PhoneNumberTagData.TYPE_NORMAL) {
                    break
                }
            }
        }
        return info
    }
}

