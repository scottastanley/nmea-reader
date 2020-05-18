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

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import com.bb.nmea.NMEAListener;

/**
 * The configuration details for an NMEAListener.
 * 
 * @author Scott Stanley
 */
public class ListenerConfig {
    static final String LISTENER_PREFIX = "listeners.";
    static final String IDS_PROPERTY = "ids";
    static final String CLASS_PROPERTY = "class";
    
    private final Properties m_props;


    /**
     * Create a new ListenerConfig
     */
    public ListenerConfig(final Properties props) {
        m_props = props;
    }
    
    /**
     * Get the properties prefix for the listener with the given ID.
     * 
     * @param listenerId The listener ID
     * @return The properties prefix for this listener
     */
    static String getListenerPropertyPrefix(final String listenerId) {
        return LISTENER_PREFIX + listenerId + ".";
    }

    /**
     * Get the Listener configuration IDs.
     * 
     * @param props The properties
     * @return The list of IDs
     */
    static String[] getListenerIds(final Properties props) {
        String idsStr = props.getProperty(LISTENER_PREFIX + IDS_PROPERTY);
        
        String[] ids = null;
        if (idsStr != null) {
            ids = idsStr.trim().split(",");
        } else {
            ids = new String[0];
        }

        return ids; 
    }
    
    /**
     * Get an instance of this NMEAListener.
     * 
     * @return The NMEAListener
     */
    NMEAListener getListenerInstance() {
        String className = m_props.getProperty(CLASS_PROPERTY);
        
        NMEAListener list = null;
        try {
            Class<? extends NMEAListener> z = Class.forName(className).asSubclass(NMEAListener.class);
            list = z.getDeclaredConstructor().newInstance();
            list.setProperties(m_props);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
                 IllegalArgumentException | InvocationTargetException | NoSuchMethodException | 
                 SecurityException e) {
            e.printStackTrace();
        }
        
        return list;
    }
}
