package com.example.javasnakegame.Models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.javasnakegame.R;

import static com.example.javasnakegame.MySurfaceView.oneFieldSize;

public class Snake {

    private Context context;

    private Bitmap snakeHead;
    private Bitmap snakeBody;

    public Snake(Context context) {
        this.context = context;
        initBitmaps();
    }

    private void initBitmaps() {
        snakeHead = BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_head);
        snakeBody = BitmapFactory.decodeResource(context.getResources(), R.drawable.snake_body);

        snakeHead = Bitmap.createScaledBitmap(snakeHead, oneFieldSize, oneFieldSize, false);
        snakeBody = Bitmap.createScaledBitmap(snakeBody, oneFieldSize-15, oneFieldSize-15, false);
    }

    public Bitmap getSnakeHead() {
        return snakeHead;
    }

    public Bitmap getSnakeBody() {
        return snakeBody;
    }

}
