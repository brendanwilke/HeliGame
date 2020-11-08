/**
 * 
 */


import java.awt.Graphics2D;

/**
 * All Game c
 */
public interface Game {
 
  /**
   * renders all graphics to g
   */
  public void render(Graphics2D g);

  /**
   * updates all instance variables
   */
  public void update();

}