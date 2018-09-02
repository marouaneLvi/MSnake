package com.mar.snake.core;

import java.util.ArrayList;
import java.util.List;

import com.mar.snake.api.GraphicEngine;
import com.mar.snake.api.SoundEngine;
import com.mar.snake.core.constant.DefaultValues;
import com.mar.snake.core.FoodLogic;
import com.mar.snake.core.Level;
import com.mar.snake.core.constant.Signal;
import com.mar.snake.core.Snake;
import com.mar.snake.core.piece.LevelPiece;
import com.mar.snake.core.piece.Piece;

public class GameEngine {

   private GraphicEngine graphicsEngine;
   private SoundEngine soundEngine;

   private final Snake snake;
   private final Level level;
   private final FoodLogic foodLogic;

   private boolean paused;
   private boolean gameover;
   private int speedInMs = DefaultValues.GAME_SPEED_IN_MS;

   public GameEngine() {
      this.level = new Level();
      this.snake = new Snake(this.level);
      this.foodLogic = new FoodLogic(this.level, this.snake);
   }

   public int getGroundWidth() {
      return this.level.getWidth();
   }

   public int getGroundHeight() {
      return this.level.getHeight();
   }

   public void initializeEngine(GraphicEngine graphicsEngine, SoundEngine soundEngine) {
      this.graphicsEngine = graphicsEngine;
      this.soundEngine = soundEngine;
   }

   private void step() {
      this.foodLogic.step();
      this.snake.step();
      handleCollision();
   }

   private void handleCollision() {
      if (this.foodLogic.isFoodInContact()) {
         this.soundEngine.playSnakeEatPiece();
         this.foodLogic.reset();
         this.snake.grow();
      }

      if (this.snake.isHurtingHimself()) {
         this.endGame();
      }

      final Piece sHead = this.snake.getHead();
      // Snake collide with a piece in the ground
      final Piece piece = this.level.getSpot(sHead.getX(), sHead.getY());

      if (piece instanceof LevelPiece) {
         this.endGame();
      }

   }

   public boolean isGameOver() {
      return this.gameover;
   }

   private void endGame() {
      this.soundEngine.playSnakeDie();
      this.gameover = true;
   }

   public void redraw() {
      for (Piece piece : this.level.getPieces()) {
         this.graphicsEngine.drawLevelPiece(piece.getX(), piece.getY());
      }
      List<Piece> digestingPieces = new ArrayList<Piece>();
      for (Piece snPiece : this.snake.getParts()) {
         if (snPiece == this.snake.getHead()) {
            this.graphicsEngine.drawSnakeHeadPiece(snPiece.getX(), snPiece.getY());
         } else {
            if (!this.snake.isDigestingPart(snPiece)) {
               this.graphicsEngine.drawSnakePiece(snPiece.getX(), snPiece.getY());
            } else {
               digestingPieces.add(snPiece);
            }
         }
      }

      for (Piece pc : digestingPieces) {
         this.graphicsEngine.drawDigestingFoodPiece(pc.getX(), pc.getY());
      }
      final Piece foodPiece = this.foodLogic.getFoodPiece();
      if (foodPiece != null) {
         this.graphicsEngine.drawFoodPiece(foodPiece.getX(), foodPiece.getY());
      }

      if (this.gameover)
         this.graphicsEngine.drawGameOver();

      if (this.paused)
         this.graphicsEngine.drawPause();
   }

   private void startGame() {
      this.soundEngine.playSoundTrak();
      this.graphicsEngine.redrawAll();

      new Thread(new Runnable() {
         @Override
         public void run() {
            while (!GameEngine.this.gameover) {
               if (!GameEngine.this.paused)
                  step();
               GameEngine.this.graphicsEngine.redrawAll();
               try {
                  Thread.sleep(GameEngine.this.speedInMs);
               } catch (InterruptedException e) {
               }
            }
         }
      }).start();
   }

   public void sendSignal(Signal signal) {
      if (signal == Signal.START_GAME) {
         if (this.gameover) {
            this.snake.reset();
            this.level.reset();
            this.foodLogic.reset();
            this.gameover = false;
         }

         this.startGame();
         return;
      }

      if (signal == Signal.PAUSE_OR_UNPAUSE_GAME) {
         this.paused = !this.paused;
         return;
      }

      if (this.paused) {
         return;
      }

      switch (signal) {
      case SPEED_UP:
         this.speedInMs -= 10;
         break;
      case SPEED_DOWN:
         this.speedInMs += 10;
         break;
      case MOVE_LEFT:
         this.snake.moveLeft();
         break;
      case MOVE_UP:
         this.snake.moveUp();
         break;
      case MOVE_RIGHT:
         this.snake.moveRight();
         break;
      case MOVE_DOWN:
         this.snake.moveDown();
         break;
      case TURN_LEFT:
         this.snake.toTheLeft();
         break;
      case TURN_RIGHT:
         this.snake.toTheRight();
         break;
      default:
         break;
      }
   }
}