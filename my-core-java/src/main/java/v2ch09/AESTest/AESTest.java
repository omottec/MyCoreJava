package v2ch09.AESTest;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

/**
 * Created by qinbingbing on 6/22/16.<br/>
 * java AESTest -genkey keyfile<br/>
 * java AESTest -encrypt plaintext encrypted keyfile<br/>
 * java AESTest -decrypt encrypted decrypted keyfile<br/>
 */
public class AESTest {
    public static void main(String[] args) {
        test2(args);
    }

    private static void test(String[] args) {
        try {
            if (args[0].equals("-genkey")) {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                SecureRandom random = new SecureRandom();
                keyGen.init(random);
                SecretKey key = keyGen.generateKey();
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]));
                out.writeObject(key);
                out.close();
            } else {
                int mode;
                if (args[0].equals("-encrypt"))
                    mode = Cipher.ENCRYPT_MODE;
                else
                    mode = Cipher.DECRYPT_MODE;
                ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                Key key = (Key) keyIn.readObject();
                InputStream in = new FileInputStream(args[1]);
                OutputStream out = new FileOutputStream(args[2]);
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(mode, key);
                crypt(in, out, cipher);
                in.close();
                out.close();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private static void test2(String[] args) {
        try {
            if (args[0].equals("-genkey")) {
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                SecureRandom random = new SecureRandom();
                keyGen.init(random);
                SecretKey key = keyGen.generateKey();
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]));
                out.writeObject(key);
                out.close();
            } else {
                int mode;
                if (args[0].equals("-encrypt"))
                    mode = Cipher.ENCRYPT_MODE;
                else
                    mode = Cipher.DECRYPT_MODE;
                byte[] encoded = "qinbingbingmagic".getBytes();//Key data
                SecretKey key = new SecretKeySpec(encoded, "AES");
                InputStream in = new FileInputStream(args[1]);
                OutputStream out = new FileOutputStream(args[2]);
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(mode, key);
                crypt(in, out, cipher);
                in.close();
                out.close();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void test1() {
        try {
            InputStream in = new FileInputStream("/Users/didi/GitHub/MyCoreJava/src/v2ch09/AESTest/plaintextFile");
            OutputStream out = new FileOutputStream("/Users/didi/GitHub/MyCoreJava/src/v2ch09/AESTest/encryptFile");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] encoded = "qinbingbingmagic".getBytes();//Key data
            SecretKey secret = new SecretKeySpec(encoded, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            crypt(in, out, cipher);
            in.close();
            out.close();

            in = new FileInputStream("/Users/didi/GitHub/MyCoreJava/src/v2ch09/AESTest/encryptFile");
            out = new FileOutputStream("/Users/didi/GitHub/MyCoreJava/src/v2ch09/AESTest/decryptFile");
            encoded = "qinbingbingmagic".getBytes();//Key data
            secret = new SecretKeySpec(encoded, "AES");
            cipher.init(cipher.DECRYPT_MODE, secret);
            crypt(in, out, cipher);
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ShortBufferException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private static void crypt(InputStream in, OutputStream out, Cipher cipher) throws IOException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);
        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];
        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, inLength, outBytes);
                out.write(outBytes, 0, outLength);
            } else
                more = false;
        }
        if (inLength > 0)
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        else
            outBytes = cipher.doFinal();
        out.write(outBytes);
    }
}
