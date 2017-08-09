package com.level.fractal.salestwo.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Map;

public class IntentManager {
    public static void OpenActivity(Context context, Class<?> cls, Map<String, Object> bundleParams) {
        final Intent intent = new Intent(context, cls);
        if (bundleParams != null && !bundleParams.isEmpty()) {
            Bundle b = new Bundle();
            for (Map.Entry<String, Object> param : bundleParams.entrySet()) {
                if (param.getValue() instanceof String) {
                    b.putString(param.getKey(), String.valueOf(param.getValue()));
                }
                if (param.getValue() instanceof Integer) {
                    b.putInt(param.getKey(), (Integer) param.getValue());
                }
            }
            intent.putExtras(b);
        }

        context.startActivity(intent);
    }
}
