package com.mar.snake.api;

import com.mar.snake.core.Statistics;
import com.mar.snake.core.constant.Direction;

/**
 * Clients need to implement this interface
 * and provide the corresponding drawing method
 * for all game objects.
 * @author Marouane
 */
public interface GraphicEngine {
   void drawSnakeHeadPiece(int x, int y, Direction direction);
   void drawSnakePiece(int x, int y);
   void drawDigestingFoodPiece(int x, int y);

   void drawLevelPiece(int x,int y);
   void drawFoodPiece(int x, int y);

   void drawPause();
   void drawGameOver();
   void drawStatistics(Statistics statistics);

   /**
    * After all elements have been drawn, this method
    * will draw everything in the final destination.
    */
   void redrawAll();
}
