package com.mygdx.game.gameobjects;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.managers.GameManager;
import com.mygdx.game.managers.SpawnManager;
import com.mygdx.game.BasketBall;
import com.mygdx.game.MenuScreen;
public class NotBall{

    public Sprite notballSprite;// спрайт для отображения мяча
    public Vector2 position = new Vector2(); // вектор для обозначения позиции
    public Vector2 velocity = new Vector2(); // вектор для обозначения скорости
    static Vector2 gravity = new Vector2(0,-0.12f);// вектор для обозначения ускорения падения мяча
    private static Vector2 bonus_gravity = new Vector2(0,-0.03f); // вектор для обозначения ускорения падения мяча, если активен бонус
    public Circle notballCircle; //круг для обнаружения столкновений мяча
    public boolean isAlive; // флаг для проверки "жив" мяч или нет

    public void render(SpriteBatch batch){

        notballSprite.draw(batch);

    }

    public void update() {
        if (SpawnManager.bonusActivity) //Проверяем, активен ли бонус, если да - используем бонусную гравитацию, если нет - обычную
        {
            velocity.add(bonus_gravity);
        }
        else {
            velocity.add(gravity);
        }
        // обновление значения переменной velocity путем добавления к нему значения переменной gravity
        position.add(velocity); // обновляем положение мяча в зависимости от скорости
        notballSprite.setPosition(position.x, position.y); // устанавливаем позицию спрайта
        notballCircle.setPosition(position.x + (notballSprite.getWidth()/2), (position.y + notballSprite.getHeight()/2)); //установка позиции круга, который отвечает за обнаружение столкновений

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
        if(Intersector.overlaps(notballCircle, GameManager.basket.basketRectangle)){
            GameManager.GameOverSound.play();
            BasketBall.game.setScreen(new MenuScreen(BasketBall.game)); // Если игрок поймал "неправильный" мяч, то завершаем игру и выходим в главное меню игры
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

