package uk.co.ticklethepanda.memetic.gui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

public class GUIView extends JPanel {
  /**
   *
   */
  private static final long serialVersionUID = -2789429155485125767L;
  private JMenuBar menuBar;
  private JMenu mn;
  private JMenuItem menuFile;
  private JTabbedPane tabbedPane;
  private JMenuItem mntmClose;
  private JMenuItem mntmExit;
  private JSeparator separator_2;

  ArrayList<ActionListener> tabCloseButtonListener = new ArrayList<ActionListener>();

  protected SimulationView<?> viewClosed;

  public GUIView() {
    initialize();
  }

  public void addNewActionListener(final ActionListener actionListener) {
    menuFile.addActionListener(actionListener);
  }

  public void addNewSimulationView(final SimulationView<?> component) {
    tabbedPane.addTab("new*", component);
    tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(component));
    final ButtonTabComponent btc = new ButtonTabComponent(tabbedPane);
    btc.addCloseButtonListener(e -> {
      GUIView.this.viewClosed = component;
      for (final ActionListener al : tabCloseButtonListener) {
        al.actionPerformed(e);
      }
    });
    tabbedPane.setTabComponentAt(tabbedPane.getSelectedIndex(), btc);
  }

  public void addTabClosedActionListener(final ActionListener actionListener) {
    tabCloseButtonListener.add(actionListener);
  }

  public void closeTab(final SimulationView<?> view) {
    final int i = tabbedPane.indexOfComponent(view);
    if (i != -1) {
      tabbedPane.remove(i);
    }
  }

  public SimulationView<?> getClosedView() {
    return viewClosed;
  }

  public SimulationView<?> getCurrentlySelectedTab() {
    return (SimulationView<?>) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
  }

  private void initialize() {
    setLayout(new BorderLayout(0, 0));

    menuBar = new JMenuBar();
    add(menuBar, BorderLayout.NORTH);

    mn = new JMenu("File");
    menuBar.add(mn);
    // -----------------------------
    menuFile = new JMenuItem("New");
    menuFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
    mn.add(menuFile);
    // -----------------------------
    mntmClose = new JMenuItem("Close Tab");
    mntmClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
    mn.add(mntmClose);
    // -----------------------------
    separator_2 = new JSeparator();
    mn.add(separator_2);
    // -----------------------------
    mntmExit = new JMenuItem("Exit");
    mn.add(mntmExit);
    mntmClose.addActionListener(e -> {
      tabbedPane.remove(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()));
    });
    mntmExit.addActionListener(e -> {
      System.exit(0);
    });
    tabbedPane = new JTabbedPane(SwingConstants.TOP);
    tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    tabbedPane.setPreferredSize(new Dimension(800, 500));
    add(tabbedPane, BorderLayout.CENTER);
  }
}
