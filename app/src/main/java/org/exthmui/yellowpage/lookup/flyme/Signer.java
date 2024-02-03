package org.exthmui.yellowpage.lookup.flyme;

import android.content.Context;
import android.text.TextUtils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 类f
 */
public class Signer {

    /* renamed from: a */
    // 公共静态最终 okhttp3.MediaType f3405a = okhttp3.MediaType.get("application/x-www-form-urlencoded");

    /* renamed from: b */
    private final Map<String, String> newhashmap = new HashMap<>();
    // from class: com.meizu.netcontactservice.b.f.1
    // java.util.Comparator
    /* renamed from: a  reason: avoid collision after fix types in other method */
    private final SortedMap<String, String> c = new TreeMap<>(Comparator.naturalOrder());

    /**
     * 方法a，将map中的键值对添加到newhashmap中，并对值进行空值判断
     *
     * @param map map对象
     */
    private void a(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            this.newhashmap.put(entry.getKey(), TextUtils.isEmpty(entry.getValue()) ? "" : entry.getValue());
        }
    }

    /**
     * 方法a，向newhashmap中添加键值对，并返回该对象本身
     *
     * @param str  键
     * @param str2 值
     * @return f对象
     */
    public Signer a(String str, String str2) {
        Map<String, String> map = this.newhashmap;
        if (TextUtils.isEmpty(str2)) {
            str2 = "";
        }
        map.put(str, str2);
        return this;
    }

    /**
     * 构造方法，传入context参数，调用a方法
     *
     * @param context 上下文对象
     */
    public Signer(Context context) {
        a(a(context));
    }

    /* renamed from: a */

    /**
     * 方法append，向c中添加键值对，并对值进行空值判断和字符串转换
     *
     * @param str 键
     * @param obj 值
     * @return f对象
     */
    public Signer append(String str, Object obj) {
        if (obj != null && !b(str, String.valueOf(obj)) && !TextUtils.isEmpty(obj.toString())) {
            this.c.put(str, String.valueOf(obj));
        }
        return this;
    }

    /**
     * 方法a，从c中移除指定的键
     *
     * @param str 键
     */
    public void a(String str) {
        this.c.remove(str);
    }

    /**
     * 方法b，将map中的键值对转换为字符串形式，并去除最后一个'&'符号
     *
     * @param map map对象
     * @return 转换后的字符串
     */
    private static String b(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!TextUtils.isEmpty(entry.getValue()) && !b(entry.getKey(), entry.getValue())) {
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
                sb.append('&');
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        return sb.toString();
    }

    /**
     * 方法b，判断是否满足特定条件
     *
     * @param str  键
     * @param str2 值
     * @return 如果满足特定条件则返回true，否则返回false
     */
    private static boolean b(String str, String str2) {
        return ("entry_id".equals(str) && "0".equals(str2)) || "-1".equals(str2);
    }

    /**
     * 方法a，返回c
     *
     * @return c
     */
    public Map<String, String> a() {
        return this.c;
    }

    /**
     * 方法toString，返回b(c)
     *
     * @return 转换后的字符串
     */
    public String toString() {
        return b(this.c);
    }

    /**
     * 静态方法a，返回一个包含特定键值对的HashMap
     *
     * @param context 上下文对象
     * @return 包含特定键值对的HashMap
     */
    public static Map<String, String> a(Context context) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("flyme_version", Util.getFlymeVersion());
        hashMap.put("version", Util.getVersionName(context));
        hashMap.put("version_code", Util.getVersionCode(context));
        hashMap.put("clientType", String.valueOf(1));
        return hashMap;
    }

    /**
     * 私有方法e，调用b(false)
     */
    private void e() {
        b(false);
    }

    /**
     * 私有方法b，根据参数z的值进行不同的操作
     *
     * @param z 布尔值
     */
    private void b(boolean z) {
        String fVar = toString();
        a("key");
        append("sign", new DigestUtils("MD5").digestAsHex(fVar));
        this.c.putAll(this.newhashmap);
        if (z) {
            Map<String, String> c = encodeHashMap(this.c);
            this.c.clear();
            this.c.putAll(c);
        }
    }

    /**
     * 方法b，返回b()
     *
     * @return b()
     */
    public Map<String, String> b() {
        e();
        return a();
    }

    /**
     * 静态方法d，返回String.valueOf(System.currentTimeMillis())
     *
     * @return 当前时间的字符串表示
     */
    public static String d() {
        return String.valueOf(System.currentTimeMillis());
    }

    /* loaded from: classes.dex */

    /**
     * 静态内部类a
     */
    public static class a {
        /**
         * 静态方法a，返回String.valueOf(System.currentTimeMillis())
         *
         * @return 当前时间的字符串表示
         */
        public static String a() {
            return String.valueOf(System.currentTimeMillis());
        }
    }

    /**
     * 静态方法encodeHashMap，将map中的值进行URL编码处理，并返回编码后的HashMap
     *
     * @param map map对象
     * @return 编码后的HashMap
     */
    private static Map<String, String> encodeHashMap(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            try {
                hashMap.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }
}

