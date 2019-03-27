package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
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
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            if (adHandler != null) {
                switch (controller) {
                    case 3:
                        adHandler.showAds(controller);
                        break;
                    case 4:
                        adHandler.showAds(controller);
                        break;
                }
            }
        }
    }

    public boolean showAd(int controller)   {
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            if (adHandler != null) {
                adHandler.showAds(controller);
                return true;
            }
            return false;
        }
        return false;
    }

    public void referencePlayer(Player player) {
        adHandler.importPlayer(player);
    }
}
