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
 * A simple java object representing an input file configuration.
 * 
 * @author Scott Stanley
 */
class FileConfig {
    private static final String FILE_PREFIX = "files.";
    private static final String IDS_PROPERTY = "ids";
    private static final String FILENAME_PROPERTY = "filename";
    private static final String PAUSE_MILLIS_PROPERTY = "pause_millis";

    private final String m_filename;
    private final Long m_pauseMillis;

    /**
     * Create a FileConfig instance using the provided properties for the
     * specified configuration ID.
     * 
     * @param fileId The file configuration ID
     * @param props The set of properties to extract configuration from
     */
    FileConfig(final String fileId, final Properties props) {
        m_filename = props.getProperty(FILE_PREFIX + fileId + "." + FILENAME_PROPERTY);
        
        String pauseMillisStr = props.getProperty(FILE_PREFIX + fileId + "." + PAUSE_MILLIS_PROPERTY);
        if (pauseMillisStr != null) {
            m_pauseMillis = new Long(pauseMillisStr);
        } else {
            m_pauseMillis = 0L;
        }
    }
    
    
    /**
     * Get the list of file configuration IDs.
     * 
     * @param props The properties 
     * @return An array of strings of file configuration IDs
     */
    static String[] getFileIds(final Properties props) {
        String idsStr = props.getProperty(FILE_PREFIX + IDS_PROPERTY);
        
        String[] fileIds = null;
        if (idsStr != null) {
            fileIds = idsStr.trim().split(",");
        } else {
            fileIds = new String[0];
        }

        return fileIds; 
    }
    

    /**
     * @return the filename
     */
    String getFilename() {
        return m_filename;
    }
    

    /**
     * @return the pauseMillis
     */
    Long getPauseMillis() {
        return m_pauseMillis;
    }
}
