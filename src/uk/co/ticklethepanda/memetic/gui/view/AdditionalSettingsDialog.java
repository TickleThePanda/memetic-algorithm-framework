package uk.co.ticklethepanda.memetic.gui.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.google.common.primitives.Ints;

class AdditionalSettingsDialog extends JDialog {

  private class PrevSettings {
    final int popSize;
    final float mutRate;
    private final int croSize;

    final int eliSize;

    PrevSettings(final int popSize, final float mutRate, final int eliSize, final int croSize) {
      this.popSize = popSize;
      this.mutRate = mutRate;
      this.eliSize = eliSize;
      this.croSize = croSize;
    }

    public int getCroSize() {
      return croSize;
    }

    public int getEliSize() {
      return eliSize;
    }

    public float getMutRate() {
      return mutRate;
    }

    public int getPopSize() {
      return popSize;
    }
  }

  /**
   *
   */
  private static final long serialVersionUID = 3828672204122707099L;

  private JLabel polLab;

  private JLabel mutLab;

  private JLabel eliLab;

  private JTextField polTxt;

  private JTextField mutTxt;

  private JTextField eliTxt;

  private JButton submit;
  private JButton defaultBut;
  private final Component setupPanel;
  PrevSettings ps;
  private JLabel lblCrossoverSize;
  private JTextField crsTxt;

  public AdditionalSettingsDialog(final JDialog setupPanel) {
    super(setupPanel);
    this.setupPanel = setupPanel;
    initialize();
  }

  public void addDefaultSettingsListener(final ActionListener a) {
    defaultBut.addActionListener(a);
  }

  public void addSubmitActionListener(final ActionListener a) {
    submit.addActionListener(a);
  }

  public int getCrossoverSize() {
    final Integer crsSize = Ints.tryParse(crsTxt.getText());
    if (crsSize == null) {
      return 0;
    } else {
      return crsSize;
    }
  }

  public int getEliteSize() {
    final Integer eliSize = Ints.tryParse(eliTxt.getText());
    if (eliSize == null) {
      return 0;
    } else {
      return eliSize;
    }
  }

  public float getMutRate() {
    final Integer mutSize = Ints.tryParse(mutTxt.getText());
    if (mutSize == null) {
      return 0f;
    } else {
      return mutSize;
    }
  }

  /**
   *
   */

  public int getPopSize() {
    final Integer popSize = Ints.tryParse(polTxt.getText());
    if (popSize == null) {
      return 0;
    } else {
      return popSize;
    }
  }

  private void initialize() {
    setResizable(false);
    setLocationRelativeTo(setupPanel);
    final GridBagLayout gbl_panel_1 = new GridBagLayout();
    gbl_panel_1.columnWidths = new int[] { 29, 0, 200, 0 };
    gbl_panel_1.rowHeights = new int[] { 29, 29, 29, 29, 29, 0, 0 };
    gbl_panel_1.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0 };
    gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0 };
    this.getContentPane().setLayout(gbl_panel_1);

    polLab = new JLabel("Population Size");
    {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.anchor = GridBagConstraints.EAST;
      gbc.insets = new Insets(0, 0, 5, 5);
      gbc.gridx = 1;
      gbc.gridy = 1;
      this.getContentPane().add(polLab, gbc);
    }
    mutLab = new JLabel("Mutation Rate");
    {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.anchor = GridBagConstraints.EAST;
      gbc.insets = new Insets(0, 0, 5, 5);
      gbc.gridx = 1;
      gbc.gridy = 2;
      this.getContentPane().add(mutLab, gbc);
    }
    eliLab = new JLabel("Elite Size");
    {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.anchor = GridBagConstraints.EAST;
      gbc.insets = new Insets(0, 0, 5, 5);
      gbc.gridx = 1;
      gbc.gridy = 3;
      this.getContentPane().add(eliLab, gbc);
    }

    polTxt = new JTextField();
    {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(0, 0, 5, 5);
      gbc.gridx = 2;
      gbc.gridy = 1;
      this.getContentPane().add(polTxt, gbc);
    }
    mutTxt = new JTextField();
    {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(0, 0, 5, 5);
      gbc.gridx = 2;
      gbc.gridy = 2;
      this.getContentPane().add(mutTxt, gbc);
    }
    eliTxt = new JTextField();
    {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(0, 0, 5, 5);
      gbc.gridx = 2;
      gbc.gridy = 3;
      this.getContentPane().add(eliTxt, gbc);
    }
    // -----------------------------
    lblCrossoverSize = new JLabel("Crossover Size");
    final GridBagConstraints gbc_lblCrossoverSize = new GridBagConstraints();
    gbc_lblCrossoverSize.anchor = GridBagConstraints.EAST;
    gbc_lblCrossoverSize.insets = new Insets(0, 0, 5, 5);
    gbc_lblCrossoverSize.gridx = 1;
    gbc_lblCrossoverSize.gridy = 4;
    getContentPane().add(lblCrossoverSize, gbc_lblCrossoverSize);
    // -----------------------------
    crsTxt = new JTextField();
    final GridBagConstraints gbc_crsTxt = new GridBagConstraints();
    gbc_crsTxt.insets = new Insets(0, 0, 5, 5);
    gbc_crsTxt.fill = GridBagConstraints.HORIZONTAL;
    gbc_crsTxt.gridx = 2;
    gbc_crsTxt.gridy = 4;
    getContentPane().add(crsTxt, gbc_crsTxt);
    crsTxt.setColumns(10);

    submit = new JButton("Submit");
    {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(0, 0, 5, 5);
      gbc.gridx = 2;
      gbc.gridy = 5;
      this.getContentPane().add(submit, gbc);
    }

    defaultBut = new JButton("Default");
    {
      final GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new Insets(0, 0, 5, 5);
      gbc.gridx = 1;
      gbc.gridy = 5;
      this.getContentPane().add(defaultBut, gbc);
    }

    this.addWindowListener(new WindowAdapter() {

      PrevSettings prevSettings = null;

      @Override
      public void windowClosing(final java.awt.event.WindowEvent windowEvent) {
        if (prevSettings != null) {
          setPopSize(prevSettings.getPopSize());
          setMutRate(prevSettings.getMutRate());
          setEliteSize(prevSettings.getEliSize());
          setCrossoverSize(prevSettings.getCroSize());
        }
        prevSettings =
            new PrevSettings(getPopSize(), getMutRate(), getEliteSize(), getCrossoverSize());
      }
    });

    submit.addActionListener(e -> AdditionalSettingsDialog.this.setVisible(false));

    this.setModalityType(ModalityType.APPLICATION_MODAL);
    this.pack();
  }

  public void setCrossoverSize(final int crsSize) {
    crsTxt.setText(Integer.toString(crsSize));
  }

  public void setEliteSize(final int eliSize) {
    eliTxt.setText(Integer.toString(eliSize));
  }

  public void setMutRate(final float mutRate) {
    mutTxt.setText(Float.toString(mutRate));
  }

  public void setPopSize(final int popSize) {
    polTxt.setText(Integer.toString(popSize));
  }
}