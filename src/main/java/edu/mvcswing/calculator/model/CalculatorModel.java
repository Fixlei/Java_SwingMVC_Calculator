package edu.calculatorSwingMVC.model;

/**
 * Model Layer: Pure business logic with zero UI dependencies.
 * <p>
 * Design Patterns Applied:
 * - Single Responsibility Principle (SRP): Only handles computation, never display.
 * - Open-Closed Principle (OCP): New operations require only a new method;
 * existing code remains untouched.
 */
public class CalculatorModel {

  public double add(double a, double b) {
    return a + b;
  }

  public double subtract(double a, double b) {
    return a - b;
  }

  public double multiply(double a, double b) {
    return a * b;
  }

  public double divide(double a, double b) {
    if (b == 0) {
      throw new ArithmeticException("Cannot divide by zero");
    }
    return a / b;
  }

  public double modulus(double a, double b) {
    if (b == 0) {
      throw new ArithmeticException("Cannot divide by zero");
    }
    return a % b;
  }

  public double power(double a, double b) {
    return Math.pow(a, b);
  }
}