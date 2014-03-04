package com.rperryng.tutorialview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.view.View;

public class DrawingView extends View {
    
    private static final int GRADIENT_RADIUS = 50;
    private static final int GRADIENT_RADIUS_BIG = 100;
    private static final int BORDER_THICKNESS = 4;
    
    private Canvas darkCanvas;
    private Canvas drawingCanvas;
    private Paint eraser;
    private Paint gradientPaint;
    private Paint gradientPaintBig;
    private Paint borderPaint;
    
    private Bitmap bitmap;
    private Bitmap darkBitmap;
    
    private int dimLevel;
    private int colorThemeFaded;
    private int colors[];
    
    private int[] coordinates;
    private int radius;
    private int borderRadius;;
    private int gradientRadius;
    private int gradientRadiusBig;
    
    public DrawingView(Context context, int[] coordinates, int colorTheme, int colorThemeFaded, int dimLevel, int radius) {
        super(context);
        
        eraser = new Paint();
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser.setAntiAlias(true);
        
        borderPaint = new Paint();
        borderPaint.setColor(colorTheme);
        borderPaint.setAntiAlias(true);
        
        gradientPaint = new Paint();
        gradientPaint.setAntiAlias(true);
        gradientPaintBig = new Paint();
        gradientPaintBig.setAntiAlias(true);
        // actual gradientPaint applied on size changed
        
        this.dimLevel = dimLevel;
        this.colorThemeFaded = colorThemeFaded;
        colors = new int[]{colorTheme, colorThemeFaded};
        
        this.coordinates = coordinates;
        this.radius = radius;
        borderRadius = radius + Utils.pxFromDp(context, BORDER_THICKNESS);
        gradientRadius = radius + Utils.pxFromDp(context, GRADIENT_RADIUS);
        gradientRadiusBig = radius + Utils.pxFromDp(context, GRADIENT_RADIUS_BIG);
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
        if (dimLevel != 0) {
            darkBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
            darkCanvas = new Canvas(darkBitmap);
        }
        drawingCanvas = new Canvas(bitmap);
        
        RadialGradient radialGradient = new RadialGradient(coordinates[0], coordinates[1], gradientRadius,
                colors, null, android.graphics.Shader.TileMode.CLAMP);
        gradientPaint.setShader(radialGradient);
        
        RadialGradient radialGradientBig = new RadialGradient(coordinates[0], coordinates[1], gradientRadiusBig, colors, null, android.graphics.Shader.TileMode.CLAMP);
        gradientPaintBig.setShader(radialGradientBig);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawingCanvas.drawPaint(eraser);
        if (dimLevel != 0) {
            darkCanvas.drawColor(Color.argb(dimLevel, 0, 0, 0));
            darkCanvas.drawCircle(coordinates[0], coordinates[1], radius, eraser);
            canvas.drawBitmap(darkBitmap, 0, 0, null);
        }
        drawingCanvas.drawColor(colorThemeFaded);
        drawingCanvas.drawCircle(coordinates[0], coordinates[1], gradientRadiusBig, eraser);
        drawingCanvas.drawCircle(coordinates[0], coordinates[1], gradientRadiusBig, gradientPaintBig);
        drawingCanvas.drawCircle(coordinates[0], coordinates[1], gradientRadius, eraser);
        drawingCanvas.drawCircle(coordinates[0], coordinates[1], gradientRadius, gradientPaint);
        drawingCanvas.drawCircle(coordinates[0], coordinates[1], borderRadius, borderPaint);
        drawingCanvas.drawCircle(coordinates[0], coordinates[1], radius, eraser);
        canvas.drawBitmap(bitmap, 0, 0, null);
        super.onDraw(canvas);
    }
    
}
