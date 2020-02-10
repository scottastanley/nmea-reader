package com.bb.nmea.main;

import java.util.Properties;

public class FileConfig {
    private static final String FILE_PREFIX = "files.";
    private static final String IDS_PROPERTY = "ids";
    private static final String FILENAME_PROPERTY = "filename";
    private static final String PAUSE_MILLIS_PROPERTY = "pause_millis";

    private final String m_filename;
    private final Long m_pauseMillis;

    public FileConfig(final String fileId, final Properties props) {
        m_filename = props.getProperty(FILE_PREFIX + fileId + "." + FILENAME_PROPERTY);
        
        String pauseMillisStr = props.getProperty(FILE_PREFIX + fileId + "." + PAUSE_MILLIS_PROPERTY);
        if (pauseMillisStr != null) {
            m_pauseMillis = new Long(pauseMillisStr);
        } else {
            m_pauseMillis = 0L;
        }
    }
    
    /**
     * Get the list of file configurations.
     * 
     * @param props
     * @return
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
    public String getFilename() {
        return m_filename;
    }

    /**
     * @return the pauseMillis
     */
    public Long getPauseMillis() {
        return m_pauseMillis;
    }
}
