package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by Hoxsey on 9/27/2017.
 */

public class BlinkingOutline extends Actor {

    private boolean isBlink;
    private Timer timer;
    private float speed;
    private ShapeRenderer shapeRenderer;
    private Rectangle outline;

    public BlinkingOutline()   {
        speed = 0.5f;
        outline = new Rectangle();
        shapeRenderer = new ShapeRenderer();
    }

    public void setOutline(Rectangle newOutline)    {
        outline = newOutline;
    }

    public void start() {
        timer = new Timer();

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                isBlink = !isBlink;
            }
        },speed, speed);
    }
    public void stop()  {
        timer.stop();
        timer.clear();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!isBlink) {
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.GREEN);
                shapeRenderer.rect(outline.getX(),outline.getY(), outline.getWidth(), outline.getHeight());
            shapeRenderer.end();
        }
    }
}
