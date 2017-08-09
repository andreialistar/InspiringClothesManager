package com.level.fractal.salestwo.helpers;

import com.level.fractal.salestwo.R;

public class ResourcesLoader {
    public static int[] allImages = new int[] { R.drawable.dress1, R.drawable.dress2, R.drawable.dress3, R.drawable.dress4, R.drawable.dress5,
            R.drawable.hat1, R.drawable.hat2, R.drawable.hat3, R.drawable.hat4, R.drawable.hat5,
            R.drawable.jeans1, R.drawable.jeans2, R.drawable.jeans3, R.drawable.jeans4, R.drawable.jeans1,
            R.drawable.shirt1, R.drawable.shirt2, R.drawable.shirt3, R.drawable.shirt4, R.drawable.shirt5,
            R.drawable.tshirt1, R.drawable.tshirt2, R.drawable.tshirt3, R.drawable.tshirt4, R.drawable.tshirt5};

    public static int[] GetImagesIds(String path) {
        if (path.contains("Trending")) {
            if (path.contains("1")) {
                return new int[] { R.drawable.dress1, R.drawable.dress2, R.drawable.dress3, R.drawable.dress4, R.drawable.dress5,
                        R.drawable.dress1, R.drawable.dress2, R.drawable.dress3, R.drawable.dress4, R.drawable.dress5,
                        R.drawable.dress1, R.drawable.dress2, R.drawable.dress3, R.drawable.dress4, R.drawable.dress5,
                        R.drawable.dress1, R.drawable.dress2, R.drawable.dress3, R.drawable.dress4, R.drawable.dress5,
                        R.drawable.dress1, R.drawable.dress2, R.drawable.dress3, R.drawable.dress4, R.drawable.dress5};
            }
            else if (path.contains("2")) {
                return new int[] { R.drawable.dress1, R.drawable.hat1, R.drawable.jeans1, R.drawable.shirt1, R.drawable.tshirt1 };
            }
        }
        if (path.contains("Variants")) {
            int[] variant = new int[5];
            for (int i = 0; i < 5; i++) {
                int pivot = Integer.parseInt(path.substring(path.length() - 1));
                variant[i] = allImages[pivot * 5 + i];
            }

            return variant;
        }

        return new int[] { };
    }
}
