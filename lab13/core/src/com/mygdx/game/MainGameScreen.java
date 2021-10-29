package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.utils.Array;

import sun.management.Sensor;

public class MainGameScreen implements Screen {
    
    private World world; //переменная для управления миром
    private Box2DDebugRenderer rend; //отладочный отрисовщик тел Мира
    private OrthographicCamera camera; //видеокамера
    private Body body; //тело прямоугольника

    private int Nblock;
    private float Nf;


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

        createJoint();
    }

    @Override
    public void render(float delta) {
        //Очистка экрана
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Собственно отрисовка
        rend.render(world, camera.combined);//закомменировать после отладки

        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        //Выполнение расчета нового состояния Мира
        world.step(1 / 10f, 0, 400);
        world.setGravity(new Vector2(Gdx.input.getAccelerometerY(), -Gdx.input.getAccelerometerX()));
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

    //функция создания тела треугольника
    private Body createTriangle(BodyDef.BodyType type, Vector2 position) {
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0f  , -0.6f);
        vertices[1] = new Vector2(3f , -0.6f);
        vertices[2] = new Vector2(1.5f , 1.5f);

        //Структура геометрических свойств тела
        body = world.createBody(getStandardBodyDef(type, position));
        //Создать эскиз контура тела в виде приямоугольника 2х2 метра
        PolygonShape shape = new PolygonShape();
        shape.set(vertices);
        body.createFixture(getCommonFixtureDef(shape)); //закрепить свойства за телом
        return body;
    }

    //функция создания тела круга
    private Body createCircle(BodyDef.BodyType type, Vector2 position, float radius, boolean rotation) {
        body = world.createBody(getStandardBodyDef(type, position));
        //Создать эскиз контура тела в виде окружности
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        if (rotation){
            body.setAngularVelocity(-4f);
        }
        body.createFixture(getCommonFixtureDef(shape));//закрепить свойства за телом
        return body;
    }
    // общие настройки геометрических свойств тела
    private BodyDef getStandardBodyDef(BodyDef.BodyType type, Vector2 position){
        //Структура геометрических свойств тела
        BodyDef bDef = new BodyDef();
        //задать телу тип динамического тела (на него действует гравитация)
        bDef.type = type;
        //задать позицию тела в Мире – в метрах X и Y
        bDef.position.set(position.x, position.y);

        //создание тела в Мире
        return bDef;
    }
    // общие настройки физических свойств  тела
    private FixtureDef getCommonFixtureDef(Shape shape){
        //Структура физических свойств тела
        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;//назначить вид контура тела
        fDef.density = 2;  //назначить плотность тела г/см3
        fDef.restitution = 0.7f;//назначить упругость
        fDef.friction = 0.1f;   //назначить коэф-т трения
        return fDef;
    }

    private Joint createJoint(){
        final float XOFFSET = camera.viewportWidth/2;
        final float YOFFSET = camera.viewportHeight/2;
        Vector2 trianglePosition = new Vector2(camera.viewportWidth/2 - 1.5f, camera.viewportHeight/2 - 3);
        Vector2 dotPosition = new Vector2(camera.viewportWidth/2,camera.viewportHeight/2);

        Body triangle = createTriangle(BodyDef.BodyType.DynamicBody, trianglePosition);
        Body dot = createCircle(BodyDef.BodyType.KinematicBody, dotPosition, 0.01f, false);

        RopeJointDef rDef = new RopeJointDef();
        rDef.maxLength = 1.5f;
        rDef.collideConnected = true;
        rDef.bodyA = dot;
        rDef.bodyB = triangle;
        rDef.localAnchorA.set(0, 0);
        rDef.localAnchorB.set(1.5f, 1.5f);

        return world.createJoint(rDef);
    }

    // функция возвращаяющая случайное число в заданном диапазоне
    static float rnd(float min, float max)
    {
        max -= min;
        return (float) (Math.random() * ++max) + min;
    }
}
