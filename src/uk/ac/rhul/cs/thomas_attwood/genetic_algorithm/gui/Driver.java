package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui;

import java.applet.Applet;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.controller.GuiController;

/**
 * The driver for the main GUI.
 */
public class Driver extends Applet {

  /**
   * Launches the main GUI.
   * 
   * @param args
   *          There are no parameters expected.
   */
  public static void main(final String[] args) {

    final GuiController controller = new GuiController();

    controller.start();
  }

  /**
   * Serial Version UID.
   */
  private static final long serialVersionUID = 1L;
}
