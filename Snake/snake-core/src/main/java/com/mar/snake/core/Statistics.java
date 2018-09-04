package com.mar.snake.core;

public class Statistics {
   /**
    * Each eaten piece for 30 points.
    */
   private static final long SCORE_STEP = 30;
   private final long speedInMs;
   private long score;
   private long elpasedTimeInGameStep;

   public Statistics(long speedInMs) {
      this.speedInMs = speedInMs;
   }
   public void scoreUp() {
      this.score += SCORE_STEP; 
   }

   public void upTime() {
      ++this.elpasedTimeInGameStep; 
   }

   public long getScore() {
      return this.score;
   }

   public long getElpasedTimeInSeconds() {
      return this.elpasedTimeInGameStep * this.speedInMs / 1000l;
   }

   public String prettyElapsedTime() {
      final long totalSecondsPassed = this.getElpasedTimeInSeconds();
      final long secondsPassed = totalSecondsPassed % 60;
      if (totalSecondsPassed >= 60) {
         final long minutesPassed = totalSecondsPassed / 60;
         return String.format("%02dmin : %02ds", minutesPassed, secondsPassed);
      }
      return String.format("%02ds", secondsPassed);
   }
}
