package com.mar.snake.core;

import java.util.ArrayList;
import java.util.List;

import com.mar.snake.core.constant.DefaultValues;
import com.mar.snake.core.piece.LevelPiece;
import com.mar.snake.core.piece.Piece;

/**
 * The level where the action is happening!
 * Represented as a vertical rectangle with a width and height,
 * and the list of all bricks/walls which our hero should not
 * collide. 
 * @author Marouane
 */
public class Level {
   /**
    * Bricks/Blocks/Walls.
    */
   private List<Piece> pieces;
   private final int groundWidth;
   private final int groundHeight;

   public Level() {
      this(DefaultValues.LEVEL_WIDTH, DefaultValues.LEVEL_HEIGHT);
   }

   public Level(int groundWidth, int groundHeight) {
      this.groundWidth = groundWidth;
      this.groundHeight = groundHeight;
      this.reset();
   }

   public final void reset() {
      this.pieces = new ArrayList<Piece>();

      // Create the top and bottom bricks of the level
      for (int iCnt = 0; iCnt < groundWidth;++iCnt) {
         this.pieces.add(new LevelPiece(iCnt, 0));
         this.pieces.add(new LevelPiece(iCnt, groundHeight - 1));
      }

      // Create the left and right bricks of the level
      for (int iCnt = 1; iCnt < groundHeight - 1;++iCnt) {
         this.pieces.add(new LevelPiece(0, iCnt));
         this.pieces.add(new LevelPiece(groundWidth - 1, iCnt));
      }
   }

   public int getWidth() {
      return this.groundWidth;
   }

   public int getHeight() {
      return this.groundHeight;
   }

   public void fillSpot(Piece piece) {
      this.pieces.add(piece);
   }

   public void fillSpot(int x, int y) {
      this.fillSpot(new Piece(x, y));
   }

   public void clearSpot(int x, int y) {
      int idx = this.getPieceIndex(x, y);
      if (idx >= 0) {
         this.pieces.remove(idx);
      }
   }

   public void clearSpot(Piece piece) {
      clearSpot(piece.getX(), piece.getY());
   }

   public Piece getSpot(int x, int y) {
      int idx = this.getPieceIndex(x, y);
      if (idx >= 0) {
         return this.pieces.get(idx);
      }
      return null;
   }

   private int getPieceIndex(int x, int y) {
      for (int iCnt = 0; iCnt < this.pieces.size(); ++iCnt) {
         if (this.pieces.get(iCnt).isCollideWith(x, y)) {
            return iCnt;
         }
      }
      return -1;
   }

   public boolean isSpotEmpty(int x, int y) {
      return this.getSpot(x, y) == null;
   }

   public List<Piece> getPieces() {
      return pieces;
   }
}
