
package com.rperryng.tutorialview;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;

public class Utils {
    
    public static final double DEFAULT_FACTOR = .35;

    public static int pxFromDp(Context context, int desiredDp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, desiredDp,
                context.getResources().getDisplayMetrics());
    }

    public static int applyAlpha(int alpha, int color) {
        return Color.argb(alpha, Color.red(color), Color.green(color), Color.blue(color));
    }

    public static int getTint(int color, int factor) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(Color.alpha(color), red + (int) ((255 - red) * DEFAULT_FACTOR),
                green + (int) ((255 - green) * DEFAULT_FACTOR),
                blue + (int) ((255 - blue) * DEFAULT_FACTOR));
    }

}
