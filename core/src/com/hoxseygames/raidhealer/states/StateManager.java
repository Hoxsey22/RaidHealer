package com.hoxseygames.raidhealer.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

/**
 * Created by Hoxsey on 5/27/2017.
 */
public class StateManager {

    private final Stack<State> states;

    public StateManager()   {
        states = new Stack<>();
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

}
