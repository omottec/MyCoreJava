package v2ch09.AESTest;

import javax.crypto.*;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by qinbingbing on 6/22/16.<br/>
 * java AESTest -genkey keyfile<br/>
 * java AESTest -encrypt plaintext encrypted keyfile<br/>
 * java AESTest -decrypt encrypted decrypted keyfile<br/>
 */
public class AESTest {
    public static void main(String[] args) {
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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
