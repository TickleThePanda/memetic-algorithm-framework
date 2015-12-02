package uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.controller;

import java.awt.BorderLayout;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.model.SimulationPresenterBuilder;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.view.GUIView;
import uk.ac.rhul.cs.thomas_attwood.genetic_algorithm.gui.view.SimulationView;

/**
 * Controls the whole GUI.
 */
public class GuiController {

  /**
   * Stores pairs of simulation settings and simulation views.
   */
  private class SettingsViewPair {
    /**
     * The settings for the associated view.
     */
    private final SimulationPresenterBuilder settings;

    /**
     * The view for the associated settings.
     */
    private final SimulationView<?> view;

    /**
     * Creates a new SettingsViewPair with the settings and the view.
     * 
     * @param settings
     *          the settings for the associated view
     * @param view
     *          the view for the associated settings
     */
    public SettingsViewPair(final SimulationPresenterBuilder settings,
        final SimulationView<?> view) {
      this.settings = settings;
      this.view = view;
    }

    /**
     * Gets the settings for the the view.
     * 
     * @return the settings for the view
     */
    public SimulationPresenterBuilder getSettings() {
      return settings;
    }

    /**
     * Gets the view for the settings.
     * 
     * @return the view for the settings
     */
    public SimulationView<?> getView() {
      return view;
    }
  }

  /**
   * Stores pairs of simulation settings and simulation files.
   */
  private class SimulationFilePair {
    /**
     * The simulation file that is associated with the view.
     */
    private final File file;

    /**
     * The simulation view which is associated with the file.
     */
    private final SimulationView<?> view;

    /**
     * Creates a new SimulationFilePair with the settings and the view.
     * 
     * @param file
     *          the file for the associated view
     * @param view
     *          the view for the associated file
     */
    public SimulationFilePair(File file, SimulationView<?> view) {
      this.file = file;
      this.view = view;
    }

    /**
     * Gets the file for the view.
     * 
     * @return the file for the view
     */
    public File getFile() {
      return file;
    }

    /**
     * Gets the view for the settings.
     * 
     * @return the view for the settings
     */
    public SimulationView<?> getView() {
      return view;
    }
  }

  /**
   * The settings presenter to generate new settings.
   */
  SettingsPresenter settingsPresenter;
  /**
   * The jframe that GUI is stored in.
   */
  JFrame jframe;
  /**
   * The GUIView that is being controlled.
   */
  GUIView guiView;
  /**
   * The list of SimulationPresenters that currently exist.
   */
  List<SimulationPresenter<?>> hs = new ArrayList<SimulationPresenter<?>>();
  /**
   * The list of associated settings and views.
   */
  List<SettingsViewPair> settingsViewPair = new ArrayList<SettingsViewPair>();
  /**
   * The list of associated simulations and files.
   */
  List<SimulationFilePair> simulationFilePair =
      new ArrayList<SimulationFilePair>();

  /**
   * A constructor that creates a new GuiController in a new JFrame.
   */
  public GuiController() {
    buildGui();

    guiView.addNewActionListener(e -> {
      settingsPresenter.setVisible(true);
    });
    
    guiView.addTabClosedActionListener(e -> {
      SimulationView<?> sv = guiView.getClosedView();
      for (int i = 0; i < hs.size(); i++) {
        if (hs.get(i).getView() == sv) {
          hs.get(i).getModel().setRunning(false);
        }
      }

      guiView.closeTab(sv);
    });

    settingsPresenter
        .addNewSimulationActionListener(e -> {
          SimulationPresenterBuilder ss =
              settingsPresenter.getSimulationSettings();
          this.openNewSettings(ss);
        });
  }

  /**
   * Adds a simulation to the GUIView.
   * 
   * @param simulationPresenter
   *          the simulation to add
   */
  private void addSimulationViewToGui(SimulationPresenter<?> simulationPresenter) {
    try {
      SwingUtilities.invokeAndWait(() -> {
        settingsPresenter.setVisible(false);
        guiView.addNewSimulationView(simulationPresenter.getView());
        jframe.pack();
      });
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  /**
   * Adds a simulation to the lists required.
   * 
   * @param simulationSettings
   *          the settings required
   * @param simulationPresenter
   *          the simulation required
   */
  private void addViewToLists(SimulationPresenterBuilder simulationSettings,
      SimulationPresenter<?> simulationPresenter) {
    hs.add(simulationPresenter);
    settingsViewPair.add(new SettingsViewPair(simulationSettings,
        simulationPresenter.getView()));
  }

  /**
   * Builds the GUI, adding it to a JFrame.
   */
  private void buildGui() {
    settingsPresenter = new SettingsPresenter();
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException
        | IllegalAccessException | UnsupportedLookAndFeelException e1) {
      e1.printStackTrace();
    }

    try {
      SwingUtilities.invokeAndWait(() -> guiView = new GUIView());
      jframe = new JFrame();
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jframe.getContentPane().setLayout(new BorderLayout(0, 0));
      jframe.getContentPane().add(guiView, BorderLayout.CENTER);
      jframe.setTitle("Algorithms");
      jframe.pack();
    } catch (InvocationTargetException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets the settings for the current tab from the list.
   * 
   * @return the settings for the current tab
   */
  private SimulationPresenterBuilder getCurrentTabsSettings() {
    SimulationView<?> simulationView = guiView.getCurrentlySelectedTab();

    SimulationPresenterBuilder settings = null;

    for (int i = 0; i < settingsViewPair.size(); i++) {
      if (settingsViewPair.get(i).getView() == simulationView) {
        settings = settingsViewPair.get(i).getSettings();
      }
    }
    return settings;
  }

  /**
   * Gets the last save location used for the current tab.
   * 
   * @return the last save location used for the current tab
   */
  private File getFromLastSaveLocation() {
    File file = null;
    SimulationView<?> simulationView = guiView.getCurrentlySelectedTab();
    for (SimulationFilePair pair : simulationFilePair) {
      if (pair.getView() == simulationView) {
        file = pair.getFile();
      }
    }
    return file;
  }

  /**
   * Opens settings window with the settings provided.
   * 
   * @param settings
   *          the settings that the window will be opened with
   */
  private void openNewSettings(SimulationPresenterBuilder settings) {
    Executors.newSingleThreadExecutor().execute(
        () -> {
          SimulationPresenter<?> simulationPresenter =
              settings.createSimulationPresenter();
          addSimulationViewToGui(simulationPresenter);

          if (simulationPresenter != null) {
            simulationPresenter.getModel().execute();
            addViewToLists(settings, simulationPresenter);
          }
        });
  }

  /**
   * Starts the GUI, setting it visible.
   */
  public void start() {
    SwingUtilities.getRoot(guiView).setVisible(true);
  }

  public JPanel getPanel() {
    return guiView;
  }

}
