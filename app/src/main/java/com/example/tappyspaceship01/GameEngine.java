package com.example.tappyspaceship01;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameEngine extends SurfaceView implements Runnable {

    // Android debug variables
    final static String TAG="TAPPY-SPACESHIP";

    // screen size
    int screenHeight;
    int screenWidth;

    // game state
    boolean gameIsRunning;

    // threading
    Thread gameThread;


    // drawing variables
    SurfaceHolder holder;
    Canvas canvas;
    Paint paintbrush;

    int lives = 10;



    // -----------------------------------
    // GAME SPECIFIC VARIABLES
    // -----------------------------------

    // ----------------------------
    // ## SPRITES
    // ----------------------------
    int playerXPosition;
    int playerYPosition;

    int enemyXPosition;
    int enemyYPosition;

    int enemyX1Position;
    int enemyY1Position;

    int enemyX2Position;
    int enemyY2Position;



    Bitmap playerImage;

    Bitmap enemyImage;
    Bitmap enemyImage1;
    Bitmap enemyImage2;

    Rect playerHitBox;

    Rect enemyHitBox;
    Rect enemyHitBox1;
    Rect enemyHitBox2;


    // ----------------------------
    // ## GAME STATS
    // ----------------------------

    public GameEngine(Context context, int w, int h) {
        super(context);


        this.holder = this.getHolder();
        this.paintbrush = new Paint();

        this.screenWidth = w;
        this.screenHeight = h;


        this.printScreenInfo();

        // @TODO: Add your sprites

        playerXPosition = 100;
        playerYPosition = 600;

        enemyXPosition = 1300;
        enemyYPosition = 80;

        enemyX1Position = 500;
        enemyY1Position = 320;

        enemyX2Position = 1300;
        enemyY2Position = 600;



        this.playerImage  = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.player_ship);

        this.playerHitBox = new Rect(100,600,100+playerImage.getWidth(), 600+playerImage.getHeight());

        enemyImage = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.alien_ship2);
        this.enemyHitBox = new Rect(1300,80,1300+enemyImage.getWidth(), 80+enemyImage.getHeight());

        enemyImage1 = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.alien_ship1);
        this.enemyHitBox1 = new Rect(500,320,500+enemyImage1.getWidth(), 320+enemyImage1.getHeight());

        enemyImage2 = BitmapFactory.decodeResource(this.getContext().getResources(),
                R.drawable.alien_ship3);
        this.enemyHitBox2 = new Rect(1300,600,1300+enemyImage2.getWidth(), 600+enemyImage2.getHeight());



        // @TODO: Any other game setup

    }


    private void printScreenInfo() {

        Log.d(TAG, "Screen (w, h) = " + this.screenWidth + "," + this.screenHeight);
    }

    private void spawnPlayer() {
        //@TODO: Start the player at the left side of screen
    }
    private void spawnEnemyShips() {
        Random random = new Random();

        //@TODO: Place the enemies in a random location

    }

    // ------------------------------
    // GAME STATE FUNCTIONS (run, stop, start)
    // ------------------------------
    @Override
    public void run() {
        while (gameIsRunning == true) {
            this.updatePositions();
            this.redrawSprites();
            this.setFPS();
        }
    }


    public void pauseGame() {
        gameIsRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // Error
        }
    }

    public void startGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // ------------------------------
    // GAME ENGINE FUNCTIONS
    // - update, draw, setFPS
    // ------------------------------

    public void updateHitBox(Rect hitBox, Bitmap image, int xPos, int yPos){
        hitBox.left  = xPos;
        hitBox.top = yPos;
        hitBox.right  = xPos + image.getWidth();
        hitBox.bottom = yPos + image.getHeight();
    }

//    reset the player position if it crases
//     public void playerHit(Rect playHitBox, Bitmap image, int xPos, int yPos, int live){
////         this.playerHitBox = new Rect(100,600,100+playerImage.getWidth(), 600+playerImage.getHeight());
//
//         playerXPosition = xPos;
//         playerYPosition = yPos;
//        playHitBox = new Rect(xPos,yPos,xPos+image.getWidth(), yPos+image.getHeight());
//        live = live-1;
//     }

