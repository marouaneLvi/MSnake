package com.mar;

import com.mar.snake.api.GraphicEngine;
import com.mar.snake.core.GameEngine;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class JavaFxGameGraphicImpl extends Canvas implements GraphicEngine {
   private GraphicsContext gc;
   private int BLOCK_SIZE_W;
   private int BLOCK_SIZE_H;
   private float PAUSE_TEXT_WIDTH;
   private float GAMEOVER_TEXT_WIDTH;
   private final GameEngine gEngine;

   public JavaFxGameGraphicImpl(GameEngine gEngine) {
      super(330, 400);
      this.gEngine = gEngine;
   }

   public void resetGraphics() {
      BLOCK_SIZE_W = (int) ((float) this.getWidth()
            / (float) this.gEngine.getGroundWidth());
      BLOCK_SIZE_H = (int) ((float) this.getHeight()
            / (float) this.gEngine.getGroundHeight());
      gc = super.getGraphicsContext2D();
      final Text text1 = new Text(PAUSE_TEXT);
      final Text text2 = new Text(GAME_OVER_TEXT);
      PAUSE_TEXT_WIDTH = (float) text1.getLayoutBounds().getWidth();
      GAMEOVER_TEXT_WIDTH = (float) text2.getLayoutBounds().getWidth();
   }

   @Override
   public void drawSnakeHeadPiece(final int x, int y) {
      gc.setFill(Color.BLUE);
      gc.fillOval(x * BLOCK_SIZE_W,
            this.getHeight() - ++y * BLOCK_SIZE_H, BLOCK_SIZE_W,
            BLOCK_SIZE_H);
      gc.setFill(Color.BLACK);
      gc.strokeOval(x * BLOCK_SIZE_W,
            this.getHeight() - y * BLOCK_SIZE_H, BLOCK_SIZE_W,
            BLOCK_SIZE_H);
      // graphics.drawImage(background, x * BLOCK_SIZE_W, this.getHeight() -
      // ++y * BLOCK_SIZE_H, BLOCK_SIZE_W, BLOCK_SIZE_H, null);
   }

   @Override
   public void drawSnakePiece(final int x, int y) {
      gc.setFill(Color.DARKGRAY);
      // graphics.fillRect(x * BLOCK_SIZE_W, this.getHeight() - ++y *
      // BLOCK_SIZE_H, BLOCK_SIZE_W, BLOCK_SIZE_H);
      gc.fillOval(x * BLOCK_SIZE_W,
            this.getHeight() - ++y * BLOCK_SIZE_H, BLOCK_SIZE_W,
            BLOCK_SIZE_H);
      gc.setFill(Color.BLACK);
      gc.strokeOval(x * BLOCK_SIZE_W,
            this.getHeight() - y * BLOCK_SIZE_H, BLOCK_SIZE_W,
            BLOCK_SIZE_H);
   }

   @Override
   public void drawLevelPiece(final int x, int y) {
      gc.setFill(Color.BLACK);
      gc.fillRect(x * BLOCK_SIZE_W,
            this.getHeight() - ++y * BLOCK_SIZE_H, BLOCK_SIZE_W,
            BLOCK_SIZE_H);
      gc.setFill(Color.WHITE);
      gc.strokeRect(x * BLOCK_SIZE_W,
            this.getHeight() - y * BLOCK_SIZE_H, BLOCK_SIZE_W,
            BLOCK_SIZE_H);
   }

   @Override
   public void drawFoodPiece(final int x, int y) {
      gc.setFill(Color.RED);
      gc.fillRect(x * BLOCK_SIZE_W,
            this.getHeight() - ++y * BLOCK_SIZE_H, BLOCK_SIZE_W,
            BLOCK_SIZE_H);
   }

   public void drawDigestingFoodPiece(final int x, int y) {
      final int MAXIMAZE = 2;
      gc.setFill(Color.BLACK);
      gc.fillOval(x * BLOCK_SIZE_W - MAXIMAZE,
            this.getHeight() - MAXIMAZE - ++y * BLOCK_SIZE_H,
            MAXIMAZE * 2 + BLOCK_SIZE_W, MAXIMAZE * 2 + BLOCK_SIZE_H);
      gc.setFill(Color.RED);
      gc.strokeOval(x * BLOCK_SIZE_W - MAXIMAZE,
            this.getHeight() - MAXIMAZE - y * BLOCK_SIZE_H,
            MAXIMAZE * 2 + BLOCK_SIZE_W, MAXIMAZE * 2 + BLOCK_SIZE_H);
   }

   @Override
   public void drawPause() {
      // gc.setFont(textFont);
      gc.setFill(Color.MAGENTA);
      gc.strokeText(PAUSE_TEXT,
            (this.getWidth() / 2) - (PAUSE_TEXT_WIDTH / 2),
            this.getHeight() / 2);
   }

   @Override
   public void drawGameOver() {
      // gc.setFont(textFont);
      gc.setFill(Color.MAGENTA);
      gc.strokeText(GAME_OVER_TEXT,
            (this.getWidth() / 2) - (GAMEOVER_TEXT_WIDTH / 2),
            this.getHeight() / 2);
   }

   @Override
   public void redrawAll() {
      Platform.runLater(new Runnable() {
         @Override
         public void run() {
            gc.clearRect(0, 0, JavaFxGameGraphicImpl.this.getWidth(),
                  JavaFxGameGraphicImpl.this.getHeight());
            gEngine.redraw();
         }
      });
   }
}