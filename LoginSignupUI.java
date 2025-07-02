import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LoginSignupUI extends JFrame {
   private CardLayout cardLayout;
   private JPanel cardPanel;
   private UserAuthService authService = new UserAuthService();

   public LoginSignupUI() {
      this.setTitle("Login / Signup - File Encryptor");
      this.setSize(400, 320);
      this.setDefaultCloseOperation(3);
      this.setLocationRelativeTo((Component)null);
      this.setResizable(false);
      this.setLayout(new BorderLayout());
      this.cardLayout = new CardLayout();
      this.cardPanel = new JPanel(this.cardLayout);
      this.cardPanel.add(this.createLoginPanel(), "login");
      this.cardPanel.add(this.createSignupPanel(), "signup");
      JPanel switchPanel = new JPanel();
      JButton loginBtn = new JButton("Login");
      JButton signupBtn = new JButton("Sign Up");
      loginBtn.addActionListener((e) -> {
         this.cardLayout.show(this.cardPanel, "login");
      });
      signupBtn.addActionListener((e) -> {
         this.cardLayout.show(this.cardPanel, "signup");
      });
      switchPanel.add(loginBtn);
      switchPanel.add(signupBtn);
      this.add(this.cardPanel, "Center");
      this.add(switchPanel, "South");
      this.setVisible(true);
   }

   private JPanel createLoginPanel() {
      JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
      panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
      JTextField emailField = new JTextField();
      JPasswordField passField = new JPasswordField();
      JButton loginButton = new JButton("Login");
      panel.add(new JLabel("Email:"));
      panel.add(emailField);
      panel.add(new JLabel("Password:"));
      panel.add(passField);
      panel.add(loginButton);
      loginButton.addActionListener((e) -> {
         String email = emailField.getText();
         String password = new String(passField.getPassword());
         if (this.authService.login(email, password)) {
            JOptionPane.showMessageDialog(this, "Login Successful!");
            this.dispose();
            new EncryptorFancyUI();
         } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.");
         }

      });
      return panel;
   }

   private JPanel createSignupPanel() {
      JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
      panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
      JTextField emailField = new JTextField();
      JPasswordField passField = new JPasswordField();
      JPasswordField confirmField = new JPasswordField();
      JButton signupButton = new JButton("Sign Up");
      panel.add(new JLabel("Email:"));
      panel.add(emailField);
      panel.add(new JLabel("Password:"));
      panel.add(passField);
      panel.add(new JLabel("Confirm Password:"));
      panel.add(confirmField);
      panel.add(signupButton);
      signupButton.addActionListener((e) -> {
         String email = emailField.getText();
         String password = new String(passField.getPassword());
         String confirm = new String(confirmField.getPassword());
         if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match.");
         } else if (this.authService.signup(email, password)) {
            JOptionPane.showMessageDialog(this, "Account created! You can now log in.");
            this.cardLayout.show(this.cardPanel, "login");
         } else {
            JOptionPane.showMessageDialog(this, "Email already exists.");
         }

      });
      return panel;
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(LoginSignupUI::new);
   }
}
