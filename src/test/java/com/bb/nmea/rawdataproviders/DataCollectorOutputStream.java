/*
 * Copyright 2020 Scott Alan Stanley
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
