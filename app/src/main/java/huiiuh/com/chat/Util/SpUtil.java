package huiiuh.com.chat.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hp on 2017/7/29.
 */

public class SpUtil {
    static SharedPreferences sp;

    public static void putBooelan(Context context, String key, boolean booleans) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        }
        sp.edit().putBoolean(key, booleans).commit();
    }

    public static boolean getBooelan(Context context, String key, boolean booleans) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        }
        return sp.getBoolean(key, booleans);
    }

    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        }
        sp.edit().putString(key, value).commit();

    }

    public static String getString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        }
        return sp.getString(key, value);
    }

    public static void putInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        }
        sp.edit().putInt(key, value).commit();

    }

    public static int getInt(Context context, String key, int value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        }
        return sp.getInt(key,value);
    }
}
