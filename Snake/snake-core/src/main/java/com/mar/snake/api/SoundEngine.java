package com.mar.snake.api;

/**
 * Clients need to implement this interface
 * and provide the corresponding play sound method
 * for all game events.
 * @author Marouane
 */
public interface SoundEngine {
   void playSnakeDie();
   void playSnakeEatPiece();
   void playSoundTrak();
}
