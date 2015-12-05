package uk.co.ticklethepanda.memetic;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;

import uk.co.ticklethepanda.memetic.gui.controller.GuiController;

/**
 * The driver for the main GUI.
 */
public class Driver extends JApplet {

  /**
   * Launches the main GUI.
   * 
   * @param args
   *          There are no parameters expected.
   * @throws InterruptedException 
   * @throws InvocationTargetException 
   */
  public static void main(final String[] args) throws InvocationTargetException, InterruptedException {

    final GuiController controller = new GuiController();
    controller.start();
  }

  /**
   * Serial Version UID.
   */
  private static final long serialVersionUID = 1L;
}
