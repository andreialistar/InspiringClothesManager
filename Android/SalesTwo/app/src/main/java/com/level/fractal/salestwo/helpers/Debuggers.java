package com.level.fractal.salestwo.helpers;

import android.view.View;
import android.view.ViewParent;

public class Debuggers {
    public static String GetAllParents (View view){
        String path = "";

        ViewParent parent = view.getParent();
        while (parent != null) {
            path += ((View)parent).getClass().getName() + ", ";
            parent = parent.getParent();
        }

        return path;
    }
}
