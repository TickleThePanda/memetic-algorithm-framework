package uk.co.ticklethepanda.memetic.gui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import uk.co.ticklethepanda.memetic.gui.ProblemType;
import uk.co.ticklethepanda.memetic.gui.helpers.AlgorithmType;

import com.google.common.primitives.Longs;

public class SettingsDialog extends JDialog {

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private JComboBox<AlgorithmType> algorithmTypeCombo;
  private JLabel label;
  private JLabel label_1;
  private JLabel label_2;
  private JLabel lblStepLength;
  private JPanel panel;
  private JPanel panel_1;
  private JSpinner problemSizeSpinner;
  private JComboBox<ProblemType> problemTypeCombo;
  private JButton settingsButton;
  private JButton startAlgorithmButton;
  private JTextField seedTxt;
  private JCheckBox chckbxUseSeed;
  private AdditionalSettingsDialog additionalSettings;

  /**
   * Create the panel.
   */
  public SettingsDialog() {
    initialize();
  }

  public void addAdditionalSettingsDefaultListener(ActionListener al) {
    this.additionalSettings.addDefaultSettingsListener(al);
  }

  public void addAlgorithmChangeListener(final ActionListener listener) {
    algorithmTypeCombo.addActionListener(listener);
  }

  public void addStartListener(final ActionListener startListener) {
    startAlgorithmButton.addActionListener(startListener);
  }

  public AlgorithmType getAlgorithmType() {
    return algorithmTypeCombo.getItemAt(algorithmTypeCombo.getSelectedIndex());
  }

  public int getCrossoverSize() {
    return additionalSettings.getCrossoverSize();
  }

  public int getElitistSize() {
    return additionalSettings.getEliteSize();
  }

  public float getMutationRate() {
    return additionalSettings.getMutRate();
  }

  public int getPoolSize() {
    return additionalSettings.getPopSize();
  }

  public long getProblemSeed() {
    Long problemSeed = Longs.tryParse(seedTxt.getText());
    if (problemSeed == null)
      return 0;
    else
      return problemSeed;
  }

  public int getProblemSize() {
    return (int) problemSizeSpinner.getValue();
  }

  public ProblemType getProblemType() {
    return problemTypeCombo.getItemAt(problemTypeCombo.getSelectedIndex());
  }

  public boolean hasProblemSeed() {
    return chckbxUseSeed.isSelected();
  }

