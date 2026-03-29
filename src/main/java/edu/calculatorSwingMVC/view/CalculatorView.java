package edu.calculatorSwingMVC.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * View Layer: Responsible only for UI rendering and input collection.
 * <p>
 * Design Patterns Applied:
 * - Observer Pattern: View does not handle events directly.
 * It exposes addCalculateListener() / addClearListener() so the
 * Controller can subscribe — View never knows who is listening.
 * - MVC Decoupling: View has zero references to Controller or Model.
 */
public class CalculatorView extends JFrame {

  // ===== Theme Color Constants =====
  private static final Color BG_DARK = new Color(30, 30, 46);    // Main background
  private static final Color BG_PANEL = new Color(45, 45, 65);    // Result panel background
  private static final Color ACCENT = new Color(137, 180, 250); // Primary accent (blue)
  private static final Color ACCENT_HOVER = new Color(116, 160, 235); // Button hover state
  private static final Color TEXT_PRIMARY = new Color(205, 214, 244); // Primary text
  private static final Color TEXT_DIM = new Color(147, 153, 178); // Secondary / placeholder text
  private static final Color INPUT_BG = new Color(55, 55, 80);    // Input field background
  private static final Color SUCCESS = new Color(166, 227, 161); // Result success (green)
  private static final Color ERROR = new Color(243, 139, 168); // Error message (red)

  // ===== UI Components =====
  private JTextField field1;
  private JTextField field2;
  private JComboBox<String> operatorBox;
  private JButton calculateBtn;
  private JButton clearBtn;
  private JLabel resultLabel;
  private JLabel formulaLabel;

  // Supported operators displayed in the dropdown
  private static final String[] OPERATORS = {"+", "−", "×", "÷", "%", "^"};

  public CalculatorView() {
    initFrame();
    initComponents();
    layoutComponents();
  }

  // ----- Window Initialization -----
  private void initFrame() {
    setTitle("MVC Calculator");
    setSize(420, 340);
    setMinimumSize(new Dimension(380, 300));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    setResizable(false);
    getContentPane().setBackground(BG_DARK);
  }

  // ----- Component Creation -----
  private void initComponents() {
    field1 = createStyledTextField("Number A");
    field2 = createStyledTextField("Number B");

    // Operator dropdown selector
    operatorBox = new JComboBox<>(OPERATORS);
    operatorBox.setFont(new Font("SansSerif", Font.BOLD, 18));
    operatorBox.setPreferredSize(new Dimension(65, 40));
    operatorBox.setBackground(INPUT_BG);
    operatorBox.setForeground(ACCENT);
    operatorBox.setFocusable(false);
    // Custom renderer to center-align dropdown items
    operatorBox.setRenderer(new DefaultListCellRenderer() {
      @Override
      public Component getListCellRendererComponent(
          JList<?> list, Object value, int index,
          boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setHorizontalAlignment(CENTER);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setBackground(isSelected ? ACCENT : INPUT_BG);
        setForeground(isSelected ? BG_DARK : TEXT_PRIMARY);
        return this;
      }
    });

    // Action buttons
    calculateBtn = createStyledButton("Calculate", ACCENT, BG_DARK);
    clearBtn = createStyledButton("Clear", new Color(80, 80, 105), TEXT_PRIMARY);

    // Formula display label
    formulaLabel = new JLabel(" ");
    formulaLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
    formulaLabel.setForeground(TEXT_DIM);
    formulaLabel.setHorizontalAlignment(SwingConstants.CENTER);

    // Result display label
    resultLabel = new JLabel("Awaiting input...");
    resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
    resultLabel.setForeground(TEXT_DIM);
    resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
  }

  // ----- Layout Assembly -----
  private void layoutComponents() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBackground(BG_DARK);
    mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

    // Title
    JLabel title = new JLabel("MVC Calculator");
    title.setFont(new Font("SansSerif", Font.BOLD, 20));
    title.setForeground(ACCENT);
    title.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainPanel.add(title);
    mainPanel.add(Box.createVerticalStrut(5));

    JLabel subtitle = new JLabel("Design Pattern: MVC + Observer");
    subtitle.setFont(new Font("SansSerif", Font.PLAIN, 11));
    subtitle.setForeground(TEXT_DIM);
    subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
    mainPanel.add(subtitle);
    mainPanel.add(Box.createVerticalStrut(18));

    // Input row: [field1] [operator] [field2]
    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    inputPanel.setBackground(BG_DARK);
    inputPanel.add(field1);
    inputPanel.add(operatorBox);
    inputPanel.add(field2);
    mainPanel.add(inputPanel);
    mainPanel.add(Box.createVerticalStrut(15));

