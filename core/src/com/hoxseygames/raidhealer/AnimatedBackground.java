package com.hoxseygames.raidhealer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

/**
 * Created by Hoxsey on 8/1/2017.
 */

public class AnimatedBackground extends Actor {

    private ArrayList<ScrollImage> scrollImages;
    private Timer timer;

    public AnimatedBackground() {
        scrollImages = new ArrayList<>();
    }

    public void add(ScrollImage scrollImage)   {
        scrollImages.add(scrollImage);
    }

    public void start() {
        timer = new Timer();
        timer.schedule(new Timer.Task() {

            @Override
            public void run() {
                for(int i = 0; i < scrollImages.size(); i++)   {
                    scrollImages.get(i).move();
                }
            }
        },0.05f,0.05f);

    }

    public void stop()  {
        timer.stop();
        timer.clear();
    }

// --Commented out by Inspection START (5/29/2018 8:12 PM):
//    public ArrayList<ScrollImage> getScrollImages() {
//        return scrollImages;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:12 PM)

// --Commented out by Inspection START (5/29/2018 8:12 PM):
//    public void setScrollImages(ArrayList<ScrollImage> scrollImages) {
//        this.scrollImages = scrollImages;
//    }
// --Commented out by Inspection STOP (5/29/2018 8:12 PM)

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(int i = 0; i < scrollImages.size(); i++)   {
            scrollImages.get(i).draw(batch, parentAlpha);
        }
    }


}
