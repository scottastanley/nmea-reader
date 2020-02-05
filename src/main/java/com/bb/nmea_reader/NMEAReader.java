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
package com.bb.nmea_reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fazecast.jSerialComm.SerialPort;

public class NMEAReader {
    private static final Logger LOG = Logger.getLogger(NMEAReader.class);
    private static String PORT_DESC = "/dev/cu.usbserial-AM009S8I";
    private static Integer BAUD = 4800;
    
    private SerialPort m_port = null;
    
    private List<NMEAListener> m_listeners = new ArrayList<NMEAListener>();


    public NMEAReader() {
        m_port = SerialPort.getCommPort(PORT_DESC);
        m_port.setBaudRate(BAUD);
        m_port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
    }
    
    public void addListener(final NMEAListener listener) {
        m_listeners.add(listener);
    }
    
    public void start() throws NMEAReaderException,FileNotFoundException  {
        long timestamp = System.currentTimeMillis();
        FileOutputStream rawOStrm = new FileOutputStream("raw_" + timestamp + ".nmea");

        BufferedReader rdr;
        try {
            PipedInputStream inpStrm = new PipedInputStream();
            PipedOutputStream outStrm = new PipedOutputStream(inpStrm);

            PortListener listener = new PortListener(outStrm, rawOStrm);
            m_port.addDataListener(listener);

            rdr = new BufferedReader(new InputStreamReader(inpStrm));
        } catch (IOException e) {
            throw new NMEAReaderException("Failed configuring NMEA reader", e);
        }
        
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.debug("Shutting down...");
                m_port.closePort();
                
                if (rawOStrm != null) {
                    try {
                        rawOStrm.close();
                    } catch (IOException e) {}
                }
            }
        });

        
        m_port.openPort();
        
        try {
            String sentenceStr = null;
            while((sentenceStr = rdr.readLine()) != null) {
                notifyListeners(sentenceStr);
                LOG.info(sentenceStr);
            }
        } catch (IOException e) {
            throw new NMEAReaderException("Failed reading NMEA sentences", e);
        }
    }
    
    private void notifyListeners(final String nmeaSentenceStr) {
        m_listeners.forEach((l) -> {l.newSentence(nmeaSentenceStr);});
    }
}