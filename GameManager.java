package com.mygdx.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MenuScreen;
import com.mygdx.game.gameobjects.Ball;
import com.mygdx.game.gameobjects.NotBall;
import com.mygdx.game.gameobjects.Basket;
import com.mygdx.game.gameobjects.Bonus;
import java.util.Random;

public class GameManager {
    private static Random random = new Random(); // объект класса Random дл¤ генерации случайных чисел
    public static Basket basket; // экземпл¤р корзины
    private static Texture basketTexture; // текстура корзины
    private static Sprite backgroundSprite; // спрайт заднего фона
    private static Texture backgroundTexture; // текстура дл¤ заднего фона
    private static float BASKET_RESIZE_FACTOR = 3000f; // переменна¤ дл¤ масштабировани¤ корзины
    public static Ball ball; // экземпл¤р м¤ча
    private static Texture ballTexture; // текстура дл¤ м¤ча
    public static NotBall notball; // Notball
    private static Texture notballTexture; // Notball texture
    public static Bonus bonus; // Notball
    private static Texture bonusTexture; // Notball texture
    private static final float BALL_RESIZE_FACTOR = 5500f;
    private static Array<Ball> balls = new Array<Ball>(); // массив объектов ball
    private static Array<NotBall> notballs = new Array<NotBall>(); // массив объектов notball
    private static Array<Bonus> bonuses = new Array<Bonus>(); // массив объектов bonuses
    public static int score; // текущий счёт игрока
    public static int highScore; // лучший результат
    private static Preferences prefs; // объект класса Preferences
    static Sprite backButtonSprite; // спрайт кнопки "Ќазад"
    private static Texture backButtonTexture; // текстура кнопки "Ќазад"
    public static Sound groundHitSound; //экземпл¤р звука, проигрываемый при ударе м¤ча об "земллю"
    //public static Sound basketHitSound; //экземпл¤р звука, проигрываемый при попадании м¤ча в корзину
    public static Sound bonusSound; // экземпляр звука, проигрываемый при взятии бонуса
    public static Music backgroundMusic;//экземпл¤р фоновой музыки
    public static Sound buttonSound; // экземпляр звука кнопки выхода в меню
    public static Sound ballCatchSound; // экземпляр звука пойманного мяча
    public static Sound GameOverSound; // экземпляр звука проигрыша
    public static Sound StartGameSound; // экземпляр звука кнопки начала игры
    public static void initialize(float width,float height){


        int random_soundtrack = random.nextInt(6); // выбираем случайный номер саундтрека игры
        System.out.println("initialize GameManager");
        System.out.printf("Random_soundtrack_number is: %d",random_soundtrack);
        score=0;

        Gdx.input.setCatchBackKey(true); // ловим касание по системной кнопке "Ќазад"

        prefs = Gdx.app.getPreferences("My Preferences"); //получаем файл персональных данных
        highScore = prefs.getInteger("highscore"); //получаем текущий лучший результат
        basket = new Basket();
        basketTexture = new Texture(Gdx.files.internal("basket.png"));// загружаем текстуру корзины в наш проект
        basket.basketSprite = new Sprite(basketTexture);// загружаем текстуру корзины в спрайт
        basket.basketSprite.setSize(basket.basketSprite.getWidth()*(width/BASKET_RESIZE_FACTOR), basket.basketSprite.getHeight()*(width/BASKET_RESIZE_FACTOR)); // устанавливаем размер спрайта
        basket.setPosition(0, 0);// установим позицию корзины в левом нижнем углу
        //задаем размер пр¤моуголькика, ограничивающего нашу корзину
        basket.basketRectangle.setSize(basket.basketSprite.getWidth(), basket.basketSprite.getHeight());

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));// загружаем текстуру заднего фона в наш проект
        backgroundSprite= new Sprite(backgroundTexture); // загружаем текстуру заднего фона в спрайт
        backgroundSprite.setSize(width, height); // устанавливаем размер заднего фона по размеру экрана предполагаемого устройства
        // загружаем текстуры мячей
        ballTexture = new Texture(Gdx.files.internal("peka.gif"));
        notballTexture = new Texture(Gdx.files.internal("pekaangry.gif"));
        bonusTexture = new Texture(Gdx.files.internal("bonus.png"));
        //загружаем текстуру кнопки "Ќазад"
        backButtonTexture = new Texture(Gdx.files.internal("backbutton.png"));
        //устанавливаем текстуру кнопки "Ќазад в спрайт"
        backButtonSprite= new Sprite(backButtonTexture);
        backButtonSprite.setSize(backButtonSprite.getWidth()* (width/1500), backButtonSprite.getHeight()* (width/1500));
        // устанавливаем позицию кнопки назад вверху экрана по центру
        backButtonSprite.setPosition(width/2- backButtonSprite.getWidth()/2, height*0.935f);
        // инициализируем игровые объекты, текст на экране и остнаваливаем воспроизведение музыки из меню
        SpawnManager.initialize(width, height, ballTexture,notballTexture,bonusTexture);
        TextManager.initialize(width, height);
        MenuScreen.MenuMusic.stop();
        //загружаем в переменные звуковые файлы в зависимости от значения random_soundtrack
        switch (random_soundtrack){
            case 0 :
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SoundTrack#1.mp3")); //загружаем в переменную файл с музыкой
                break;
            case 1 :
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SoundTrack#2.mp3")); //загружаем в переменную файл с музыкой
                break;
            case 2 :
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SoundTrack#3.mp3")); //загружаем в переменную файл с музыкой
                break;
            case 3 :
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SoundTrack#4.mp3")); //загружаем в переменную файл с музыкой
                break;
            case 4 :
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SoundTrack#5.mp3")); //загружаем в переменную файл с музыкой
                break;
            case 5 :
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SoundTrack#6.mp3")); //загружаем в переменную файл с музыкой
                break;
            case 6 :
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("SoundTrack#7.mp3")); //загружаем в переменную файл с музыкой
                break;
        }
        // загружаем файлы звуков
        groundHitSound = Gdx.audio.newSound(Gdx.files.internal("Catch4.mp3"));
        bonusSound = Gdx.audio.newSound(Gdx.files.internal("Bonus.mp3"));
        ballCatchSound = Gdx.audio.newSound(Gdx.files.internal("Catch1.mp3"));
        GameOverSound = Gdx.audio.newSound(Gdx.files.internal("Gameover2.mp3"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("Button.mp3"));
        StartGameSound= Gdx.audio.newSound(Gdx.files.internal("Score.mp3"));
        backgroundMusic.setLooping(true); // зацикливаем файл с музыкой
        backgroundMusic.play(); // запускаем музыку
    } // конец метода initialize()

    public static void renderGame(SpriteBatch batch){

        backgroundSprite.draw(batch);
        basket.render(batch);
        //отрисовка кнопки "Ќазад"
        backButtonSprite.draw(batch);
        TextManager.displayMessage(batch);
        if (Bonus.check_bonus)
        {
            SpawnManager.ifBonus(); // метод для бонуса
        }
        SpawnManager.run(balls); // методы для рендера разных видов мячей
        for(Ball ball:balls) {

            if (ball.isAlive) {
                ball.update();
                ball.render(batch);
            }
        }
        SpawnManager.cleanup(balls);
        SpawnManager.notballrun(notballs);
        for(NotBall notball:notballs) {

            if (notball.isAlive) {
                notball.update();
                notball.render(batch);
            }
        }
        SpawnManager.notballcleanup(notballs);
        SpawnManager.bonusrun(bonuses);
        for(Bonus bonus:bonuses) {

            if (bonus.isAlive) {
                bonus.update();
                bonus.render(batch);
            }
        }
        SpawnManager.bonuscleanup(bonuses);
    }

    public static void dispose() {
        prefs.putInteger("highscore", highScore);
        prefs.flush(); //убедитьс¤, что настройки сохранены

        //избавл¤емс¤ от текстур, чтобы не захламл¤ть пам¤ть устройства
        backgroundTexture.dispose();
        basketTexture.dispose();
        ballTexture.dispose();
        notballTexture.dispose();
        bonusTexture.dispose();
        backButtonTexture.dispose();
        //избавл¤емс¤ от экземпл¤ров звуковых файлов
        ballCatchSound.dispose();
        bonusSound.dispose();
        groundHitSound.dispose();
        StartGameSound.dispose();
        GameOverSound.dispose();
        buttonSound.dispose();
        backgroundMusic.dispose();

        balls.clear();
        notballs.clear();
        bonuses.clear();

    }

}



