import static java.awt.event.KeyEvent.*;

/**
 * <xmp> <code> <pre>
 * 
 * for GameWindow project .. 2017
 * 
 * four players:
 * each player has 1 keys 
 * 
 * player 1 - Z
 * player 2 - F
 * player 3 - U
 * player 4 - P
 *  
 * </pre> </code> </xmp>
 */

public class KeyHandler {

  private int numPlayers = 4;

  private Control button[] = new Control[4];
  private GameWindow gw;

  /**
   * Default constructor.
   */
  public KeyHandler() {
    new KeyHandler(null);
  }

  /**
   * Constructor has a parameter so KeyHandler has a reference to the MultiGame
   * Object.
   */
  public KeyHandler(GameWindow mg) {
    this.gw = mg;
    init();
  }

  private void setVal(Control ctl[], int index, boolean press) {
    if (press) { // press
      ctl[index].pressed = true;
      if (ctl[index].clickReady) {
        ctl[index].clickReady = false;
        ctl[index].clicked = true;
      }
    } else { // release
      ctl[index].clickReady = true;
      ctl[index].pressed = false;
    }
  }

  private void setVal(Control ctl, boolean press) {
    if (press) { // press
      ctl.pressed = true;
      if (ctl.clickReady) {
        ctl.clickReady = false;
        ctl.clicked = true;
      }
    } else { // release
      ctl.clickReady = true;
      ctl.pressed = false;
    }
  }

  void init() {
    for (int i = 0; i < 4; i++) {
      button[i] = new Control();
    }
  }

  /**
   * <pre>
   * Sample usage:
   * if keyChar is a digit.. it might be from the bar code reader..
   * shift the digits in String idNum to the left and place keyChar as the last digit
   * 
   * 
   * </pre>
   */
  void handleKey(int keyCode, char keyChar, boolean press) {

    switch (keyCode) {

    // PLAYER_1
    case VK_Z:
      setVal(button, 0, press);
      break;
    // PLAYER_2
    case VK_F:
      setVal(button, 1, press);
      break;
    // PLAYER_3
    case VK_U:
      setVal(button, 2, press);
      break;
    // PLAYER_4
    case VK_P:
      setVal(button, 3, press);
      break;

    default:
    }

  }

  void clearAllClicks() {
    for (int i = 0; i < 4; i++) {
      button[i] = new Control();
    }
  }

  /**
   * returns the status of button of player playerNum
   */
  public boolean isButtonPressed(int playerNum) {
    return button[playerNum - 1].pressed;
  }

  /**
   * returns true if button of player playerNum has just been pushed
   */
  public boolean wasButtonJustPressed(int playerNum) {
    if (button[playerNum - 1].clicked) {
      button[playerNum - 1].clicked = false;
      return true;
    } else {
      return false;
    }
  }

  // inner class
  class Control {
    boolean pressed;
    boolean clicked;
    boolean clickReady;

    public Control() {
      pressed = false;
      clicked = false;
      clickReady = true;
    }
  }

}// end class KeyHandler