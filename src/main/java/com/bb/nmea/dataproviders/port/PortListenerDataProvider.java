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
package com.bb.nmea.dataproviders.port;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bb.nmea.DataProvider;
import com.bb.nmea.DataProviderException;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * A sample {@link com.bb.nmea.DataProvider} which listens to a serial port and provides 
 * all received data to the {@link com.bb.nmea.NMEASentenceProvider}.
 * 
 * @author sstanley
 */
public class PortListenerDataProvider extends DataProvider implements SerialPortDataListener {
    private static final Logger LOG = LogManager.getLogger(PortListenerDataProvider.class);
   
    private SerialPort m_port = null;

    /**
     * Construct a PortListenerDataProvider for the provided port descriptor and baud.
     * 
     * @param descriptor
     * @param baudRate
     */
    public PortListenerDataProvider(final String descriptor, final Integer baudRate) {
        m_port = SerialPort.getCommPort(descriptor);
        m_port.setBaudRate(baudRate);
        m_port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
    }

    @Override
    public void start() throws DataProviderException {
        LOG.info("Starting " + m_port.getSystemPortName() + " ...");
        m_port.addDataListener(this);
        
        if (! m_port.openPort()) {
            LOG.error("Failed to open serial port");
            throw new DataProviderException("Failed to open serial port");
        }
    }

    @Override
    public void stop() throws DataProviderException {
        LOG.info("Stopping " + m_port.getSystemPortName() + " ...");
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
            this.provideData(data, 0, data.length);
        } catch (DataProviderException e) {
            LOG.error("Failed provide data", e);
        }
    }

}
