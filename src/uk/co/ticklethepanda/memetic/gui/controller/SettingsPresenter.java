package uk.co.ticklethepanda.memetic.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import uk.co.ticklethepanda.memetic.algorithms.EvoAlgorithmSettings;
import uk.co.ticklethepanda.memetic.gui.helpers.SimulationPresenterBuilder;
import uk.co.ticklethepanda.memetic.gui.view.SettingsDialog;

/**
 * Class that controls what information that the SettingsView shows.
 */
public class SettingsPresenter {

  /**
   * The default number of generations before duplicates are removed.
   */
  private static final int GENERATIONS_BEFORE_DUPLICATES_REMOVED = 100;

  /**
   * The settings dialog that is displayed.
   */
  private final SettingsDialog settingsDialog;

  /**
   * Creates a new SettingsPresenter, creating its own SettingsDialog.
   */
  public SettingsPresenter() {
    settingsDialog = new SettingsDialog();
    settingsDialog.setPoolSize(EvoAlgorithmSettings.DEFAULT_SETTINGS.getPoolSize());
    settingsDialog.setEliteSize(EvoAlgorithmSettings.DEFAULT_SETTINGS.getElitismSize());
    settingsDialog.setMutationRate(EvoAlgorithmSettings.DEFAULT_SETTINGS.getMutationRate());
    settingsDialog.setCrossoverSize(EvoAlgorithmSettings.DEFAULT_SETTINGS.getCrossoverSize());

    settingsDialog.addAdditionalSettingsDefaultListener((final ActionEvent event) -> {
      settingsDialog.setPoolSize(EvoAlgorithmSettings.DEFAULT_SETTINGS.getPoolSize());
      settingsDialog.setEliteSize(EvoAlgorithmSettings.DEFAULT_SETTINGS.getElitismSize());
      settingsDialog.setMutationRate(EvoAlgorithmSettings.DEFAULT_SETTINGS.getMutationRate());
      settingsDialog.setCrossoverSize(EvoAlgorithmSettings.DEFAULT_SETTINGS.getCrossoverSize());
    });

    settingsDialog.addAlgorithmChangeListener((final ActionEvent event) -> {
      settingsDialog.setProblemTypes(settingsDialog.getAlgorithmType().getAvailableProblems());
    });
    settingsDialog.setProblemTypes(settingsDialog.getAlgorithmType().getAvailableProblems());
  }

  /**
   * Adds a listener to the dialog that listens to when the new simulation action has been pressed.
   *
   * @param actionListener
   *          the action listener to add
   */
  public void addNewSimulationActionListener(final ActionListener actionListener) {
    settingsDialog.addStartListener(actionListener);
  }

  /**
   * Gets the simulation settings currently being displayed by the dialog.
   *
   * @return the simulation settings currently being displayed by the dialog
   */
  public SimulationPresenterBuilder getSimulationSettings() {
    final SimulationPresenterBuilder.Builder builder = new SimulationPresenterBuilder.Builder();
    builder.setAlgorithmType(settingsDialog.getAlgorithmType())
        .setProblemSize(settingsDialog.getProblemSize())
        .setProblemType(settingsDialog.getProblemType())
        .setEvoAlgorithmSettings(new EvoAlgorithmSettings(settingsDialog.getPoolSize(),
            settingsDialog.getElitistSize(), settingsDialog.getCrossoverSize(),
            settingsDialog.getMutationRate(), GENERATIONS_BEFORE_DUPLICATES_REMOVED));
    if (settingsDialog.hasProblemSeed()) {
      builder.setProblemSeed(settingsDialog.getProblemSeed());
    }
    return builder.build();
  }

  /**
   * Sets the settings to be displayed by the dialog.
   *
   * @param settings
   *          the settings to be displayed by the dialogs
   */
  public void setSettings(final SimulationPresenterBuilder settings) {
    settingsDialog.setPoolSize(settings.getEvoAlgorithmSettings().getPoolSize());
    settingsDialog.setEliteSize(settings.getEvoAlgorithmSettings().getElitismSize());
    settingsDialog.setMutationRate(settings.getEvoAlgorithmSettings().getMutationRate());
    settingsDialog.setCrossoverSize(settings.getEvoAlgorithmSettings().getCrossoverSize());
    settingsDialog.setUseSeed(settings.useProblemSeed());
    settingsDialog.setProblemSeed(settings.getProblemSeed());
    settingsDialog.setAlgorithmType(settings.getAlgorithmType());
    settingsDialog.setProblemTypes(settings.getAlgorithmType().getAvailableProblems());
    settingsDialog.setProblemType(settings.getProblemType());
  }

  /**
   * Sets the visibility of the GUI.
   *
   * @param whether
   *          the GUI is visible
   */
  public void setVisible(final boolean bool) {
    SwingUtilities.getRoot(settingsDialog).setVisible(bool);

  }
}
