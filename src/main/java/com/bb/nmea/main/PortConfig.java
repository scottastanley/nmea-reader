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
package com.bb.nmea.main;

import java.util.Properties;

/**
 * A simple java object representing an port configuration.
 * 
 * @author Scott Stanley
 */
class PortConfig {
    static final String PORT_PREFIX = "ports.";
    static final String IDS_PROPERTY = "ids";
    static final String DESCRIPTOR_PROPERTY = "descriptor";
    static final String BAUDRATE_PROPERTY = "baudRate";
    
    private final String m_descriptor;
    private final Integer m_baudRate;

    /**
     * Create a PortConfig instance using the provided properties for the
     * specified configuration ID.
     * 
     * @param portId The port configuration ID
     * @param props The set of properties to extract configuration from
     */
    PortConfig(final String portId, final Properties props) {
        m_descriptor = props.getProperty(PORT_PREFIX + portId + "." + DESCRIPTOR_PROPERTY);
        
        String baudRateStr = props.getProperty(PORT_PREFIX +portId + "." + BAUDRATE_PROPERTY);
        if (baudRateStr != null) {
            m_baudRate = new Integer(baudRateStr);
        } else {
            m_baudRate = null;
        }
    }
    

    /**
     * Get the list of port configuration IDs.
     * 
     * @param props The properties
     * @return An array of strings with port configuration IDs
     */
    static String[] getPortIds(final Properties props) {
        String idsStr = props.getProperty(PORT_PREFIX + IDS_PROPERTY);

        String[] portIds = null;
        if (idsStr != null) {
            portIds = idsStr.trim().split(",");
        } else {
            portIds = new String[0];
        }
        
        return portIds; 
    }
    

    /**
     * @return the descriptor
     */
    String getDescriptor() {
        return m_descriptor;
    }
    

    /**
     * @return the baudRate
     */
    Integer getBaudRate() {
        return m_baudRate;
    }
}
