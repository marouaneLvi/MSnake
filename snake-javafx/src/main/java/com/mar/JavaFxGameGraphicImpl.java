package com.mar;

import com.mar.snake.api.GraphicEngine;
import com.mar.snake.core.GameEngine;
import com.mar.snake.core.Statistics;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class JavaFxGameGraphicImpl extends Canvas implements GraphicEngine {
   private GraphicsContext gc;
   /**
    * A top section is reserved for statistics.
    */
   private static final int Y_OFFSET = 40;
   private double blockSizeW;
   private double blockSizeH;
   private double pauseTextWidth;
   private double gameoverTextWidth;
   private final GameEngine gEngine;

   public JavaFxGameGraphicImpl(GameEngine gEngine) {
      super(280, 360);   // Does not matter as the window can be resized
      this.gEngine = gEngine;
   }

   public void resetGraphics() {
      final Text text1 = new Text(PAUSE_TEXT);
      final Text text2 = new Text(GAME_OVER_TEXT);
      text1.setFont(Font.font(30));
      text2.setFont(Font.font(30));
      pauseTextWidth = text1.getLayoutBounds().getWidth();
      gameoverTextWidth = text2.getLayoutBounds().getWidth();
      blockSizeW = this.getWidth()
            / this.gEngine.getGroundWidth();
      blockSizeH = (this.getHeight() - Y_OFFSET)
            / this.gEngine.getGroundHeight();
      gc = super.getGraphicsContext2D();
   }

   @Override
   public void drawSnakeHeadPiece(final int x, final int y) {
      gc.setFill(Color.RED);
      gc.fillOval(x * blockSizeW,
            getPieceYPosition(y), blockSizeW,
            blockSizeH);
      gc.setStroke(Color.BLACK);
      gc.strokeOval(x * blockSizeW,
            getPieceYPosition(y), blockSizeW,
            blockSizeH);
   }

   @Override
   public void drawSnakePiece(final int x, final int y) {
      gc.setFill(Color.BROWN);
      gc.fillOval(x * blockSizeW,
            getPieceYPosition(y), blockSizeW,
            blockSizeH);
      gc.setStroke(Color.BLACK);
      gc.strokeOval(x * blockSizeW,
            getPieceYPosition(y), blockSizeW,
            blockSizeH);
   }

   @Override
   public void drawLevelPiece(final int x, final int y) {
      gc.setFill(Color.BLACK);
      gc.fillRect(x * blockSizeW, getPieceYPosition(y),
            blockSizeW, blockSizeH);
      gc.setStroke(Color.WHITE);
      gc.strokeRect(x * blockSizeW,
            getPieceYPosition(y),
            blockSizeW, blockSizeH);
   }

   @Override
   public void drawFoodPiece(final int x, final int y) {
      gc.setFill(Color.GREEN);
      gc.fillRoundRect(x * blockSizeW,
            getPieceYPosition(y), blockSizeW,
            blockSizeH, 10, 10);
   }

   public void drawDigestingFoodPiece(final int x, final int y) {
      final int MAXIMIZE = 2;
      gc.setFill(Color.BROWN);
      gc.setStroke(Color.WHITESMOKE);
      final double realX = x * blockSizeW - MAXIMIZE;
      final double realY = getPieceYPosition(y) - MAXIMIZE;
      final double w = MAXIMIZE * 2 + blockSizeW;
      final double h = MAXIMIZE * 2 + blockSizeH;
      gc.fillOval(realX, realY, w, h);
      gc.strokeOval(realX, realY, w, h);
   }

   @Override
   public void drawPause() {
      gc.setStroke(Color.WHITE);
      gc.setFill(Color.BLACK);
      gc.setFont(Font.font(30));
      final double x = (this.getWidth() / 2) - (pauseTextWidth / 2);
      final double y = this.getEffectiveHeight() / 2 + Y_OFFSET;
      gc.fillText(PAUSE_TEXT, x, y);
      gc.strokeText(PAUSE_TEXT, x, y);
   }

   @Override
   public void drawGameOver() {
      gc.setStroke(Color.WHITE);
      gc.setFill(Color.BLACK);
      gc.setFont(Font.font(30));
      final double x = (this.getWidth() / 2) - (gameoverTextWidth / 2);
      final double y = this.getEffectiveHeight() / 2 + Y_OFFSET;
      gc.fillText(GAME_OVER_TEXT, x, y);
      gc.strokeText(GAME_OVER_TEXT, x, y);
   }

   @Override
   public void drawStatistics(Statistics statistics) {
      gc.setStroke(Color.BLACK);
      gc.setFill(Color.BLACK);
      gc.strokeRect(0, 0, this.getWidth(), Y_OFFSET);
      gc.setFont(Font.font(11));
      final String scoreText = String.format(SCORE, statistics.getScore());
      final String elapsedTimeText = String.format(ELAPSED_TIME, statistics.prettyElapsedTime());
      gc.strokeText(scoreText, 20, 15);
      gc.strokeText(elapsedTimeText, 20, 30);
      gc.fillText(scoreText, 20, 15);
      gc.fillText(elapsedTimeText, 20, 30);
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

   @Override
   public boolean isResizable() {
      return true;
   }
   
   private double getPieceYPosition(final int y) {
      final double realY = this.gEngine.getGroundHeight() - y - 1;
      return realY * blockSizeH + Y_OFFSET;
   }


   private double getEffectiveHeight() {
      return this.getHeight() - Y_OFFSET;
   }
}