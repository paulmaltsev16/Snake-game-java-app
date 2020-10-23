package com.example.javasnakegame.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.javasnakegame.R;

import java.util.Random;

import static com.example.javasnakegame.MainActivity.screenHeight;
import static com.example.javasnakegame.MainActivity.screenWidth;
import static com.example.javasnakegame.MySurfaceView.oneFieldSize;
import static com.example.javasnakegame.MySurfaceView.snakeLength;

public class Apple {

    public static PositionHandler applePosition;
    private Context context;
    private Bitmap apple;
    private Random random;

    public Apple(Context context) {
        this.context = context;
        initBitmaps();
    }

    private void initBitmaps() {
        apple = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
        apple = Bitmap.createScaledBitmap(apple, oneFieldSize, oneFieldSize, false);
    }

    public void setAppleInNewPosition() {
        applePosition = new PositionHandler();
        random = new Random();
        int widthParts = screenWidth / oneFieldSize;
        int heightParts = screenHeight / oneFieldSize;

        applePosition.setLeftDistance(random.nextInt(widthParts) * oneFieldSize);
        applePosition.setTopDistance(random.nextInt(heightParts) * oneFieldSize);
    }

    public boolean appleFound() {
        return applePosition.getLeftDistance() == snakeLength.get(0).getLeftDistance()
                && applePosition.getTopDistance() == snakeLength.get(0).getTopDistance();
    }

    public Bitmap getApple() {
        return apple;
    }

}
