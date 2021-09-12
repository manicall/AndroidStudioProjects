package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class MainGameScreen implements Screen {
    private World world; //переменная для управления миром
    private Box2DDebugRenderer rend; //отладочный отрисовщик тел Мира
    private OrthographicCamera camera; //видеокамера
    private Body rect; //тело прямоугольника

    private int Nblock;
    private float Nf;
    private int flag;


    public MainGameScreen() {
        flag = 1;
    }

    //Процедура создания тела прямоугольника
    private void createRect() {
        //Структура геометрических свойств тела
        BodyDef bDef = new BodyDef();
        //задать телу тип динамического тела (на него действует гравитация)
        bDef.type = BodyDef.BodyType.DynamicBody;
        //задать позицию тела в Мире – в метрах X и Y
        //bDef.position.set(10,13);
        bDef.position.set((int) (Math.random() * 10f + 2f), 14);
        //создание тела в Мире
        rect = world.createBody(bDef);

        //Создать эскиз контура тела в виде приямоугольника 2х2 метра
        PolygonShape shape = new PolygonShape();
        //shape.setAsBox(2,2);
        shape.setAsBox((float) (Math.random() + 0.1f), (float) (Math.random() + 0.1f));
        //Структура физических свойств тела
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;//назначить вид контура тела
        fDef.density = 2;  //назначить плотность тела г/см3
        fDef.restitution = 0.7f;//назначить упругость
        fDef.friction = 0.1f;   //назначить коэф-т трения
        rect.createFixture(fDef);//закрепить свойства за телом
    }


    //Процедура создания тела прямоугольника
    private void createCircle() {

        float positionX = (float) (Math.random() * 10f + 2f);
        float radius = 0.3f;

        //Структура геометрических свойств тела
        BodyDef bDef = new BodyDef();
        //задать телу тип динамического тела (на него действует гравитация)
        bDef.type = BodyDef.BodyType.DynamicBody;
        //задать позицию тела в Мире – в метрах X и Y
        bDef.position.set(positionX, 14);
        //создание тела в Мире
        rect = world.createBody(bDef);

        //Создать эскиз контура тела в виде окружности
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        //Структура физических свойств тела
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;//назначить вид контура тела
        fDef.density = 2;  //назначить плотность тела г/см3
        fDef.restitution = 0.7f;//назначить упругость
        fDef.friction = 0.1f;   //назначить коэф-т трения
        rect.createFixture(fDef);//закрепить свойства за телом
    }

    //Процедура создания внешних стен
    private void createWall() {
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
        bDef.position.set(0, 0);

        Body w = world.createBody(bDef);
        ChainShape shape = new ChainShape();
        //контур стены в виде перевернутой трапеции без основания
        shape.createChain(new Vector2[]{new Vector2(1, 15), new Vector2(1, 1),
                new Vector2(19, 1), new Vector2(19, 15)});

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.friction = 0.1f;
        w.createFixture(fDef);
    }

    //Процедура создания треугольников
    private void createTriangle() {
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0f  , -0.6f  );
        vertices[1] = new Vector2(1f , -0.6f  );
        vertices[2] = new Vector2(1f , 1f);
        //Структура геометрических свойств тела
        BodyDef bDef = new BodyDef();
        //задать телу тип динамического тела (на него действует гравитация)
        bDef.type = BodyDef.BodyType.KinematicBody;
        //задать позицию тела в Мире – в метрах X и Y
        bDef.position.set(10,13);
        //создание тела в Мире

        rect = world.createBody(bDef);
        //Создать эскиз контура тела в виде приямоугольника 2х2 метра
        PolygonShape shape = new PolygonShape();
        //shape.setAsBox(2,2);
        shape.set(vertices);
        //Структура физических свойств тела
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;//назначить вид контура тела
        fDef.density = 2;  //назначить плотность тела г/см3
        fDef.restitution = 0.7f;//назначить упругость
        fDef.friction = 0.1f;   //назначить коэф-т трения
        rect.createFixture(fDef);//закрепить свойства за телом

    }

    @Override
    public void show() {
        //Создание нового мира – задан вектор гравитации в Мире
        world = new World(new Vector2(0, -10), true);
        //Создать камеру с охватом холста 20х15 метров
        camera = new OrthographicCamera(20, 15);
        //Позиционировать камету по центру холста
        camera.position.set(new Vector2(10, 7.5f), 0);
        //Обновление состояния камеры
        camera.update();
        //Создать отладочный отрисовщик
        rend = new Box2DDebugRenderer();

        createTriangle();

        //Вызвыть процедуру создания контуров внешних стен
        //createWall();
    }

    @Override
    public void render(float delta) {

        //Очистка экрана
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Собственно отрисовка
        rend.render(world, camera.combined);//закомменировать после отладки

        //Выполнение расчета нового состояния Мира
        world.step(1 / 60f, 4, 4);

        Nf += delta;
        if (Nf > 0.5f) {
            createCircle();
            Nblock += 1;
            Nf = 0;
        }

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

    }

    @Override
    public void dispose() {

        //Удаление всех тел Мира
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (int i = 0; i < bodies.size; i++) world.destroyBody(bodies.get(i));


        rend.dispose();
        world.dispose();

    }
}