//    reset position
    public void ResetPosition(){
        playerXPosition = 100;
        playerYPosition = 600;

        enemyXPosition = 1300;
        enemyYPosition = 80;

        enemyX1Position = 500;
        enemyY1Position = 320;

        enemyX2Position = 1300;
        enemyY2Position = 600;
    }

    public void updatePositions() {
        // @TODO: Update position of player
//        if mouse down , then moves player up
        if(this.fingerAction == "moveDown"){
            this.playerYPosition = this.playerYPosition - 15;
        }

        //        if mouse up , then moves player down
        if(this.fingerAction == "moveUp"){
            this.playerYPosition = this.playerYPosition + 15;
        }

//        Make the enemy move
//        when enemy touches the wall respan it
        this.enemyXPosition = this.enemyXPosition - 25;
        this.enemyX1Position = this.enemyX1Position - 25;
        this.enemyX2Position = this.enemyX2Position - 25;
        //        respan
        if(this.enemyXPosition <= -350){
            this.enemyXPosition = 1700;
            this.enemyYPosition = 80;
        }
        if(this.enemyX1Position <= -450){
            this.enemyX1Position = 1700;
            this.enemyY1Position = 320;
        }
        if(this.enemyX2Position <= -550){
            this.enemyX2Position = 1700;
            this.enemyY2Position = 620;
        }

//          MOVE THE HITBOX Enemy(recalcluate the position of the hitbox)
//        this.enemyHitBox.left  = this.enemyXPosition;
//        this.enemyHitBox.top = this.enemyYPosition;
//        this.enemyHitBox.right  = this.enemyXPosition + this.enemyImage.getWidth();
//        this.enemyHitBox.bottom = this.enemyYPosition + this.enemyImage.getHeight();
        updateHitBox(enemyHitBox,enemyImage,enemyXPosition,enemyYPosition);

//        this.enemyHitBox1.left  = this.enemyX1Position;
//        this.enemyHitBox1.top = this.enemyY1Position;
//        this.enemyHitBox1.right  = this.enemyX1Position + this.enemyImage1.getWidth();
//        this.enemyHitBox1.bottom = this.enemyY1Position + this.enemyImage1.getHeight();
        updateHitBox(enemyHitBox1,enemyImage1,enemyX1Position,enemyY1Position);

//        this.enemyHitBox2.left  = this.enemyX2Position;
//        this.enemyHitBox2.top = this.enemyY2Position;
//        this.enemyHitBox2.right  = this.enemyX2Position + this.enemyImage2.getWidth();
//        this.enemyHitBox2.bottom = this.enemyY2Position + this.enemyImage2.getHeight();
        updateHitBox(enemyHitBox2,enemyImage2,enemyX2Position,enemyY2Position);

        //          MOVE THE HITBOX player(recalcluate the position of the hitbox)
//        this.playerHitBox.left  = this.playerXPosition;
//        this.playerHitBox.top = this.playerYPosition;
//        this.playerHitBox.right  = this.playerXPosition + this.playerImage.getWidth();
//        this.playerHitBox.bottom = this.playerYPosition + this.playerImage.getHeight();
    updateHitBox(playerHitBox,playerImage,playerXPosition,playerYPosition);
//        @TODO CHECK COLLISIONS B/W ENEMY

        if(this.playerHitBox.intersect(this.enemyHitBox) == true){
            Log.d(TAG,"++Enemy Player colliding");


//        @Todo what do you next
//        Restart the game

            ResetPosition();
//            playerHit(playerHitBox,playerImage,playerXPosition,playerYPosition,lives);
//            playerXPosition = 100;
//            playerYPosition = 600;
            updateHitBox(playerHitBox,playerImage,playerXPosition,playerYPosition);
            updateHitBox(enemyHitBox,enemyImage,enemyXPosition,enemyYPosition);
            updateHitBox(enemyHitBox1,enemyImage1,enemyX1Position,enemyY1Position);
            updateHitBox(enemyHitBox2,enemyImage2,enemyX2Position,enemyY2Position);
//            this.playerHitBox = new Rect(100,600,100+playerImage.getWidth(), 600+playerImage.getHeight());
//
            // decrease the lives
            lives = lives - 1;

        }
        if(this.playerHitBox.intersect(this.enemyHitBox1) == true){
            Log.d(TAG,"++Enemy Player colliding");


//        @Todo what do you next
//        Restart the game

            ResetPosition();
//            playerXPosition = 100;
//            playerYPosition = 600;
//            this.playerHitBox = new Rect(100,600,100+playerImage.getWidth(), 600+playerImage.getHeight());
            updateHitBox(playerHitBox,playerImage,playerXPosition,playerYPosition);
            updateHitBox(enemyHitBox,enemyImage,enemyXPosition,enemyYPosition);
            updateHitBox(enemyHitBox1,enemyImage1,enemyX1Position,enemyY1Position);
            updateHitBox(enemyHitBox2,enemyImage2,enemyX2Position,enemyY2Position);

            // decrease the lives
            lives = lives - 1;
        }

        if(this.playerHitBox.intersect(this.enemyHitBox2) == true){
            Log.d(TAG,"++Enemy Player colliding");


//        @Todo what do you next
//        Restart the game

            ResetPosition();
//            playerXPosition = 100;
//            playerYPosition = 600;
//            this.playerHitBox = new Rect(100,600,100+playerImage.getWidth(), 600+playerImage.getHeight());
            updateHitBox(playerHitBox,playerImage,playerXPosition,playerYPosition);
            updateHitBox(enemyHitBox,enemyImage,enemyXPosition,enemyYPosition);
            updateHitBox(enemyHitBox1,enemyImage1,enemyX1Position,enemyY1Position);
            updateHitBox(enemyHitBox2,enemyImage2,enemyX2Position,enemyY2Position);

            // decrease the lives
            lives = lives - 1;
        }






    }

    public void redrawSprites() {
        if (this.holder.getSurface().isValid()) {
            this.canvas = this.holder.lockCanvas();

            //----------------

            // configure the drawing tools
            this.canvas.drawColor(Color.argb(255,255,255,255));
            paintbrush.setColor(Color.WHITE);


            // DRAW THE PLAYER HITBOX
            // ------------------------
            // 1. change the paintbrush settings so we can see the hitbox
            paintbrush.setColor(Color.BLUE);
            paintbrush.setStyle(Paint.Style.STROKE);
            paintbrush.setStrokeWidth(5);

//            Add The player on the screen


            canvas.drawBitmap(playerImage, playerXPosition, playerYPosition, paintbrush);

//            draw the hit box for player
            canvas.drawRect(this.playerHitBox,paintbrush);


//            Draw the enemy

            canvas.drawBitmap(enemyImage, enemyXPosition, enemyYPosition, paintbrush);
            canvas.drawBitmap(enemyImage1, enemyX1Position, enemyY1Position, paintbrush);
            canvas.drawBitmap(enemyImage2, enemyX2Position, enemyY2Position, paintbrush);


//            draw the hit box for enemy
            canvas.drawRect(this.enemyHitBox,paintbrush);
            canvas.drawRect(this.enemyHitBox1,paintbrush);
            canvas.drawRect(this.enemyHitBox2,paintbrush);


            // Show lives on the screen
            paintbrush.setTextSize(55);
            canvas.drawText("Lives Remaining: " + lives,1100,50,paintbrush);


            if (lives==0){
                //            Game Over
                paintbrush.setTextSize(100);
                canvas.drawText("Game Over ",screenWidth/2 - 300,screenHeight/2 -100,paintbrush);


//
//                try {
//                    gameThread.sleep(2000);
//                    pauseGame();
//                }catch (Exception e){
//
//                }

            }


            //----------------
            this.holder.unlockCanvasAndPost(canvas);
        }
    }

    public void setFPS() {
        try {
            gameThread.sleep(60);
        }
        catch (Exception e) {

        }
    }

    // ------------------------------
    // USER INPUT FUNCTIONS
    // ------------------------------

    String fingerAction = "";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        int userAction = event.getActionMasked();
        //@TODO: What should happen when person touches the screen?
        if (userAction == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "onTouchEvent: Person tapped the screen");
//            user is pressing down, so move player up
            fingerAction = "moveDown";


        }
        else if (userAction == MotionEvent.ACTION_UP) {
            Log.d(TAG, "onTouchEvent: Person lifted finger");
//            user has released finger so move player down
            fingerAction = "moveUp";
        }

        return true;
    }
}
