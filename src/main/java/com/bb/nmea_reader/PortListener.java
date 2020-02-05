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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedOutputStream;

import org.apache.log4j.Logger;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class PortListener implements SerialPortDataListener {
    private static final Logger LOG = Logger.getLogger(PortListener.class);

    private final PipedOutputStream m_outStrm;
    private final OutputStream m_rawOStrm;

    public PortListener(final PipedOutputStream outStrm, final OutputStream rawOStrm) {
        m_outStrm = outStrm;
        m_rawOStrm = rawOStrm;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE |  SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        try {
            byte[] data = event.getReceivedData();
            
//            LOG.info("Read " + data.length + " bytes");
            m_outStrm.write(data);
            m_rawOStrm.write(data);
        } catch (IOException e) {
            LOG.error("Failed writing data to piped stream", e);
        }
    }
}
