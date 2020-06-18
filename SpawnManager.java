package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.gameobjects.Ball;
import com.mygdx.game.gameobjects.NotBall;
import com.mygdx.game.gameobjects.Bonus;
import com.mygdx.game.gameobjects.Basket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpawnManager {

    static float delayTime = 0.7f;// задержка между по¤влением следующего м¤ча
    static float notballdelayTime = 1.6f;// задержка между по¤влением следующего м¤ча
    static float bonusdelayTime = 6.0f;
    static float bonusActiveTime = 15.0f;
    public static boolean bonusActivity = false;
    private static float delayCounter = 0.0f; // счетчик дл¤ отслеживани¤ задержки
    private static float bonusdelayCounter = 0.0f;
    private static float notballdelayCounter = 0.0f;
    private static float bonusActiveCounter = 0.0f;
    private static float width,height; // ширина и высота области просмотра игры
    private static Texture ballTexture; // текстура м¤ча
    private static final float BALL_RESIZE_FACTOR = 5500f;
    private static Texture notballTexture; // текстура м¤ча
    private static final float NOTBALL_RESIZE_FACTOR = 5500f;
    private static Texture bonusTexture; // текстура м¤ча
    private static final float BONUS_RESIZE_FACTOR = 5500f;
    private static List<Integer> removeIndices = new ArrayList<Integer>();
    private static List<Integer> removenotballIndices = new ArrayList<Integer>();
    private static List<Integer> removebonusIndices = new ArrayList<Integer>();// хранит индексы шаров, которые нужно удалить
    private static Random random = new Random(); // объект класса Random дл¤ генерации случайных чисел

    static void initialize(float width, float height, Texture ballTexture,Texture notballTexture,Texture bonusTexture){

        System.out.println("initialize SpawnManager");

        SpawnManager.width = width;
        SpawnManager.height = height;
        SpawnManager.ballTexture = ballTexture;
        SpawnManager.notballTexture = notballTexture;
        SpawnManager.bonusTexture = bonusTexture;
        delayCounter=0.0f; // сброс счетчика задержки
        bonusdelayCounter = 0.0f;
        notballdelayCounter = 0.0f;
        bonusActiveCounter = 0.0f;
        ballPool.clear(); // очистка pool'а объектов
        notballPool.clear(); // очистка pool'а объектов
        bonusPool.clear(); 
    }

    private final static Pool<Ball> ballPool = new Pool<Ball>() {
        // этот метод запускаетс¤, когда требуетс¤ создание нового экземпл¤ра класса Ball, т.е. когда pool пуст, а объект требуетс¤

        @Override
        protected Ball newObject() {
            Ball ball = new Ball();
            // создание экземпл¤ра спрайта м¤ча
            ball.ballSprite = new Sprite(ballTexture);
            //System.out.println("Hello! I am a new one!"); // дл¤ проверки
            return ball;
        }
    };

    private static Ball resetBall(Ball ball){

        ball.ballSprite.setSize(ball.ballSprite.getTexture().getWidth()*(width/BALL_RESIZE_FACTOR),ball.ballSprite.getTexture().getHeight()*(width/BALL_RESIZE_FACTOR));
        ball.position.set(random.nextInt((int) (width - ball.ballSprite.getWidth())), height-ball.ballSprite.getHeight());
        ball.velocity.set(0, 0);
        ball.isAlive=true;
        Vector2 center = new Vector2();
        // установка центра вектора в центре спрайта м¤ча
        center.x=ball.position.x + (ball.ballSprite.getWidth()/2);
        center.y=ball.position.y + (ball.ballSprite.getHeight()/2);
        ball.ballCircle = new Circle(center, (ball.ballSprite.getHeight()/2));
        return ball;
    }

    static void cleanup(Array<Ball> balls){

        removeIndices.clear(); // очищаем список индексов
        for(int i=balls.size-1 ; i>=0 ; i--){
            if(!balls.get(i).isAlive){
                removeIndices.add(i); // получаем индексы объектов ball, которые нам не нужны (isAlive=false)
            }
        }

        // ”дал¤ем объекты ball из массива, согласно индексу
        for (int i = 0 ; i<removeIndices.size(); i++) {
            Ball ball= balls.removeIndex(i);
            ballPool.free(ball);// return the ball back to the pool
        }
    }

    static void run(Array<Ball> balls){
        // если счетчик delaycounter превысил значение в delayTime
        if(delayCounter>=delayTime){
            Ball ball= ballPool.obtain(); // получаем м¤ч из pool'а м¤чей
            resetBall(ball); // переиницциализаци¤ м¤ча
            balls.add(ball); // добавл¤ем м¤ч к списку
            delayCounter=0.0f;// сбросить счетчик задержки
        }
        else{
            delayCounter += Gdx.graphics.getDeltaTime();
            //в противном случае аккумулируем счетчик задержки
        }
    }
    private final static Pool<NotBall> notballPool = new Pool<NotBall>() {
        // этот метод запускаетс¤, когда требуетс¤ создание нового экземпл¤ра класса Ball, т.е. когда pool пуст, а объект требуетс¤

        @Override
        protected NotBall newObject() {
            NotBall notball = new NotBall();
            // создание экземпл¤ра спрайта м¤ча
            notball.notballSprite = new Sprite(notballTexture);
            //System.out.println("Hello! I am a new one!"); // дл¤ проверки
            return notball;
        }
    };

    private static NotBall resetNotBall(NotBall notball){

        notball.notballSprite.setSize(notball.notballSprite.getTexture().getWidth()*(width/NOTBALL_RESIZE_FACTOR),notball.notballSprite.getTexture().getHeight()*(width/NOTBALL_RESIZE_FACTOR));
        notball.position.set(random.nextInt((int) (width - notball.notballSprite.getWidth())), height-notball.notballSprite.getHeight());
        notball.velocity.set(0, 0);
        notball.isAlive=true;
        Vector2 center1 = new Vector2();
        // установка центра вектора в центре спрайта м¤ча
        center1.x=notball.position.x + (notball.notballSprite.getWidth()/2);
        center1.y=notball.position.y + (notball.notballSprite.getHeight()/2);
        notball.notballCircle = new Circle(center1, (notball.notballSprite.getHeight()/2));
        return notball;
    }

    static void notballcleanup(Array<NotBall> notballs){

        removenotballIndices.clear(); // очищаем список индексов
        for(int i=notballs.size-1 ; i>=0 ; i--){
            if(!notballs.get(i).isAlive){
                removenotballIndices.add(i); // получаем индексы объектов ball, которые нам не нужны (isAlive=false)
            }
        }

        // ”дал¤ем объекты ball из массива, согласно индексу
        for (int i = 0 ; i<removenotballIndices.size(); i++) {
            NotBall notball= notballs.removeIndex(i);
            notballPool.free(notball);// return the ball back to the pool
        }
    }

    static void notballrun(Array<NotBall> notballs){
        // если счетчик delaycounter превысил значение в delayTime
        if(notballdelayCounter>=notballdelayTime){
            NotBall notball= notballPool.obtain(); // получаем м¤ч из pool'а м¤чей
            resetNotBall(notball); // переиницциализаци¤ м¤ча
            notballs.add(notball); // добавл¤ем м¤ч к списку
            notballdelayCounter=0.0f;// сбросить счетчик задержки
        }
        else{
            notballdelayCounter += Gdx.graphics.getDeltaTime();
            //в противном случае аккумулируем счетчик задержки
        }
    }
    private final static Pool<Bonus> bonusPool = new Pool<Bonus>() {
        // этот метод запускаетс¤, когда требуетс¤ создание нового экземпл¤ра класса Ball, т.е. когда pool пуст, а объект требуетс¤

        @Override
        protected Bonus newObject() {
            Bonus bonus = new Bonus();
            // создание экземпл¤ра спрайта м¤ча
            bonus.bonusSprite = new Sprite(bonusTexture);
            //System.out.println("Hello! I am a new one!"); // дл¤ проверки
            return bonus;
        }
    };

    private static Bonus resetBonus(Bonus bonus){

        bonus.bonusSprite.setSize(bonus.bonusSprite.getTexture().getWidth()*(width/BONUS_RESIZE_FACTOR),bonus.bonusSprite.getTexture().getHeight()*(width/BONUS_RESIZE_FACTOR));
        bonus.position.set(random.nextInt((int) (width - bonus.bonusSprite.getWidth())), height-bonus.bonusSprite.getHeight());
        bonus.velocity.set(0, 0);
        bonus.isAlive=true;
        Vector2 center = new Vector2();
        // установка центра вектора в центре спрайта м¤ча
        center.x=bonus.position.x + (bonus.bonusSprite.getWidth()/2);
        center.y=bonus.position.y + (bonus.bonusSprite.getHeight()/2);
        bonus.bonusCircle = new Circle(center, (bonus.bonusSprite.getHeight()/2));
        return bonus;
    }

    static void bonuscleanup(Array<Bonus> bonuses){

        removebonusIndices.clear(); // очищаем список индексов
        for(int i=bonuses.size-1 ; i>=0 ; i--){
            if(!bonuses.get(i).isAlive){
                removebonusIndices.add(i); // получаем индексы объектов ball, которые нам не нужны (isAlive=false)
            }
        }

        // ”дал¤ем объекты ball из массива, согласно индексу
        for (int i = 0 ; i<removebonusIndices.size(); i++) {
            Bonus bonus= bonuses.removeIndex(i);
            bonusPool.free(bonus);// return the ball back to the pool
        }
    }

    static void bonusrun(Array<Bonus> bonuses){
        // если счетчик delaycounter превысил значение в delayTime
        if(bonusdelayCounter>=bonusdelayTime){
            Bonus bonus= bonusPool.obtain(); // получаем м¤ч из pool'а м¤чей
            resetBonus(bonus); // переиницциализаци¤ м¤ча
            bonuses.add(bonus); // добавл¤ем м¤ч к списку
            bonusdelayCounter=0.0f;// сбросить счетчик задержки
        }
        else{
            bonusdelayCounter += Gdx.graphics.getDeltaTime();
            //в противном случае аккумулируем счетчик задержки
        }
    }
    static void ifBonus()
    {
            bonusActivity = true;
            if (bonusActiveCounter <= bonusActiveTime) {
                bonusActiveCounter += Gdx.graphics.getDeltaTime();
            } else {
                Bonus.check_bonus = false;
                bonusActivity = false;
                bonusActiveCounter = 0.0f;
                //в противном случае аккумулируем счетчик задержки
            }

    }
}











