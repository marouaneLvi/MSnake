package com.mar.snake.core.piece;

/**
 * The pieces that the snake is made from. 
 * @author Marouane
 */
public class SnakePiece extends Piece {

   public SnakePiece(int x, int y) {
      super(x, y);
   }

   public SnakePiece(Piece piece) {
      this(piece.getX(), piece.getY());
   }

   public SnakePiece copy() {
      return new SnakePiece(this);
   }

   public void moveTo(int x, int y) {
      this.x = x;
      this.y = y;
   }
   public void moveTo(Piece piece) {
      moveTo(piece.x, piece.y);
   }
}
