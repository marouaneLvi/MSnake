package com.mar.snake.core;

import java.util.Random;

import com.mar.snake.core.constant.DefaultValues;
import com.mar.snake.core.piece.FoodPiece;
import com.mar.snake.core.piece.Piece;
import com.mar.snake.core.piece.Position;

public class FoodLogic {
   /**
    * Used to generate random number for next food position.
    */
   private static final Random RND = new Random();
   /**
    * Necessary to know the surface dimensions(width and size),
    * also to make sure the food to be generated will not overlap
    * an existent piece.
    */
   private final Level level;
   /**
    * Necessary to check that new food does not overlap
    * the snakes parts.
    */
   private final Snake snake;
   /**
    * How long before a piece of food is generated.
    * The delay depends on the game step.
    */
   private final int foodAppearanceDelay;
   /**
    * How long before a piece of food is generated.
    * The delay depends on the game step.
    */
   private final int foodDisappearrenceDelay;

   /**
    * Used to count for the food to appear or disappear.
    */
   private int foodCounter;

   /**
    * Represent the food currently displayed or null if none.
    */
   private Piece foodPiece;

   public FoodLogic(Level level, Snake snake, int foodAppearanceDelay,
         int foodDisappearrenceDelay) {
      this.level = level;
      this.snake = snake;
      this.foodAppearanceDelay = foodAppearanceDelay;
      this.foodDisappearrenceDelay = foodDisappearrenceDelay;
   }

   public FoodLogic(Level level, Snake snake) {
      this(level, snake, DefaultValues.STEPS_FOR_FOOD_TO_APPEAR,
            DefaultValues.STEPS_FOR_FOOD_TO_DISAPPEAR);
   }

   /**
    * Invoked for example when the Snake die and we want to restart same level.
    */
   public void reset() {
      this.clearFood();
   }

   /**
    * Invoked on every game step.
    */
   public void step() {
      ++foodCounter;
      if (this.isFoodPresent()) {
         if (foodCounter == this.foodDisappearrenceDelay) {
            this.clearFood();
         }
      } else {
         if (foodCounter == this.foodAppearanceDelay) {
            this.generateFood();
         }
      }
   }

   /**
    * Check if the Snake head is in contact with the food. 
    * @return true if the Snake head is in contact with the food
    */
   public boolean isFoodInContact() {
      return this.isFoodPresent()
            && this.foodPiece.isCollideWith(this.snake.getHead());
   }

   /**
    * Public because the gameEngine needs to knows where is the food
    * to draw it.
    * @return The food piece if exists, otherwise null
    */
   public Piece getFoodPiece() {
      return this.foodPiece;
   }

   /**
    * Randomly select a free spot in the level,
    * not taken by any level's brick or Snake's part.
    * @return The location
    */
   private Position getRandomFreeSpot() {
      boolean repeat;
      int x;
      int y;
      do {
         repeat = false;
         x = RND.nextInt(this.level.getWidth());
         y = RND.nextInt(this.level.getHeight());

         for (Piece piece : this.snake.getParts()) {
            if (piece.isCollideWith(x, y)) {
               repeat = true;
               break;
            }
         }
         if (!repeat) {
            repeat = !this.level.isSpotEmpty(x, y);
         }
      } while (repeat);
      return new Position(x, y);
   }

   private boolean isFoodPresent() {
      return this.foodPiece != null;
   }

   private void generateFood() {
      this.foodPiece = new FoodPiece(this.getRandomFreeSpot());
      this.foodCounter = 0;
   }

   private void clearFood() {
      this.foodCounter = 0;
      this.foodPiece = null;
   }

}
