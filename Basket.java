package com.mygdx.game.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Basket {

    public Sprite basketSprite; //спрайт для отображение корзины
    public Rectangle basketRectangle = new Rectangle();//прямоугольник для проверки столкновений с корзиной

    public void render(SpriteBatch batch){
        basketSprite.draw(batch);
    }
    // устанавливаем положение корзины
    public void setPosition(float x,float y){
        basketSprite.setPosition(x, y);
        basketRectangle.setPosition(x, y);
    }
    // метод для установки корзины в точку касания экрана
    public void handleTouch(float x,float y){
        if(x-(basketSprite.getWidth()/2)>0.0){
            setPosition(x-(basketSprite.getWidth()/2), 0);
        }
        else{
            setPosition(0,0);
        }
    }

}






