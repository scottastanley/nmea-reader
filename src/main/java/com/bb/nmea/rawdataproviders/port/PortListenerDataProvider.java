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
package com.bb.nmea.rawdataproviders.port;

import org.apache.log4j.Logger;

import com.bb.nmea.DataProvider;
import com.bb.nmea.DataProviderException;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class PortListenerDataProvider extends DataProvider implements SerialPortDataListener {
    private static final Logger LOG = Logger.getLogger(PortListenerDataProvider.class);
    
    private static String PORT_DESC = "/dev/cu.usbserial-AM009S8I";
    private static Integer BAUD = 4800;

    private SerialPort m_port = null;



    public PortListenerDataProvider() {
        m_port = SerialPort.getCommPort(PORT_DESC);
        m_port.setBaudRate(BAUD);
        m_port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
    }

    @Override
    public void start() throws DataProviderException {
        m_port.addDataListener(this);
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.debug("Shutting down...");
                m_port.closePort();
            }
        });
        
        if (! m_port.openPort()) {
            LOG.error("Failed to open serial port");
            throw new DataProviderException("Failed to open serial port");
        }
    }

    @Override
    public void stop() throws DataProviderException {
        m_port.closePort();
        m_port = null;
    }

    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_AVAILABLE |  SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        try {
            byte[] data = event.getReceivedData();
            
//            LOG.info("Read " + data.length + " bytes (event type: " + event.getEventType() + ")");
            
            this.provideData(data, 0, data.length);
        } catch (DataProviderException e) {
            LOG.error("Failed provide data", e);
        }
    }

}
