package com.mar.snake.core.piece;

/**
 * Levels blocks/bricks/walls are made from this kind of piece. 
 * @author Marouane
 */
public class LevelPiece extends Piece {

   public LevelPiece(int x, int y) {
      super(x, y);
   }

   public LevelPiece(Piece piece) {
      this(piece.getX(), piece.getY());
   }
}