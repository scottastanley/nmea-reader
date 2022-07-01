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
import java.util.Iterator;
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
    protected static String NMEA_CONFIG_FILE_PROP = "nmeaConfigFile";
    private String m_propertiesFile = "nmea_config.properties";
    
    private final Properties m_props = new Properties();
    

    /**
     * Construct the configuration object.
     */
    Configuration(final String[] args) {
        //
        // Check for an override configuration file name
        //
        // If we have a single argument, then use it as a properties file name
        //
        String overrideFilename = System.getProperties().getProperty(NMEA_CONFIG_FILE_PROP);
        
        if (args.length > 0) {
            if (args.length > 1) {
                System.err.println("Only a single command line argument supported.  " +
                                   args.length + " arguments provided.");
                System.exit(-1);
            } else {
                overrideFilename = args[0];
            }
        }
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
    
    /**
     * Get the list of {@link com.bb.nmea.main.ListenerConfig}s to process the NMEA
     * data from the input sources.  If no listeners have been configured, then
     * an empty list is returned.
     * 
     * @return A list of {@link com.bb.nmea.main.ListenerConfig} instances
     */
    List<ListenerConfig> getListenerConfigs() {
        List<ListenerConfig> configs = new ArrayList<ListenerConfig>();
        
        for (String portId : ListenerConfig.getListenerIds(m_props)) {
            Properties listProps = filterPropertiesOnPrefix(ListenerConfig.getListenerPropertyPrefix(portId), m_props);
            
            ListenerConfig cfg = new ListenerConfig(listProps);
            configs.add(cfg);
        }
        
        return configs;
    }
    
    
    /**
     * Obtain a Properties object from the specified Properties by finding
     * all entries that have a key starting with the specified prefix.
     * The key values in the returned Properties object have the prefix
     * removed.
     *
     * @param prefix The prefix to filter the Properties object on.
     * @param props The parent Properties object to search.
     * @return A Properties object with all of the entries from the original
     * Properties object whose key starts witht he specified suffix.
     */
    private static Properties filterPropertiesOnPrefix(String prefix,
                                                       Properties props)
    {
        Properties retProps = new Properties();
        int suffixStrtIndx = prefix.length();

        Iterator<String> names = props.stringPropertyNames().iterator();
        while (names.hasNext())
        {
            String name = names.next();

            if (name.startsWith(prefix))
            {
                String suffix = name.substring(suffixStrtIndx);
                retProps.setProperty(suffix, props.getProperty(name));
            }
        }

        return retProps;
    }
}
