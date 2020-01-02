package com.bb.nmea.rawdataproviders;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataCollectorOutputStream extends PipedOutputStream {
    List<Byte> m_collectedBytes = new ArrayList<Byte>();

    public DataCollectorOutputStream() {
    }

    public DataCollectorOutputStream(PipedInputStream snk) throws IOException {
        super(snk);
    }

    /* (non-Javadoc)
     * @see java.io.PipedOutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException {
        throw new RuntimeException("Method write(int b) not supported in DataCollectorOutputStream");
    }

    /* (non-Javadoc)
     * @see java.io.PipedOutputStream#write(byte[], int, int)
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        for (int n = off; n < off + len; n++) {
            m_collectedBytes.add(b[n]);
        }
        System.out.println(new String(b, off, len));
    }
    
    /**
     * Get the bytes collected in this output stream
     * 
     * @return
     */
    public byte[] getCollectedBytes() {
        byte[] bytes = new byte[m_collectedBytes.size()];
        for (int n = 0; n < m_collectedBytes.size(); n++) {
            bytes[n] = m_collectedBytes.get(n);
        }
        return bytes;
    }
}
