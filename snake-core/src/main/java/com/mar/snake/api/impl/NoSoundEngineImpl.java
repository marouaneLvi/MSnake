package com.mar.snake.api.impl;

import com.mar.snake.api.SoundEngine;

/**
 * Dummy Sound Engine with no effects.
 * Used if sound needed.
 * @author Marouane
 */
public class NoSoundEngineImpl implements SoundEngine {

   @Override
   public void playSnakeDie() {}

   @Override
   public void playSnakeEatPiece() {}

   @Override
   public void playSoundTrack() {}

}
