package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hoxseygames.raidhealer.AdHandler;
import com.hoxseygames.raidhealer.Player;

import java.util.Stack;

/**
 * Created by Hoxsey on 5/27/2017.
 */
public class StateManager {

    private final Stack<State> states;
    private AdHandler adHandler;

    public StateManager()   {
        states = new Stack<>();
    }

    public StateManager(AdHandler adHandler)   {
        states = new Stack<>();
        this.adHandler = adHandler;
    }

    public void push(State state)  {
        states.push(state);
    }

    public void pop()   {
        states.pop();
    }

    public void set(State state)   {
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt)    {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb)  {
        states.peek().render(sb);
    }

    public void loadAd(int controller)   {
        if(adHandler != null)   {
            switch (controller)   {
                case 3:
                    adHandler.showAds(controller);
                    break;
                case 4:
                    adHandler.showAds(controller);
                    break;
            }
        }
    }

    public void showAd(int controller)   {
        if(adHandler != null)   {
            switch (controller)   {
                case 1:
                    adHandler.showAds(controller);
                    break;
                case 2:
                    adHandler.showAds(controller);
                    break;
            }
        }
    }

    public void referencePlayer(Player player) {
        adHandler.importPlayer(player);
    }
}
