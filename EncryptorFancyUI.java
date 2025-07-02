import EncryptorFancyUI.1;
import EncryptorFancyUI.2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.dnd.DropTarget;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class EncryptorFancyUI extends JFrame {
   private JLabel fileLabel;
   private File selectedFile;

   public EncryptorFancyUI() {
      this.setTitle("\ud83d\udcc1 File Encryptor");
      this.setSize(500, 260);
      this.setDefaultCloseOperation(3);
      this.setLocationRelativeTo((Component)null);
      this.setLayout(new BorderLayout());
      this.getContentPane().setBackground(new Color(245, 248, 255));
      JPanel dropPanel = new JPanel(new BorderLayout());
      dropPanel.setBackground(new Color(255, 255, 255));
      dropPanel.setBorder(BorderFactory.createDashedBorder(new Color(100, 149, 237), 2.0F, 5.0F));
      JLabel icon = new JLabel("⬆", 0);
      icon.setFont(new Font("SansSerif", 0, 40));
      icon.setForeground(new Color(100, 149, 237));
      this.fileLabel = new JLabel("Drag and Drop file here or ", 0);
      this.fileLabel.setFont(new Font("SansSerif", 0, 16));
      this.fileLabel.setForeground(new Color(80, 80, 80));
      JLabel chooseFileLink = new JLabel("<html><u>Choose file</u></html>");
      chooseFileLink.setFont(new Font("SansSerif", 1, 16));
      chooseFileLink.setForeground(new Color(30, 144, 255));
      chooseFileLink.setCursor(Cursor.getPredefinedCursor(12));
      chooseFileLink.addMouseListener(new 1(this));
      JPanel labelPanel = new JPanel();
      labelPanel.setLayout(new FlowLayout(1, 5, 0));
      labelPanel.setBackground(Color.WHITE);
      labelPanel.add(this.fileLabel);
      labelPanel.add(chooseFileLink);
      dropPanel.add(icon, "North");
      dropPanel.add(labelPanel, "Center");
      dropPanel.setPreferredSize(new Dimension(400, 200));
      dropPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
      new DropTarget(dropPanel, new 2(this));
      JButton encryptBtn = new JButton("Encrypt");
      JButton decryptBtn = new JButton("Decrypt");
      encryptBtn.setBackground(new Color(30, 144, 255));
      encryptBtn.setForeground(Color.WHITE);
      decryptBtn.setBackground(new Color(34, 139, 34));
      decryptBtn.setForeground(Color.WHITE);
      encryptBtn.setFocusPainted(false);
      decryptBtn.setFocusPainted(false);
      encryptBtn.setFont(new Font("SansSerif", 1, 14));
      decryptBtn.setFont(new Font("SansSerif", 1, 14));
      encryptBtn.addActionListener((e) -> {
         if (this.selectedFile != null) {
            try {
               FileEncryptor.generateKey();
               FileEncryptor.encryptFile(this.selectedFile.getAbsolutePath(), FileEncryptor.loadKey());
               JOptionPane.showMessageDialog(this, "File encrypted successfully!");
            } catch (Exception var3) {
               var3.printStackTrace();
               JOptionPane.showMessageDialog(this, "Encryption failed: " + var3.getMessage());
            }
         } else {
            JOptionPane.showMessageDialog(this, "Please select a file first.");
         }

      });
      decryptBtn.addActionListener((e) -> {
         if (this.selectedFile != null) {
            try {
               FileEncryptor.decryptFile(this.selectedFile.getAbsolutePath(), FileEncryptor.loadKey());
               JOptionPane.showMessageDialog(this, "File decrypted successfully!");
            } catch (Exception var3) {
               var3.printStackTrace();
               JOptionPane.showMessageDialog(this, "Decryption failed: " + var3.getMessage());
            }
         } else {
            JOptionPane.showMessageDialog(this, "Please select a file first.");
         }

      });
      JPanel bottomPanel = new JPanel();
      bottomPanel.setBackground(new Color(245, 248, 255));
      bottomPanel.setLayout(new FlowLayout());
      bottomPanel.add(encryptBtn);
      bottomPanel.add(decryptBtn);
      this.add(dropPanel, "Center");
      this.add(bottomPanel, "South");
      this.setVisible(true);
   }

   public static void main(String[] args) {
      SwingUtilities.invokeLater(EncryptorFancyUI::new);
   }
}
