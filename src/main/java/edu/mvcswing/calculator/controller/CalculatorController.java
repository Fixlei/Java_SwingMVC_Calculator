package edu.calculatorSwingMVC.controller;

import edu.calculatorSwingMVC.model.CalculatorModel;
import edu.calculatorSwingMVC.view.CalculatorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Controller Layer: Coordinates Model and View.
 *
 * Design Patterns Applied:
 * - Mediator Pattern:  Controller is the sole bridge between Model and View.
 * - Observer Pattern:  Controller subscribes to View events via listener registration.
 * - Strategy (concept): switch-based dispatch delegates to the appropriate Model method.
 *
 * MVC Decoupling Proof:
 * - Model has zero knowledge of View or Controller.
 * - View  has zero knowledge of Model or Controller.
 * - Controller holds references to both and translates between them.
 */
public class CalculatorController {
  private final CalculatorModel model;
  private final CalculatorView view;
  private final DecimalFormat df = new DecimalFormat("#.##########");

  public CalculatorController(CalculatorModel model, CalculatorView view) {
    this.model = model;
    this.view = view;

    // Register event listeners (Observer Pattern)
    this.view.addCalculateListener(new CalculateListener());
    this.view.addClearListener(e -> view.clearAll());
  }

  /**
   * Inner class listener: handles the calculate action.
   * Flow: Read from View → Invoke Model → Push result to View
   */
  class CalculateListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String input1 = view.getInput1();
      String input2 = view.getInput2();

      // Input validation
      if (input1.isEmpty() || input2.isEmpty()) {
        view.setError("Please enter both numbers");
        return;
      }

      try {
        double a = Double.parseDouble(input1);
        double b = Double.parseDouble(input2);
        String op = view.getSelectedOperator();

        double result = calculate(op, a, b);

        // Build formula string for display
        String formula = df.format(a) + " " + op + " " + df.format(b);
        view.setResult(formula, df.format(result));

      } catch (NumberFormatException ex) {
        view.setError("Invalid number format");
      } catch (ArithmeticException ex) {
        view.setError("Error: " + ex.getMessage());
      }
    }

    private double calculate(String op, double a, double b) {
      switch (op) {
        case "+":
          return model.add(a, b);
        case "−":
          return model.subtract(a, b);
        case "×":
          return model.multiply(a, b);
        case "÷":
          return model.divide(a, b);
        case "%":
          return model.modulus(a, b);
        case "^":
          return model.power(a, b);
        default:
          throw new IllegalArgumentException("Unknown operator: " + op);
      }
    }
  }
}