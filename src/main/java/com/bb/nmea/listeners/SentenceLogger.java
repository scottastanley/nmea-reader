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
package com.bb.nmea.listeners;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bb.nmea.NMEAListener;
import com.bb.nmea.NMEASentence;

/**
 * A simple example {@link com.bb.nmea.NMEAListener} which accepts and logs all NMEA sentences
 * to an output file.  The log file is consistent with that required by the 
 * {@link com.bb.nmea.dataproviders.inputfile.InputFileDataProvider}.  This combination of logger and data provider
 * are particularly useful for allowing working when a live NMEA data feed is not available.
 * 
 * @author Scott Stanley
 */
public class SentenceLogger extends NMEAListener {
    private static final Logger LOG = LogManager.getLogger(SentenceLogger.class);
    private static final String FILENAME_TEMPLATE = "raw_{TIMESTAMP}";
    private static final String FILENAME_SUFFIX = ".nmea";
    private static final String LOG_DIRECTORY_PROPERTY = "logDirectory";
    
    private PrintWriter m_wrt;

    public SentenceLogger() {
    }
    
    /**
     * Perform any initialization of the listener.
     */
    @Override
    public void initialize() {
        File oFile = getUniqueFile();
        
        try {
            m_wrt = new PrintWriter(new FileWriter(oFile));
        } catch (IOException e) {
            LOG.error("Failed to open output file", e);
            throw new RuntimeException("Failed to open output file", e);
        }
    }


    @Override
    public void processEvent(NMEASentence sentence) {
        LOG.debug("processing: " + sentence.getRawSentence() + "  " + sentence.isValid());
        m_wrt.println(sentence.getRawSentence());
    }

    @Override
    public void stop() {
        if (m_wrt != null) {
            m_wrt.close();
        }
    }
    
    /**
     * Get a filename with the specified uniquifier.
     * 
     * @param uniquifier
     * @return A filename with the specified uniquifier
     */
    private String getFilename(final Integer uniquifier) {
    	LocalDate now = LocalDate.now();
        String fileNamePrefix = FILENAME_TEMPLATE.replace("{TIMESTAMP}", now.format(DateTimeFormatter.BASIC_ISO_DATE));
        
        if (uniquifier != null) {
        	fileNamePrefix += fileNamePrefix + "_" + uniquifier.toString();
        }
        
        return fileNamePrefix + FILENAME_SUFFIX;
    }
    
    /**
     * Get a file that does not currently exist.
     * 
     * @return A new file which does not currently exist
     */
    private File getUniqueFile() {
    	// Get the base directory for the sentence logs
    	String logDirName = this.getProperties().getProperty(LOG_DIRECTORY_PROPERTY);
    	File logDir;
    	if (logDirName != null) {
    		logDir = new File(logDirName);
    		
    		if (! logDir.exists()) {
    			logDir.mkdirs();
    		}
    	} else {
    		logDir = new File("./");
    	}
    	
    	// Get the sentence log file
        String fileName = getFilename(null);
        File oFile = new File(logDir, fileName);
        
        int uniqueifier = 1;
        while (oFile.exists()) {
        	fileName = getFilename(uniqueifier);
            oFile = new File(fileName);
            
            uniqueifier++;
        }
        
        return oFile;
    }
}
