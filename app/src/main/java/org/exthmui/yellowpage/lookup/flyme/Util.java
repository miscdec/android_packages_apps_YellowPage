package org.exthmui.yellowpage.lookup.flyme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/* renamed from: com.meizu.netcontactservice.utils.a */
/* loaded from: classes.dex */
public class Util {

    /* renamed from: a */
    private static String iccid;

    /* renamed from: encodeHashMap */
    private static String getPackageName() {
        return "com.ruiwei.netcontactservice";
    }

    /* renamed from: a */
    public static String getVersionName(Context context) {
//        try {
//            return context.getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
        return "10.0.10";
    }

    /* renamed from: b */
    public static String getVersionCode(Context context) {
        return "10000010";
//        try {
//            return String.valueOf(context.getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
    }

    /* renamed from: a */
    public static String getFlymeVersion() {
        return "TYH 9.0.0.0Q";
//        return Build.DISPLAY;

    }


    @SuppressLint({"MissingPermission"})
    /* renamed from: encodeHashMap */
    public static String getIccid(Context context) {
        if (TextUtils.isEmpty(iccid)) {
            iccid = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
            Log.i("AndroidUtils", "getIccid=" + iccid);
        }
        return iccid;
    }

    public static String getfakeIccid() {
        return "89860322230611756952";
    }

    @SuppressLint("MissingPermission")
    public static String getICCID(Context context) {

        SubscriptionManager sm = context.getSystemService(SubscriptionManager.class);
        List<SubscriptionInfo> sis = sm.getActiveSubscriptionInfoList();
        if (sis.size() >= 1) {
            SubscriptionInfo si1 = sis.get(0);
            return si1.getIccId();

        } else if (sis.size() >= 2) {
            SubscriptionInfo si2 = sis.get(1);
            return si2.getIccId();

        } else return null;


    }

    /* renamed from: d */
    public static String getIdentCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("number_identify_lock_config", 0);
        String string = sharedPreferences.getString("ident_code", "");
        if (TextUtils.isEmpty(string)) {
            String generateIdentifyCode = generateIdentifyCode();
            sharedPreferences.edit().putString("ident_code", generateIdentifyCode).apply();
            return generateIdentifyCode;
        }
        return string;
    }

    /* renamed from: b */
    public static String generateIdentifyCode() {
        String l = Long.toString(System.currentTimeMillis());
        int length = l.length();
        if (length < 6) {
            return null;
        }
        char[] charArray = l.substring(Math.max(length - 6, 0)).toCharArray();
        for (int i = 0; i < charArray.length; i += 2) {
            charArray[i] = (char) (charArray[i] + '2');
        }
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            sb.append(c);
        }
        return l + sb;
    }
}
