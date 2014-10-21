package helpers;

import org.apache.commons.codec.DecoderException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import static org.apache.commons.codec.binary.Hex.decodeHex;
import static org.apache.commons.codec.binary.Hex.encodeHexString;

public class Password {

  public static String generateStrongPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
    int iterations = 10000;
    byte[] salt = generateSalt();
    byte[] hash = calculateHash(password, iterations, salt, 64);
    return iterations + ":" + encodeHexString(salt) + ":" + encodeHexString(hash);
  }

  public static boolean validatePassword(String enteredPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException, DecoderException {
    String[] parts = storedPassword.split(":");
    int iterations = Integer.parseInt(parts[0]);
    byte[] salt = decodeHex(parts[1].toCharArray());
    byte[] storedHash = decodeHex(parts[2].toCharArray());

    byte[] testHash = calculateHash(enteredPassword, iterations, salt, storedHash.length);
    return areEqual(storedHash, testHash);
  }

  private static boolean areEqual(byte[] hash, byte[] testHash) {
    int diff = hash.length ^ testHash.length;
    for (int i = 0; i < hash.length && i < testHash.length; i++) {
      diff |= hash[i] ^ testHash[i];
    }
    return diff == 0;
  }

  private static byte[] calculateHash(String originalPassword, int iterations, byte[] salt, int hashLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
    PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hashLength * 8);
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    return skf.generateSecret(spec).getEncoded();
  }

  private static byte[] generateSalt() throws NoSuchAlgorithmException {
    SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
    byte[] salt = new byte[16];
    sr.nextBytes(salt);
    return salt;
  }

  public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
    for (String arg : args) System.out.println(generateStrongPasswordHash(arg) + " = " + arg);
  }
}
