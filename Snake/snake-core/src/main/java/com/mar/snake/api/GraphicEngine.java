package com.mar.snake.api;

/**
 * Clients need to implement this interface
 * and provide the corresponding drawing method
 * for all game objects.
 * @author Marouane
 */
public interface GraphicEngine {
   String PAUSE_TEXT = "PAUSE";
   String GAME_OVER_TEXT = "GAME_OVER";
   
   void drawSnakeHeadPiece(int x, int y);
   void drawSnakePiece(int x, int y);
   void drawLevelPiece(int x,int y);
   void drawFoodPiece(int x, int y);
   void drawDigestingFoodPiece(int x, int y);
   void drawPause();
   void drawGameOver();

   /**
    * After all elements have been drawn, this method
    * will draw everything in the final destination.
    */
   void redrawAll();
}
