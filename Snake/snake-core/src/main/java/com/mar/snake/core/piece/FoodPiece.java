package com.mar.snake.core.piece;

/**
 * It's what keep our snake hero alive. 
 * @author Marouane
 */
public class FoodPiece extends Piece {

   public FoodPiece(int x, int y) {
      super(x, y);
   }

   public FoodPiece(Position position) {
      this(position.getX(), position.getY());
   }
}