package v2ch09.RsaTest;

import javax.crypto.*;
import java.io.*;
import java.security.*;

/**
 * Created by qinbingbing on 6/22/16<br/>
 * java RsaTest -genkey public private<br/>
 * java RsaTest -encrypt plaintext encrypted public<br/>
 * java RsaTest -decrypt encrypted decrypted private<br/>
 *
 */
public class RsaTest {
    public static final int KEY_SIZE = 512;
    public static void main(String[] args) {
        try {
            if (args[0].equals("-genkey")) {
                KeyPairGenerator pairGen = KeyPairGenerator.getInstance("RSA");
                SecureRandom random = new SecureRandom();
                pairGen.initialize(KEY_SIZE, random);
                KeyPair keyPair = pairGen.generateKeyPair();
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args[1]));
                out.writeObject(keyPair.getPublic());
                out.close();
                out = new ObjectOutputStream(new FileOutputStream(args[2]));
                out.writeObject(keyPair.getPrivate());
                out.close();
            } else if (args[0].equals("-encrypt")){
                KeyGenerator keyGen = KeyGenerator.getInstance("AES");
                SecureRandom random = new SecureRandom();
                keyGen.init(random);
                SecretKey key = keyGen.generateKey();

                ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                Key publicKey = (Key) keyIn.readObject();
                keyIn.close();

                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.WRAP_MODE, publicKey);
                byte[] wrappedKey = cipher.wrap(key);
                DataOutputStream out = new DataOutputStream(new FileOutputStream(args[2]));
                out.writeInt(wrappedKey.length);
                out.write(wrappedKey);

                InputStream in = new FileInputStream(args[1]);
                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                crypt(in, out, cipher);
                in.close();
                out.close();
            } else {
                ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(args[3]));
                Key privateKey = (Key) keyIn.readObject();
                keyIn.close();

                DataInputStream in = new DataInputStream(new FileInputStream(args[1]));
                int length = in.readInt();
                byte[] wrappedKey = new byte[length];
                in.read(wrappedKey, 0, length);

                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.UNWRAP_MODE, privateKey);
                Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);

                cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, key);
                OutputStream out = new FileOutputStream(args[2]);
                crypt(in, out, cipher);
                in.close();
                out.close();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (ShortBufferException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }

    public static void crypt(InputStream in, OutputStream out, Cipher cipher)
            throws IOException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
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
