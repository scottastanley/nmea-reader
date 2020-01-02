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
