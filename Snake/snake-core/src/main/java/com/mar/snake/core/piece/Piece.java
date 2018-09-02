package com.mar.snake.core.piece;

public class Piece extends Position {

   public Piece(int x, int y) {
      super(x, y);
   }
   public Piece(Piece piece) {
      this(piece.x, piece.y);
   }

   public Piece copy() {
      return new Piece(x, y);
   }
   public boolean isCollideWith(int x, int y) {
      return this.x == x && this.y == y;
   }
   public boolean isCollideWith(Piece piece) {
      return isCollideWith(piece.x, piece.y);
   }
}