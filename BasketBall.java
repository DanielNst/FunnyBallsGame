package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameManager;
import com.mygdx.game.managers.InputManager;


public class BasketBall implements Screen {

    private SpriteBatch batch; // объект дл¤ отрисовки спрайтов нашей игры
    private OrthographicCamera camera; // область просмотра нашей игры
    public static MainGame game; // экземпл¤р класса MainGame нужен дл¤ доступа к вызову метода setScreen

    BasketBall(MainGame game) {

        BasketBall.game = game;

        // получаем размеры экрана устройства пользовател¤ и записываем их в переменнные высоты и ширины
        float height = Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        camera = new OrthographicCamera(width,height);// устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
        camera.setToOrtho(false);// этим методом мы центруем камеру на половину высоты и половину ширины
        batch = new SpriteBatch();
        //вызываем метод initialize класса GameManager
        GameManager.initialize(width, height);
        Gdx.input.setInputProcessor(new InputManager(camera));// доступ класса InputManager дл¤ получени¤ касаний/нажатий

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        batch.dispose();
        GameManager.backgroundMusic.stop();
        GameManager.dispose();
    }

    @Override
    public void render (float delta) {

        Gdx.gl.glClearColor(1, 1, 1, 1);// ќчищаем экран
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined); // устанавливаем в экземпл¤р spritebatch вид с камеры (области просмотра)
        //отрисовка игровых объектов
        batch.begin();
        GameManager.renderGame(batch);
        batch.end();
    }

}