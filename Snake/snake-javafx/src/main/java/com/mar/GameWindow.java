package com.mar;

import com.mar.snake.core.GameEngine;
import com.mar.snake.api.impl.NoSoundEngineImpl;
import com.mar.snake.core.constant.Signal;
import javafx.scene.control.Button;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameWindow extends Application {

   public static void main(String[] args) {
      Application.launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {
      final Group root = new Group();
      Scene scene = new Scene(root, 800, 600, Color.BEIGE);
      final BorderPane borderpane = new BorderPane();
      borderpane.setPrefSize(640, 480);
      final Button btn = new Button("Start");
      final GameEngine gEngine = new GameEngine();
      final JavaFxGameGraphicImpl canvas = new JavaFxGameGraphicImpl(gEngine);
      canvas.autosize();

      borderpane.setTop(btn);
      borderpane.setCenter(canvas);
      root.getChildren().add(borderpane);

      btn.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent e) {
            btn.setDisable(true);
            btn.setVisible(false);
            borderpane.setFocusTraversable(true);
            borderpane.requestFocus();
            borderpane.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
}
