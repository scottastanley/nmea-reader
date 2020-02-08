package com.bb.nmea.main;

import java.util.Properties;

public class PortConfig {
    private static final String PORT_PREFIX = "ports.";
    private static final String IDS_PROPERTY = "ids";
    private static final String DESCRIPTOR_PROPERTY = "descriptor";
    private static final String BAUDRATE_PROPERTY = "baudRate";
    
    private final String m_descriptor;
    private final Integer m_baudRate;

    public PortConfig(final String portId, final Properties props) {
        m_descriptor = props.getProperty(PORT_PREFIX + portId + "." + DESCRIPTOR_PROPERTY);
        
        String baudRateStr = props.getProperty(PORT_PREFIX +portId + "." + BAUDRATE_PROPERTY);
        if (baudRateStr != null) {
            m_baudRate = new Integer(baudRateStr);
        } else {
            m_baudRate = null;
        }
    }
    
    static String[] getPortIds(final Properties props) {
        String idsStr = props.getProperty(PORT_PREFIX + IDS_PROPERTY);
        String[] propIds = idsStr.trim().split(",");
        return propIds; 
    }

    /**
     * @return the descriptor
     */
    public String getDescriptor() {
        return m_descriptor;
    }

    /**
     * @return the baudRate
     */
    public Integer getBaudRate() {
        return m_baudRate;
    }

}
