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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Configuration is a utility class which organizes the logic used to 
 * manage the access to the properties used to configure the example
 * client.
 * 
 * @author Scott Stanley
 */
class Configuration {
    private static String NMEA_CONFIG_FILE_PROP = "nmeaConfigFile";
    private String m_propertiesFile = "nmea_config.properties";
    
    private final Properties m_props = new Properties();
    

    /**
     * Construct the configuration object.
     */
    Configuration() {
        //
        // Check for an override configuration file name
        //
        String overrideFilename = System.getProperties().getProperty(NMEA_CONFIG_FILE_PROP);
        if (overrideFilename != null) {
            m_propertiesFile = overrideFilename;
        }
        
        //
        // Load the configuration
        //
        File configFile = new File(m_propertiesFile);
        
        FileReader rdr = null;
        try {
            rdr = new FileReader(configFile);
            m_props.load(rdr);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not find configuration file, " + m_propertiesFile, e);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading configuration file, " + m_propertiesFile, e);
        } finally {
            if (rdr != null) {
                try {
                    rdr.close();
                } catch (IOException e) {}
            }
        }
    }
    
    
    /**
     * Get the list of {@link com.bb.nmea.main.PortConfig}s for each serial port
     * to be listened to.  If no ports have been configured, then an empty list 
     * is returned.
     * 
     * @return A list of {@link com.bb.nmea.main.PortConfig} instances
     */
    List<PortConfig> getPortConfigs() {
        List<PortConfig> configs = new ArrayList<PortConfig>();
        
        for (String portId : PortConfig.getPortIds(m_props)) {
            configs.add(new PortConfig(portId, m_props));
        }
        
        return configs;
    }
    
    
    /**
     * Get the list of {@link com.bb.nmea.main.FileConfig}s for each input
     * file to be read for NMEA data.  If no files have been configured, then 
     * an empty list is returned.
     * 
     * @return A list of {@link com.bb.nmea.main.FileConfig} instances
     */
    List<FileConfig> getFileConfigs() {
        List<FileConfig> configs = new ArrayList<FileConfig>();
        
        for (String portId : FileConfig.getFileIds(m_props)) {
            configs.add(new FileConfig(portId, m_props));
        }
        
        return configs;
    }
}
