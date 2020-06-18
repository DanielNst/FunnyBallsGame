package com.mygdx.game.gameobjects;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.managers.GameManager;
import com.mygdx.game.BasketBall;
import com.mygdx.game.MenuScreen;
import com.mygdx.game.gameobjects.Ball;
import com.mygdx.game.gameobjects.NotBall;
public class Bonus {
    public Sprite bonusSprite;// спрайт для отображения мяча
    public Vector2 position = new Vector2(); // вектор для обозначения позиции
    public Vector2 velocity = new Vector2(); // вектор для обозначения скорости
    private final Vector2 gravity = new Vector2(0,-0.12f);// вектор для обозначения ускорения падения мяча
    public Circle bonusCircle; //круг для обнаружения столкновений мяча
    public boolean isAlive; // флаг для проверки "жив" мяч или нет
    public static boolean check_bonus; // флаг для проверки того, активен ли в данный момент бонус
    public void render(SpriteBatch batch){

        bonusSprite.draw(batch);

    }

    public void update() {

        velocity.add(gravity); // обновление значения переменной velocity путем добавления к нему значения переменной gravity
        position.add(velocity); // обновляем положение мяча в зависимости от скорости
        bonusSprite.setPosition(position.x, position.y); // устанавливаем позицию спрайта
        bonusCircle.setPosition(position.x + (bonusSprite.getWidth()/2), (position.y + bonusSprite.getHeight()/2)); //установка позиции круга, который отвечает за обнаружение столкновений

        checkCollisions();

    }//конец метода update

    private boolean checkCollisionsWithGround(){

        // проверяем, упал ли мяч на землю?
        if(position.y <= 0.0){
            GameManager.groundHitSound.play(); // звук падения мяча
            isAlive=false;
            return true;
        }
        return false;
    }//конец метода checkCollisionsWithGround()

    private boolean checkCollisionsWithBasket(){

        // проверка, было ли столкновение между мячом и корзиной
        if(Intersector.overlaps(bonusCircle, GameManager.basket.basketRectangle)){
           GameManager.bonusSound.play();
            check_bonus = true; // переключаем флаг бонуса в положение "Активен"
            isAlive=false;
            return true;
        }
        return false;
    }//конец метода checkCollisionsWithBasket()

    private void checkCollisions(){
        checkCollisionsWithGround();
        checkCollisionsWithBasket();
    }

}

