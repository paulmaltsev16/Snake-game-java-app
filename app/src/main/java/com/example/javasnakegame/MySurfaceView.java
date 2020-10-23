package com.example.javasnakegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.javasnakegame.Models.Apple;
import com.example.javasnakegame.Models.PositionHandler;
import com.example.javasnakegame.Models.Snake;

import java.util.ArrayList;

import static com.example.javasnakegame.MainActivity.screenWidth;
import static com.example.javasnakegame.Models.Apple.applePosition;


public class MySurfaceView extends SurfaceView implements Runnable {

    public static ArrayList<PositionHandler> snakeLength; //At index 0 snake head
    private static final String TAG = "MySurfaceView";

    private Context context;
    private Movements movements;
    private Snake snake;
    private Apple apple;

    private Bitmap snakeHead_bmp, snakedBody_bmp, apple_bmp;
    private boolean inGame = false;
    private SurfaceHolder surfaceHolder;
    private Thread gameThread = null;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    private int timeForSleep = 500;
    public static int oneFieldSize = 100;

    public MySurfaceView(Context context) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();

        initObjects();
        prepareForNewGame();
        Log.i(TAG, "MySurfaceView: created");
    }

    private void initObjects() {
        sharedPref = context.getSharedPreferences(String.valueOf(R.string.shared_pref_name), 0);
        editor = sharedPref.edit();
        movements = new Movements();
        snake = new Snake(context);
        apple = new Apple(context);

        snakeHead_bmp = snake.getSnakeHead();
        snakedBody_bmp = snake.getSnakeBody();
        apple_bmp = apple.getApple();
    }

    @Override
    public void run() {
        while (inGame) {
            updateValues();
            drawGame();
            waitForOneSecond();
        }
    }

    private void updateValues() {
        movements.updateBodyPositions();
        movements.updateHeadPosition();

        if (apple.appleFound()) {
            apple.setAppleInNewPosition();
            snakeLength.add(snakeLength.size(), movements.addNewSnakePart());
        }

        if (snakeLength.get(0).outOfGameField()) {
            updateHighScore();
            prepareForNewGame();
        }

        if (snakeLength.get(0).stoppedByHimself()) {
            updateHighScore();
            prepareForNewGame();
        }
    }

    private void updateHighScore() {
        int savedHighScore = sharedPref.getInt(String.valueOf(R.string.snake_length), 0);
        int currentHighScore = snakeLength.size();
        if (currentHighScore > savedHighScore) {
            editor.putInt(String.valueOf(R.string.snake_length), currentHighScore);
            editor.commit();
        }
    }

    private void prepareForNewGame() {
        inGame = true;
        apple.setAppleInNewPosition();
        snakeLength = new ArrayList<>();
        snakeLength.add(0, movements.getCenterPosition());
    }

    private void drawGame() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.DKGRAY);

            canvas = drawHighScore(canvas);

            canvas = drawSnakeAndApple(canvas);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private Canvas drawHighScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.BLACK);
        String highScore = "" + sharedPref.getInt(String.valueOf(R.string.snake_length), 0);
        canvas.drawText("High score: " + highScore, screenWidth / 2 - 120, 100, paint);
        canvas.drawText("Current score: " + snakeLength.size(), screenWidth / 2 - 122, 200, paint);
        return canvas;
    }

    private Canvas drawSnakeAndApple(Canvas canvas) {
        //Draw snake head
        canvas.drawBitmap(snakeHead_bmp, snakeLength.get(0).getLeftDistance(), snakeLength.get(0).getTopDistance(), null);
        //Draw snake body
        for (int i = 1; i < snakeLength.size(); i += 1) {//0 position - snake's head, we drew it before
            PositionHandler positionHandler = snakeLength.get(i);
            canvas.drawBitmap(snakedBody_bmp, positionHandler.getLeftDistance(), positionHandler.getTopDistance(), null);
        }
        //Draw apple
        canvas.drawBitmap(apple_bmp, applePosition.getLeftDistance(), applePosition.getTopDistance(), null);
        return canvas;
    }

    private void waitForOneSecond() {
        try {
            Thread.sleep(timeForSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        inGame = false;
        while (true) {
            try {
                gameThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        gameThread = null;
    }

    public void resume() {
        inGame = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                movements.getNewDirection(event);
                return true;
            default:
                return true;
        }
    }
}


