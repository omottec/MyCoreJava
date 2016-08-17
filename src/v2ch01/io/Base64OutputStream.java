package v2ch01.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by qinbingbing on 8/17/16.
 */
public class Base64OutputStream extends FilterOutputStream {
    private static char[] toBase64 = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '+', '/'
    };

    private int i;
    private int col;
    private int[] buf = new int[3];

    /**
     * Creates an output stream filter built on top of the specified
     * underlying output stream.
     *
     * @param out the underlying output stream to be assigned to
     *            the field <tt>this.out</tt> for later use, or
     *            <code>null</code> if this instance is to be
     *            created without an underlying stream.
     */
    public Base64OutputStream(OutputStream out) {
        super(out);
    }

    @Override
    public void write(int b) throws IOException {
        buf[i++] = b;
        if (i == 3) {
            super.write((buf[0] & 0xFC) >> 2);
            super.write(((buf[0] & 0x03) << 4) + ((buf[1] & 0xF0) >> 4));
            super.write(((buf[1] & 0x0F) << 2) + (buf[2] & 0xC0) >> 6);
            super.write((buf[2] & 0x3F));
            col += 4;
            if (col >= 76) {
                super.write('\n');
                col = 0;
            }
        }
    }

    @Override
    public void flush() throws IOException {
        if (i == 1) {
            super.write((buf[0] & 0xFC) >> 2);
            super.write((buf[0]  & 0x03) << 4);
            super.write('=');
            super.write('=');
        } else if (i == 2) {
            super.write((buf[0] & 0xFC) >> 2);
            super.write(((buf[0]  & 0x03) << 4) + ((buf[1] & 0xF0) >> 4));
            super.write((buf[1] & 0x0F) << 2);
            super.write('=');
        }
        if (col > 0) {
            super.write('\n');
            col = 0;
        }
    }
}