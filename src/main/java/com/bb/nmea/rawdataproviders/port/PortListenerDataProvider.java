package com.bb.nmea.rawdataproviders.port;

import org.apache.log4j.Logger;

import com.bb.nmea.DataProvider;
import com.bb.nmea.rawdataproviders.DataProviderException;
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
