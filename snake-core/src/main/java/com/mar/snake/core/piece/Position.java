package com.mar.snake.core.piece;

public class Position {
   protected int x;
   protected int y;
   public Position(int x, int y) {
      this.x = x;
      this.y = y;
   }

   public int getX() {
      return x;
   }
   public void setX(int x) {
      this.x = x;
   }
   public int getY() {
      return y;
   }
   public void setY(int y) {
      this.y = y;
   }
}
