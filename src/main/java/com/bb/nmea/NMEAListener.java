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
package com.bb.nmea;

import java.util.Properties;

/**
 * The abstract class which must be extended by all listeners for NMEA sentences.
 * 
 * @author Scott Stanley
 */
public abstract class NMEAListener {
    private Properties m_properties;
    
    /**
     * Process the provided NMEASentence.  This method will be called with all
     * sentences received by the system, including invalid sentences as well as those 
     * for which an explicit class is not defined.
     * 
     * @param sentence The NMEA sentence
     */
    abstract public void processEvent(final NMEASentence sentence);
    
    /**
     * Stop processing data in the listener and close all open resources.
     */
    abstract public void stop();
    
    /**
     * Set the properties for this NMEAListener.
     * 
     * @param properties The properties providing configuration details
     */
    public void setProperties(final Properties properties) {
        m_properties = properties;
    }
    
    /**
     * Get the properties.
     * 
     * @return The Properties containing configuration details for this NMEAListener.
     */
    protected Properties getProperties() {
        return m_properties;
    }
}
