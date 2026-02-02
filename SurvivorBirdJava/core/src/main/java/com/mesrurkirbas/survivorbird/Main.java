package com.mesrurkirbas.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    SpriteBatch batch;
    Texture background;
    Texture dragon;
    Texture bat1;
    Texture bat2;
    Texture bat3;

    float dragonX = 0;
    float dragonY = 0;
    int gameState = 0;
    float velocity = 0;
    float gravity = 0.2f;
    float enemyVelocity = 5;
    Random random;

    int score = 0;
    int scoredEnemy = 0;
    BitmapFont font;
    BitmapFont font2;


    Circle dragonCircle;
    ShapeRenderer shapeRenderer;



    int numberOfEnemies = 4;
    float [] enemyX = new float[numberOfEnemies];
    float [] enemyOffSet = new float[numberOfEnemies];
    float [] enemyOffSet2 = new float[numberOfEnemies];
    float [] enemyOffSet3 = new float[numberOfEnemies];

    Circle[] enemyCircles;
    Circle[] enemyCircles2;
    Circle[] enemyCircles3;


    float distance = 0;




    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("background.png");
        dragon = new Texture("dragon.png");
        bat1 = new Texture("bat.png");
        bat2 = new Texture("bat.png");
        bat3 = new Texture("bat.png");

        distance = (float) Gdx.graphics.getWidth() / 2;
        random = new Random();


        dragonX = (float) Gdx.graphics.getWidth() / 5;
        dragonY = (float) Gdx.graphics.getHeight() / 3;

        shapeRenderer = new ShapeRenderer();


        dragonCircle = new Circle();
        enemyCircles = new Circle[numberOfEnemies];
        enemyCircles2 = new Circle[numberOfEnemies];
        enemyCircles3 = new Circle[numberOfEnemies];

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(4);

        font2 = new BitmapFont();
        font2.setColor(Color.WHITE);
        font2.getData().setScale(8);

        for (int i = 0; i < numberOfEnemies; i++) {
            enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
            enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
            enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

            enemyX[i] = Gdx.graphics.getWidth() + i * distance;

            enemyCircles[i] = new Circle();
            enemyCircles2[i] = new Circle();
            enemyCircles3[i] = new Circle();

        }
    }

    @Override
    public void render() {
        //background
        batch.begin();
        batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        //oyun basladi
        if (gameState == 1){

            if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - dragon.getHeight() / 2 ){
                score++;

                if (scoredEnemy < numberOfEnemies - 1){
                    scoredEnemy++;
                }else {
                    scoredEnemy = 0;
                }
            }



            if (Gdx.input.justTouched()){
                velocity = -7;
            }

            for (int i = 0; i <numberOfEnemies;i++){

                if (enemyX[i] < (float) Gdx.graphics.getWidth() / 15){
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;

                    enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight()) -200;
                    enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() -200);
                    enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() -200);

                }else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }

                enemyX[i] = enemyX[i] - enemyVelocity;

                batch.draw(bat1,enemyX[i], (float) Gdx.graphics.getHeight() /2 + enemyOffSet[i], (float) Gdx.graphics.getWidth() / 15, (float) Gdx.graphics.getHeight() / 10);
                batch.draw(bat2,enemyX[i], (float) Gdx.graphics.getHeight() /2 + enemyOffSet2[i], (float) Gdx.graphics.getWidth() / 15, (float) Gdx.graphics.getHeight() / 10);
                batch.draw(bat3,enemyX[i], (float) Gdx.graphics.getHeight() /2 + enemyOffSet3[i], (float) Gdx.graphics.getWidth() / 15, (float) Gdx.graphics.getHeight() / 10);

                enemyCircles[i] = new Circle(enemyX[i] + (float) Gdx.graphics.getWidth() /60, (float) Gdx.graphics.getHeight() /2 + enemyOffSet[i] + (float) Gdx.graphics.getHeight() /40, (float) Gdx.graphics.getWidth() /100);
                enemyCircles2[i] = new Circle(enemyX[i] + (float) Gdx.graphics.getWidth() /60, (float) Gdx.graphics.getHeight() /2 + enemyOffSet2[i] + (float) Gdx.graphics.getHeight() /40, (float) Gdx.graphics.getWidth() /100);
                enemyCircles3[i] = new Circle(enemyX[i] + (float) Gdx.graphics.getWidth() /60, (float) Gdx.graphics.getHeight() /2 + enemyOffSet3[i] + (float) Gdx.graphics.getHeight() /40, (float) Gdx.graphics.getWidth() /100);




            }





            if (dragonY > 0){
                velocity += gravity;
                dragonY -= velocity;
            }else{
                gameState = 2;
            }


        }else if (gameState == 0){
            if (Gdx.input.justTouched()){
                gameState = 1;
            }
        } else if (gameState == 2) {

            font2.draw(batch ,"Game Over! Tap To Play Again!",100, (float) Gdx.graphics.getHeight() / 2);

            if (Gdx.input.justTouched()){
                gameState = 1;

                dragonY = (float) Gdx.graphics.getHeight() / 3;

                for (int i = 0; i < numberOfEnemies; i++) {
                    enemyOffSet[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
                    enemyOffSet2[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
                    enemyOffSet3[i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

                    enemyX[i] = Gdx.graphics.getWidth() + i * distance;

                    enemyCircles[i] = new Circle();
                    enemyCircles2[i] = new Circle();
                    enemyCircles3[i] = new Circle();
                }

                velocity = 0;
                scoredEnemy = 0;
                score = 0;
            }

        }

        batch.draw(dragon,dragonX,dragonY, (float) Gdx.graphics.getWidth() / 15, (float) Gdx.graphics.getHeight() / 10);
        font.draw(batch,String.valueOf(score),100,200);

        batch.draw(dragon,  dragonX, dragonY,  (float) Gdx.graphics.getWidth() / 15, (float) Gdx.graphics.getHeight() / 10);
        batch.end();

        dragonCircle.set(dragonX + (float) Gdx.graphics.getWidth() / 25 ,dragonY + (float) Gdx.graphics.getHeight() / 15, (float) Gdx.graphics.getWidth() / 75);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(dragonCircle.x,dragonCircle.y,dragonCircle.radius);

        for (int i = 0; i < numberOfEnemies; i++) {
            shapeRenderer.circle(enemyX[i] + (float) Gdx.graphics.getWidth() /30, (float) Gdx.graphics.getHeight() /2 + enemyOffSet[i] + (float) Gdx.graphics.getHeight() /20, (float) Gdx.graphics.getWidth() /30);
            shapeRenderer.circle(enemyX[i] + (float) Gdx.graphics.getWidth() /30, (float) Gdx.graphics.getHeight() /2 + enemyOffSet2[i] + (float) Gdx.graphics.getHeight() /20, (float) Gdx.graphics.getWidth() /30);
            shapeRenderer.circle(enemyX[i] + (float) Gdx.graphics.getWidth() /30, (float) Gdx.graphics.getHeight() /2 + enemyOffSet3[i] + (float) Gdx.graphics.getHeight() /20, (float) Gdx.graphics.getWidth() /30);

            if (Intersector.overlaps(dragonCircle,enemyCircles[i]) || Intersector.overlaps(dragonCircle,enemyCircles2[i]) || Intersector.overlaps(dragonCircle,enemyCircles3[i])){
                gameState = 2;
            }

        }
        shapeRenderer.end();
    }

    @Override
    public void dispose() {

    }
}
