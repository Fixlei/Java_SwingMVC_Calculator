package edu.mvcswing.calculator;

import edu.mvcswing.calculator.controller.CalculatorController;
import edu.mvcswing.calculator.model.CalculatorModel;
import edu.mvcswing.calculator.view.CalculatorView;
import javax.swing.SwingUtilities;

public class Main {
  public static void main(String[] args) {
    // EDT (Event Dispatch Thread) is the dedicated UI thread in Swing.
    // All UI operations must run on this thread to avoid concurrency issues.
    SwingUtilities.invokeLater(() -> {
      CalculatorModel model = new CalculatorModel();
      CalculatorView view = new CalculatorView();
      new CalculatorController(model, view);
      view.setVisible(true);
    });
  }
}