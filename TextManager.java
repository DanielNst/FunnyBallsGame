package com.mygdx.game.managers;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class TextManager {

    private static BitmapFont font; // отображаем текст на экране через эту переменную
    // размеры области просмотра нашей игры
    private static float width,height;

    static void initialize(float width, float height){

        font = new BitmapFont(Gdx.files.internal("appetitenew2.fnt"));
        TextManager.width = width;
        TextManager.height= height;
        // устанавливаем цвет шрифта красным
        font.setColor(Color.RED);
        // масштабируем размер шрифта в соответсвии с шириной экрана
        font.getData().setScale(width/1300f);
    }

    static void displayMessage(SpriteBatch batch){

        // объект класса GlyphLayout хранит в себе информацию о шрифте и содержании текста
        GlyphLayout glyphLayout = new GlyphLayout();
        glyphLayout.setText(font, "Score: " + GameManager.score);

        // отображаем лучший результат в левом верхнем углу
        font.draw(batch, "High Score: "+ GameManager.highScore, width/40f,height*0.95f);

        // отображаем результат в правом верхнем углу
        font.draw(batch, glyphLayout, width - width/8f, height*0.95f);
    }

}
