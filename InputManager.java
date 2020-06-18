package com.mygdx.game.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.BasketBall;
import com.mygdx.game.MenuScreen;

public class InputManager extends InputAdapter {

    private OrthographicCamera camera;
    private static Vector3 temp = new Vector3();

    public InputManager(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        temp.set(screenX,screenY, 0);
        // получаем координаты касания относительно области просмотра нашей камеры
        camera.unproject(temp);
        float touchX = temp.x;
        float touchY = temp.y;
        GameManager.basket.handleTouch(touchX, touchY);
        handleBackButton(touchX, touchY);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode== Input.Keys.BACK){
            BasketBall.game.setScreen(new MenuScreen(BasketBall.game)); // Переход к экрану меню
        }
        return false;
    }

    private void handleBackButton(float touchX, float touchY){
        // обработка касания/нажатия кнопки "Назад"
        if((touchX>=GameManager.backButtonSprite.getX()) && touchX <=(GameManager.backButtonSprite.getX()+GameManager.backButtonSprite.getWidth()) && (touchY>=GameManager.backButtonSprite.getY()) && touchY<=(GameManager.backButtonSprite.getY()+GameManager.backButtonSprite.getHeight()) ){
            BasketBall.game.setScreen(new MenuScreen(BasketBall.game)); // устанавливаем экран меню
            GameManager.buttonSound.play();
        }
    }

}