package com.mar;

import com.mar.snake.core.GameEngine;
import com.mar.snake.api.impl.NoSoundEngineImpl;
import com.mar.snake.core.constant.Signal;
import javafx.scene.control.Button;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameWindow extends Application {

   public static void main(String[] args) {
      Application.launch(args);
   }
   
   @Override
   public void start(Stage primaryStage) throws Exception {
      final Button btn = new Button("Start");
      final GameEngine gEngine = new GameEngine();

      final CanvasPane canvasPane = new CanvasPane(gEngine, 340, 400);
      final JavaFxGameGraphicImpl canvas = canvasPane.getCanvas();
      BorderPane root = new BorderPane(canvasPane);
      root.setTop(btn);
      Scene scene = new Scene(root, 340, 400);
      btn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent e) {
            //btn.setDisable(true);
            //btn.setVisible(false);
            root.setFocusTraversable(true);
            root.requestFocus();
            root.setOnKeyPressed(new EventHandler<KeyEvent>() {
               public void handle(final KeyEvent keyEvent) {
                  switch (keyEvent.getCode()) {
                  case C:
                     gEngine.sendSignal(Signal.TURN_LEFT);
                     break;
                  case V:
                     gEngine.sendSignal(Signal.TURN_RIGHT);
                     break;
                  case LEFT:
                     gEngine.sendSignal(Signal.MOVE_LEFT);
                     break;
                  case UP:
                     gEngine.sendSignal(Signal.MOVE_UP);
                     break;
                  case RIGHT:
                     gEngine.sendSignal(Signal.MOVE_RIGHT);
                     break;
                  case DOWN:
                     gEngine.sendSignal(Signal.MOVE_DOWN);
                     break;
                  case ADD:
                     gEngine.sendSignal(Signal.SPEED_UP);
                     break;
                  case SUBTRACT:
                     gEngine.sendSignal(Signal.SPEED_DOWN);
                     break;
                  case ENTER:
                     if (gEngine.isGameOver()) {
                        gEngine.sendSignal(Signal.START_GAME);
                     } else {
                        gEngine.sendSignal(Signal.PAUSE_OR_UNPAUSE_GAME);
                     }
                     break;
                  default:
                  }
                  keyEvent.consume();
               }
            });

            gEngine.initializeEngine(canvas, new NoSoundEngineImpl());
            canvas.autosize();
            canvas.resetGraphics();
            gEngine.sendSignal(Signal.START_GAME);
            canvas.redrawAll();
         }
      });

      primaryStage.setScene(scene);
      primaryStage.show();
      primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

         @Override
         public void handle(WindowEvent t) {
            Platform.exit();
            gEngine.sendSignal(Signal.END_GAME);
         }

      });
   }

   private static class CanvasPane extends Pane {

      private final JavaFxGameGraphicImpl canvas;

      public CanvasPane(GameEngine gameEngine, double width, double height) {
          canvas = new JavaFxGameGraphicImpl(gameEngine);
          getChildren().add(canvas);
      }

      public JavaFxGameGraphicImpl getCanvas() {
          return canvas;
      }

      @Override
      protected void layoutChildren() {
          super.layoutChildren();
          final double x = snappedLeftInset();
          final double y = snappedTopInset();
          final double w = snapSize(getWidth()) - x - snappedRightInset();
          final double h = snapSize(getHeight()) - y - snappedBottomInset();
          canvas.setLayoutX(x);
          canvas.setLayoutY(y);
          canvas.setWidth(w);
          canvas.setHeight(h);
          canvas.resetGraphics();;
      }
  }
}
