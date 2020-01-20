package com.mar.snake.core.constant;

/**
 * Signals that the game engine can receive.
 * @author Marouane
 */
public enum Signal {
   START_GAME, PAUSE_OR_UNPAUSE_GAME, END_GAME,
   MOVE_UP, MOVE_DOWN, MOVE_RIGHT, MOVE_LEFT,
   TURN_LEFT, TURN_RIGHT,
   SPEED_UP, SPEED_DOWN;
}