    // Button row
    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
    btnPanel.setBackground(BG_DARK);
    btnPanel.add(calculateBtn);
    btnPanel.add(clearBtn);
    mainPanel.add(btnPanel);
    mainPanel.add(Box.createVerticalStrut(18));

    // Result panel with bordered background
    JPanel resultPanel = new JPanel();
    resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
    resultPanel.setBackground(BG_PANEL);
    resultPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(60, 60, 90), 1, true),
        new EmptyBorder(12, 15, 12, 15)
    ));
    formulaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    resultPanel.add(formulaLabel);
    resultPanel.add(Box.createVerticalStrut(5));
    resultPanel.add(resultLabel);
    mainPanel.add(resultPanel);

    add(mainPanel);
  }

  // ===== Factory Method: Styled Text Field =====
  private JTextField createStyledTextField(String placeholder) {
    JTextField tf = new JTextField(8);
    tf.setFont(new Font("SansSerif", Font.PLAIN, 16));
    tf.setPreferredSize(new Dimension(120, 40));
    tf.setBackground(INPUT_BG);
    tf.setForeground(TEXT_PRIMARY);
    tf.setCaretColor(ACCENT);
    tf.setHorizontalAlignment(JTextField.CENTER);
    tf.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(70, 70, 100), 1, true),
        new EmptyBorder(5, 10, 5, 10)
    ));
    // Placeholder behavior via FocusListener
    tf.setText(placeholder);
    tf.setForeground(TEXT_DIM);
    tf.addFocusListener(new FocusAdapter() {
      @Override
      public void focusGained(FocusEvent e) {
        if (tf.getText().equals(placeholder)) {
          tf.setText("");
          tf.setForeground(TEXT_PRIMARY);
        }
      }

      @Override
      public void focusLost(FocusEvent e) {
        if (tf.getText().isEmpty()) {
          tf.setText(placeholder);
          tf.setForeground(TEXT_DIM);
        }
      }
    });
    return tf;
  }

  // ===== Factory Method: Styled Button =====
  private JButton createStyledButton(String text, Color bg, Color fg) {
    JButton btn = new JButton(text);
    btn.setFont(new Font("SansSerif", Font.BOLD, 14));
    btn.setPreferredSize(new Dimension(100, 38));
    btn.setBackground(bg);
    btn.setForeground(fg);
    btn.setFocusPainted(false);
    btn.setBorderPainted(false);
    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    // Hover effect
    Color hoverColor = bg.equals(ACCENT) ? ACCENT_HOVER : new Color(90, 90, 120);
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent e) {
        btn.setBackground(hoverColor);
      }

      public void mouseExited(java.awt.event.MouseEvent e) {
        btn.setBackground(bg);
      }
    });
    return btn;
  }

  // =============================================
  //  Public API: Called by Controller (MVC decoupling)
  // =============================================

  /**
   * Returns text from input field A
   */
  public String getInput1() {
    String t = field1.getText().trim();
    return t.equals("Number A") ? "" : t;
  }

  /**
   * Returns text from input field B
   */
  public String getInput2() {
    String t = field2.getText().trim();
    return t.equals("Number B") ? "" : t;
  }

  /**
   * Returns the currently selected operator
   */
  public String getSelectedOperator() {
    return (String) operatorBox.getSelectedItem();
  }

  /**
   * Displays a successful result with formula
   */
  public void setResult(String formula, String result) {
    formulaLabel.setText(formula);
    resultLabel.setText("= " + result);
    resultLabel.setForeground(SUCCESS);
  }

  /**
   * Displays an error message
   */
  public void setError(String message) {
    formulaLabel.setText(" ");
    resultLabel.setText(message);
    resultLabel.setForeground(ERROR);
  }

  /**
   * Resets all inputs and result display
   */
  public void clearAll() {
    field1.setText("Number A");
    field1.setForeground(TEXT_DIM);
    field2.setText("Number B");
    field2.setForeground(TEXT_DIM);
    operatorBox.setSelectedIndex(0);
    formulaLabel.setText(" ");
    resultLabel.setText("Awaiting input...");
    resultLabel.setForeground(TEXT_DIM);
  }

  /**
   * Observer Pattern: Controller registers its listener here.
   * View does not know who is listening — it only exposes the hook.
   */
  public void addCalculateListener(ActionListener listener) {
    calculateBtn.addActionListener(listener);
  }

  public void addClearListener(ActionListener listener) {
    clearBtn.addActionListener(listener);
  }
}