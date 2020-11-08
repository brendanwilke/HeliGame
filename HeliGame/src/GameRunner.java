import java.io.*;
import java.net.*;

/**
 * main-class .. has main method ... used to launch the program instantiates a
 * GameWindow Object which runs the game and handles all the full-screen
 * animations and game timer
 */
public class GameRunner {
  /**
   * main .. args will be -Xms2048m
   * 
   * select Run Configurations from the Run menu, go to the Arguments tab<br>
   * type -Xms2048m in the Program Arguments window
   */
  public static void main(String args[]) {
    int fps = 150;// frames per second
    new GameWindow(fps);
  } // end of main()

}// end class GameRunner