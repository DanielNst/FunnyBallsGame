package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.audio.Music;
import com.mygdx.game.managers.GameManager;

public class MenuScreen implements Screen {

    private SpriteBatch batch; // объект дл¤ отрисовки спрайтов нашей игры
    private OrthographicCamera camera; // область просмотра нашей игры

    private Texture startButtonTexture;
    private Texture exitButtonTexture;
    private Texture backGroundTexture;
    private Sprite startButtonSprite;
    private Sprite exitButtonSprite;
    private Sprite backGroundSprite;

    private static float BUTTON_RESIZE_FACTOR = 800f; // задаЄм относительный размер
    private static float START_VERT_POSITION_FACTOR = 2.7f; // задаЄм позицию конпки start
    private static float EXIT_VERT_POSITION_FACTOR = 4.2f; // задаЄм позицию кнопки exit

    private MainGame game; // экземпл¤р класса MainGame нужен дл¤ доступа к вызову метода setScreen

    private Vector3 temp = new Vector3(); // временный вектор дл¤ "захвата" входных координат
    public static Music MenuMusic;
    public MenuScreen (MainGame game){

        this.game = game;

        // получаем размеры экрана устройства пользовател¤ и записываем их в переменнные высоты и ширины
        float height= Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        // устанавливаем переменные высоты и ширины в качестве области просмотра нашей игры
        camera = new OrthographicCamera(width,height);
        // этим методом мы центруем камеру на половину высоты и половину ширины
        camera.setToOrtho(false);
        batch = new SpriteBatch();

        // инициализируем текстуры и спрайты
        startButtonTexture = new Texture(Gdx.files.internal("start_button.png"));
        exitButtonTexture = new Texture(Gdx.files.internal("exit_button.png"));
        backGroundTexture = new Texture(Gdx.files.internal("menubackground.png"));
        startButtonSprite = new Sprite(startButtonTexture);
        exitButtonSprite = new Sprite(exitButtonTexture);
        backGroundSprite = new Sprite(backGroundTexture);
        // устанавливаем размер и позиции
        startButtonSprite.setSize(startButtonSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), startButtonSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        exitButtonSprite.setSize(exitButtonSprite.getWidth() *(width/BUTTON_RESIZE_FACTOR), exitButtonSprite.getHeight()*(width/BUTTON_RESIZE_FACTOR));
        backGroundSprite.setSize(width,height);
        startButtonSprite.setPosition((width/2f -startButtonSprite.getWidth()/2) , width/START_VERT_POSITION_FACTOR);
        exitButtonSprite.setPosition((width/2f -exitButtonSprite.getWidth()/2) , width/EXIT_VERT_POSITION_FACTOR);
        // устанавливаем прозрачность заднего фона
        backGroundSprite.setAlpha(0.2f);
        MenuMusic = Gdx.audio.newMusic(Gdx.files.internal("SoundTrack#8.mp3")); //загружаем в переменную файл с музыкой
        MenuMusic.setLooping(true); // зацикливаем файл с музыкой
        MenuMusic.play(); // запускаем музыку
    }

    private void handleTouch(){
        // ѕровер¤ем были ли касание по экрану?
        if(Gdx.input.justTouched()) {
            // ѕолучаем координаты касани¤ и устанавливаем эти значени¤ в временный вектор
            temp.set(Gdx.input.getX(),Gdx.input.getY(), 0);
            // получаем координаты касани¤ относительно области просмотра нашей камеры
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY= temp.y;
            // обработка касани¤ по кнопке Start
            if((touchX>=startButtonSprite.getX()) && touchX<= (startButtonSprite.getX()+startButtonSprite.getWidth()) && (touchY>=startButtonSprite.getY()) && touchY<=(startButtonSprite.getY()+startButtonSprite.getHeight()) ){
                game.setScreen(new BasketBall(game));// ѕереход к экрану игры
                GameManager.StartGameSound.play();
            }
            // обработка касани¤ по кнопке Exit
            else if((touchX>=exitButtonSprite.getX()) && touchX<= (exitButtonSprite.getX()+exitButtonSprite.getWidth()) && (touchY>=exitButtonSprite.getY()) && touchY<=(exitButtonSprite.getY()+exitButtonSprite.getHeight()) ){
                Gdx.app.exit(); // выход из приложени¤
            }
        }
    }

    @Override
    public void show() {

    }
    @Override
    public void render(float delta) {

        // ќчищаем экран
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);// устанавливаем в экземпл¤р spritebatch вид с камеры (области просмотра)

        //отрисовка игровых объектов
        batch.begin();
        backGroundSprite.draw(batch);
        startButtonSprite.draw(batch);
        exitButtonSprite.draw(batch);
        handleTouch();
        batch.end();

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
        MenuMusic.dispose();
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
        batch.dispose();

    }

}