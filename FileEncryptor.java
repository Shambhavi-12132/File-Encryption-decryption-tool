import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptor {
   private static final String KEY_FILE = "Secret.key";

   public static void generateKey() throws Exception {
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      keyGen.init(256, new SecureRandom());
      SecretKey secretKey = keyGen.generateKey();
      byte[] keyBytes = secretKey.getEncoded();
      FileOutputStream fos = new FileOutputStream("Secret.key");

      try {
         fos.write(keyBytes);
      } catch (Throwable var7) {
         try {
            fos.close();
         } catch (Throwable var6) {
            var7.addSuppressed(var6);
         }

         throw var7;
      }

      fos.close();
   }

   public static SecretKey loadKey() throws Exception {
      byte[] keyBytes = Files.readAllBytes((new File("Secret.key")).toPath());
      return new SecretKeySpec(keyBytes, "AES");
   }

   public static void encryptFile(String filename, SecretKey key) throws Exception {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(1, key);
      byte[] fileData = Files.readAllBytes((new File(filename)).toPath());
      byte[] encryptedData = cipher.doFinal(fileData);
      FileOutputStream fos = new FileOutputStream(filename);

      try {
         fos.write(encryptedData);
      } catch (Throwable var9) {
         try {
            fos.close();
         } catch (Throwable var8) {
            var9.addSuppressed(var8);
         }

         throw var9;
      }

      fos.close();
   }

   public static void decryptFile(String filename, SecretKey key) throws Exception {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(2, key);
      byte[] encryptedData = Files.readAllBytes((new File(filename)).toPath());
      byte[] decryptedData = cipher.doFinal(encryptedData);
      FileOutputStream fos = new FileOutputStream(filename);

      try {
         fos.write(decryptedData);
      } catch (Throwable var9) {
         try {
            fos.close();
         } catch (Throwable var8) {
            var9.addSuppressed(var8);
         }

         throw var9;
      }

      fos.close();
   }

   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);
      System.out.print("Enter 'E' to encrypt or 'D' to decrypt the file: ");
      String choice = scanner.nextLine().trim().toLowerCase();

      try {
         String filename;
         File file;
         SecretKey key;
         if (choice.equals("e")) {
            System.out.print("Enter the file name to encrypt: ");
            filename = scanner.nextLine();
            file = new File(filename);
            if (file.exists()) {
               generateKey();
               key = loadKey();
               encryptFile(filename, key);
               System.out.println("File Encrypted Successfully!");
            } else {
               System.out.println("File not found!");
            }
         } else if (choice.equals("d")) {
            System.out.print("Enter the file name to decrypt: ");
            filename = scanner.nextLine();
            file = new File(filename);
            if (file.exists()) {
               key = loadKey();
               decryptFile(filename, key);
               System.out.println("File Decrypted Successfully!");
            } else {
               System.out.println("File not found!");
            }
         } else {
            System.out.println("Invalid choice. Use 'E' for encryption or 'D' for decryption.");
         }
      } catch (Exception var6) {
         System.out.println("An error occurred: " + var6.getMessage());
      }

   }
}
