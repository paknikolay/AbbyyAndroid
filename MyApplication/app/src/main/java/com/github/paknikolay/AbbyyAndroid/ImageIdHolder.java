package com.github.paknikolay.AbbyyAndroid;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ImageIdHolder {
    static List<Integer> ids = new ArrayList<Integer>();

    public static void initialise(Context context) {
        String[] imagesNames = context.getResources().getStringArray( R.array.images );
        for(int i = 0; i < imagesNames.length; i++) {
            int imageId = context.getResources().getIdentifier(imagesNames[i],
                    "raw", context.getPackageName());
            ids.add(imageId);
        }
    }

    public static Integer getImageId(int imageindx) {
        return ids.get(imageindx);
    }
}
