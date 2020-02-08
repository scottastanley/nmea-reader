package com.bb.nmea.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configuration {
    private String m_propertiesFile = "nmea_config.properties";
    
    private final Properties m_props = new Properties();

    public Configuration() {
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
    
    public List<PortConfig> getPortConfigs() {
        List<PortConfig> configs = new ArrayList<PortConfig>();
        
        for (String portId : PortConfig.getPortIds(m_props)) {
            configs.add(new PortConfig(portId, m_props));
        }
        
        return configs;
    }
}