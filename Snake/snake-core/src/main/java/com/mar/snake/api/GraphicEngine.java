package com.mar.snake.api;

import com.mar.snake.core.Statistics;

/**
 * Clients need to implement this interface
 * and provide the corresponding drawing method
 * for all game objects.
 * @author Marouane
 */
public interface GraphicEngine {
   String PAUSE_TEXT = "PAUSE";
   String GAME_OVER_TEXT = "GAME_OVER";
   String SCORE = "Score : %d";
   String ELAPSED_TIME = "Passed Time : %s";
   
   void drawSnakeHeadPiece(int x, int y);
   void drawSnakePiece(int x, int y);
   void drawLevelPiece(int x,int y);
   void drawFoodPiece(int x, int y);
   void drawDigestingFoodPiece(int x, int y);
   void drawPause();
   void drawGameOver();
   void drawStatistics(Statistics statistics);

   /**
    * After all elements have been drawn, this method
    * will draw everything in the final destination.
    */
   void redrawAll();
}