  private void initialize() {
    setResizable(false);
    setLocation(new Point(50, 50));
    getContentPane().setLayout(new BorderLayout(0, 0));

    panel = new JPanel();
    getContentPane().add(panel, BorderLayout.CENTER);
    panel.setLayout(new BorderLayout(0, 0));

    panel_1 = new JPanel();
    panel.add(panel_1, BorderLayout.NORTH);
    final GridBagLayout gbl_panel_1 = new GridBagLayout();
    gbl_panel_1.columnWidths = new int[] { 29, 0, 0, 0, 0, 0 };
    gbl_panel_1.rowHeights =
        new int[] { 29, 29, 29, 29, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
    gbl_panel_1.columnWeights =
        new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
    gbl_panel_1.rowWeights =
        new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
    panel_1.setLayout(gbl_panel_1);

    label = new JLabel("Algorithm Type:");
    final GridBagConstraints gbc_label = new GridBagConstraints();
    gbc_label.anchor = GridBagConstraints.EAST;
    gbc_label.insets = new Insets(0, 0, 5, 5);
    gbc_label.gridx = 1;
    gbc_label.gridy = 1;
    panel_1.add(label, gbc_label);

    algorithmTypeCombo = new JComboBox<AlgorithmType>();
    algorithmTypeCombo.setModel(new DefaultComboBoxModel<AlgorithmType>(
        AlgorithmType.values()));
    final GridBagConstraints gbc_algorithmTypeCombo = new GridBagConstraints();
    gbc_algorithmTypeCombo.fill = GridBagConstraints.HORIZONTAL;
    gbc_algorithmTypeCombo.insets = new Insets(0, 0, 5, 5);
    gbc_algorithmTypeCombo.gridx = 2;
    gbc_algorithmTypeCombo.gridy = 1;
    panel_1.add(algorithmTypeCombo, gbc_algorithmTypeCombo);

    settingsButton = new JButton("Settings");
    settingsButton.addActionListener(e -> {
      additionalSettings.setVisible(true);
    });
    final GridBagConstraints gbc_settingsButton = new GridBagConstraints();
    gbc_settingsButton.anchor = GridBagConstraints.EAST;
    gbc_settingsButton.insets = new Insets(0, 0, 5, 5);
    gbc_settingsButton.gridx = 3;
    gbc_settingsButton.gridy = 1;
    panel_1.add(settingsButton, gbc_settingsButton);

    label_1 = new JLabel("Problem Type:");
    label_1.setHorizontalAlignment(SwingConstants.RIGHT);
    final GridBagConstraints gbc_label_1 = new GridBagConstraints();
    gbc_label_1.anchor = GridBagConstraints.EAST;
    gbc_label_1.insets = new Insets(0, 0, 5, 5);
    gbc_label_1.gridx = 1;
    gbc_label_1.gridy = 2;
    panel_1.add(label_1, gbc_label_1);

    problemTypeCombo = new JComboBox<ProblemType>();
    final GridBagConstraints gbc_problemTypeCombo = new GridBagConstraints();
    gbc_problemTypeCombo.fill = GridBagConstraints.HORIZONTAL;
    gbc_problemTypeCombo.insets = new Insets(0, 0, 5, 5);
    gbc_problemTypeCombo.gridx = 2;
    gbc_problemTypeCombo.gridy = 2;
    panel_1.add(problemTypeCombo, gbc_problemTypeCombo);

    label_2 = new JLabel("Problem Size:");
    final GridBagConstraints gbc_label_2 = new GridBagConstraints();
    gbc_label_2.anchor = GridBagConstraints.EAST;
    gbc_label_2.insets = new Insets(0, 0, 5, 5);
    gbc_label_2.gridx = 1;
    gbc_label_2.gridy = 3;
    panel_1.add(label_2, gbc_label_2);

    problemSizeSpinner = new JSpinner();
    problemSizeSpinner.setModel(new SpinnerNumberModel(100, 1, 100000, 100));
    final GridBagConstraints gbc_problemSizeSpinner = new GridBagConstraints();
    gbc_problemSizeSpinner.fill = GridBagConstraints.HORIZONTAL;
    gbc_problemSizeSpinner.insets = new Insets(0, 0, 5, 5);
    gbc_problemSizeSpinner.gridx = 2;
    gbc_problemSizeSpinner.gridy = 3;
    panel_1.add(problemSizeSpinner, gbc_problemSizeSpinner);

    lblStepLength = new JLabel("Generate Problem Seed:");
    final GridBagConstraints gbc_lblStepLength = new GridBagConstraints();
    gbc_lblStepLength.anchor = GridBagConstraints.EAST;
    gbc_lblStepLength.insets = new Insets(0, 0, 5, 5);
    gbc_lblStepLength.gridx = 1;
    gbc_lblStepLength.gridy = 4;
    panel_1.add(lblStepLength, gbc_lblStepLength);
    // -----------------------------
    seedTxt = new JTextField();
    seedTxt.setEnabled(false);
    seedTxt.setBackground(Color.LIGHT_GRAY);
    GridBagConstraints gbc_textField = new GridBagConstraints();
    gbc_textField.insets = new Insets(0, 0, 5, 5);
    gbc_textField.fill = GridBagConstraints.HORIZONTAL;
    gbc_textField.gridx = 2;
    gbc_textField.gridy = 4;
    panel_1.add(seedTxt, gbc_textField);
    seedTxt.setColumns(10);
    // -----------------------------
    chckbxUseSeed = new JCheckBox("use seed?");
    chckbxUseSeed.setSelected(false);
    chckbxUseSeed.addActionListener(e -> {
      seedTxt.setEnabled(chckbxUseSeed.isSelected());
      if (chckbxUseSeed.isSelected()) {
        seedTxt.setBackground(Color.WHITE);
      } else {
        seedTxt.setBackground(Color.LIGHT_GRAY);
      }
    });
    GridBagConstraints gbc_chckbxUseSeed = new GridBagConstraints();
    gbc_chckbxUseSeed.insets = new Insets(0, 0, 5, 5);
    gbc_chckbxUseSeed.gridx = 3;
    gbc_chckbxUseSeed.gridy = 4;
    panel_1.add(chckbxUseSeed, gbc_chckbxUseSeed);

    startAlgorithmButton = new JButton("Start Algorithm");
    final GridBagConstraints gbc_startAlgorithmButton =
        new GridBagConstraints();
    gbc_startAlgorithmButton.fill = GridBagConstraints.VERTICAL;
    gbc_startAlgorithmButton.anchor = GridBagConstraints.EAST;
    gbc_startAlgorithmButton.insets = new Insets(0, 0, 5, 5);
    gbc_startAlgorithmButton.gridx = 2;
    gbc_startAlgorithmButton.gridy = 13;
    panel_1.add(startAlgorithmButton, gbc_startAlgorithmButton);

    this.additionalSettings = new AdditionalSettingsDialog(this);

    this.setModalityType(ModalityType.APPLICATION_MODAL);

    this.pack();
  }

  public void setAlgorithmType(AlgorithmType algorithmType) {
    algorithmTypeCombo.setSelectedItem(algorithmType);

  }

  public void setCrossoverSize(int crossoverSize) {
    additionalSettings.setCrossoverSize(crossoverSize);

  }

  public void setEliteSize(int eliteSize) {
    additionalSettings.setEliteSize(eliteSize);
  }

  public void setMutationRate(float mutRate) {
    additionalSettings.setMutRate(mutRate);
    ;
  }

  public void setPoolSize(int popSize) {
    additionalSettings.setPopSize(popSize);
  }

  public void setProblemSeed(long problemSeed) {
    seedTxt.setText(String.valueOf(problemSeed));
  }

  public void setProblemType(ProblemType problemType) {
    problemTypeCombo.setSelectedItem(problemType);

  }

  public void setProblemTypes(final ProblemType[] problemType) {
    problemTypeCombo
        .setModel(new DefaultComboBoxModel<ProblemType>(problemType));
  }

  public void setUseSeed(boolean useProblemSeed) {
    chckbxUseSeed.setSelected(useProblemSeed);
  }
}
