package com.mar.snake.core;

import java.util.ArrayList;
import java.util.List;

import com.mar.snake.core.constant.DefaultValues;
import com.mar.snake.core.constant.Direction;
import com.mar.snake.core.piece.Piece;
import com.mar.snake.core.piece.SnakePiece;

public class Snake {
   /**
    * How long should be the Snake at the start.
    */
   public final int startPiecesCount;
   /**
    * From where should the snake start on the X axis.
    */
   public final int startX;
   /**
    * From where should the snake start on the Y axis.
    */
   public final int startY;
   /**
    * Which direction should the Snake start moving to.
    */
   public final Direction startDirection;
   /**
    * How much steps should the snake jump by.
    */
   public final int moveByStep;

   /**
    * The pieces representing the Snake.
    */
   private List<SnakePiece> parts;
   /**
    * Direction the Snake is currently moving to.  
    */
   private Direction direction;
   /**
    * Next Direction the Engine transmitted.
    */
   public Direction nextDirection;
   private Level level;

   private List<Integer> digestionProcess = new ArrayList<Integer>();

   public void reset() {
      this.parts = new ArrayList<SnakePiece>(this.startPiecesCount);
      for (int iCnt = 0; iCnt < this.startPiecesCount; ++iCnt) {
         this.parts.add(new SnakePiece(this.startX, this.startY));
      }
      this.digestionProcess.clear();
      this.direction = this.nextDirection = this.startDirection;
   }

   public Snake(Level ground, Direction startDirection, int startPiecesCount,
         int startX, int startY, int moveByStep) {
      this.level = ground;
      this.startDirection = startDirection;
      this.startPiecesCount = startPiecesCount;
      this.startX = startX;
      this.startY = startY;
      this.moveByStep = moveByStep;
      this.reset();
   }

   public Snake(Level ground, Direction startDirection, int startPiecesCount,
         int startX, int startY) {
      this(ground, startDirection, startPiecesCount,
            DefaultValues.STARTING_POSITION_X, DefaultValues.STARTING_POSITION_Y,
            DefaultValues.MOVE_BY_STEP);
   }

   public Snake(Level ground, Direction startDirection, int startPiecesCount) {
      this(ground, startDirection, startPiecesCount,
            DefaultValues.STARTING_POSITION_X, DefaultValues.STARTING_POSITION_Y);
   }

   public Snake(Level ground, Direction startDirection) {
      this(ground, startDirection, DefaultValues.STARTING_BODY_PIECES);
   }

   public Snake(Level ground) {
      this(ground, DefaultValues.STARTING_DIRECTION,
            DefaultValues.STARTING_BODY_PIECES);
   }

   public Direction getDirection() {
      return this.direction;
   }

   public void setDirection(Direction direction) {
      this.nextDirection = direction;
   }

   public void step() {
      final Piece head = this.getHead();
      shiftPieces();
      int nextPos;
      this.direction = this.nextDirection;
      switch (this.direction) {
      case UP:
         nextPos = head.getY() + this.moveByStep;
         if (nextPos < this.level.getHeight()) {
            head.setY(nextPos);
         } else {
            head.setY(nextPos - this.level.getHeight());
         }
         break;
      case RIGHT:
         nextPos = head.getX() + this.moveByStep;
         if (nextPos < this.level.getWidth()) {
            head.setX(nextPos);
         } else {
            head.setX(nextPos - this.level.getWidth());
         }
         break;
      case DOWN:
         nextPos = head.getY() - this.moveByStep;
         if (nextPos >= 0) {
            head.setY(nextPos);
         } else {
            head.setY(this.level.getHeight() + nextPos);
         }
         break;
      case LEFT:
         nextPos = head.getX() - this.moveByStep;
         if (nextPos >= 0) {
            head.setX(nextPos);
         } else {
            head.setX(this.level.getWidth() + nextPos);
         }
         break;
      }
   }

   private void shiftPieces() {
      for (int iCnt = 0; iCnt < this.digestionProcess.size(); ++iCnt) {
         this.digestionProcess.set(iCnt, this.digestionProcess.get(iCnt) + 1);
         if (this.digestionProcess.get(iCnt) >= this.parts.size()) {
            this.digestionProcess.remove(iCnt);
            --iCnt;
         }
      }

      for (int iCnt = this.parts.size() - 1; iCnt > 0; --iCnt) {
         final SnakePiece newPos = this.parts.get(iCnt - 1);
         final SnakePiece oldPos = this.parts.get(iCnt);
         oldPos.moveTo(newPos);
      }
   }

   public void grow() {
      this.parts.add(getTail().copy());
      this.digestionProcess.add(0);
   }

   public boolean isDigestingPart(Piece piece) {
      for (int iCnt = 0; iCnt < this.digestionProcess.size(); ++iCnt) {
         if (this.parts.get(this.digestionProcess.get(iCnt)) == piece) {
            return true;
         }
      }
      return false;
   }

   public int getSize() {
      return this.parts.size();
   }

   public List<SnakePiece> getParts() {
      return parts;
   }

   public boolean isCollideWith(Piece piece) {
      return this.getHead().isCollideWith(piece);
   }

   public SnakePiece getHead() {
      return this.parts.get(0);
   }

   public SnakePiece getTail() {
      return this.parts.get(this.parts.size() - 1);
   }

   public List<Integer> getDigetionProcess() {
      return this.digestionProcess;
   }

   public boolean isHurtingHimself() {
      for (int iCnt = 3; iCnt < this.parts.size(); ++iCnt) {
         if (this.getHead().isCollideWith(this.parts.get(iCnt))) {
            return true;
         }
      }
      return false;
   }

   public void goToDirection(Direction direction) {
      switch (direction) {
      case LEFT:
         this.moveLeft();
         break;
      case UP:
         this.moveUp();
         break;
      case RIGHT:
         this.moveRight();
         break;
      case DOWN:
         this.moveDown();
         break;
      }
   }

   public void moveLeft() {
      if (this.getDirection() != Direction.RIGHT) {
         this.setDirection(Direction.LEFT);
      }
   }

   public void moveRight() {
      if (this.getDirection() != Direction.LEFT) {
         this.setDirection(Direction.RIGHT);
      }
   }

   public void moveUp() {
      if (this.getDirection() != Direction.DOWN) {
         this.setDirection(Direction.UP);
      }
   }

   public void moveDown() {
      if (this.getDirection() != Direction.UP) {
         this.setDirection(Direction.DOWN);
      }
   }

   public void toTheRight() {
      final Direction[] directions = Direction.values();
      if (this.direction.ordinal() == directions.length - 1)
         nextDirection = directions[0];
      else
         nextDirection = directions[direction.ordinal() + 1];
   }

   public void toTheLeft() {
      final Direction[] directions = Direction.values();
      if (this.direction.ordinal() == 0)
         this.nextDirection = directions[directions.length - 1];
      else
         this.nextDirection = directions[this.direction.ordinal() - 1];
   }
}
