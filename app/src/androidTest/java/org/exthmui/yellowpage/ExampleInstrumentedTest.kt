package org.exthmui.yellowpage

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
//    private val phoneNumberLookups = arrayOf()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.exthmui.yellowpage", appContext.packageName)

//        lookupInfoOnline(appContext, "10000", 86).apply {
//            Log.d("testlookup-final", this.toString())
//        }
    }

//    private fun lookupInfoOnline(
//        context: Context,
//        @NonNull number: String,
//        countryCode: Long
//    ): PhoneNumberInfo? {
//        var info: PhoneNumberInfo? = null
//        for (phoneNumberLookup in phoneNumberLookups) {
//            if (countryCode == 0L || phoneNumberLookup.checkRegion(countryCode)) {
//                info = phoneNumberLookup.lookup(context, number)
//                Log.d("testlookup", info.toString())
//                if (info.type != Constants.PhoneNumberTagData.TYPE_NORMAL) {
//                    break
//                }
//            }
//        }
//        return info
//    }
}

