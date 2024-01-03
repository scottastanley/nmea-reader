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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * SentenceFactory is a factory class providing instances of the specific NMEASentence classes.  The
 * classes supported by this factory are specified in the resource file nmea.properties with the
 * format;
 * 
 * nmeaSentenceType1=classNameForSentence1
 * nmeaSentenceType2=classNameForSentence2
 * 
 * @author Scott Stanley
 */
public class SentenceFactory {
    private static final Logger LOG = LogManager.getLogger(SentenceFactory.class);

    private static final String BASE_SENTENCE_LIST_FILE = "sentences.properties";
    private static final String MFG_KEY_PREFIX = "mfg.";
    private static final String SENTENCE_KEY_PREFIX = "sentence.";
    
    private static final Map<String,Class<? extends NMEASentence>> SENTENCE_CLASSES = new HashMap<String,Class<? extends NMEASentence>>();
    static {
        // Load the included NMEASentence classes
        InputStream inStrm = SentenceFactory.class.getClassLoader().getResourceAsStream(BASE_SENTENCE_LIST_FILE);
        SentenceFactory.loadSentenceClassMap(inStrm);
    }

    /**
     * Create an instance of the SentenceFactory.
     */
    public SentenceFactory() {
    }
    
    /**
     * Create an instance of the appropriate NMEASentence type for the provided
     * raw NMEA sentence string.
     * 
     * @param rawSentence The raw NMEA sentence
     * @return An instance of the appropriate NMEASentence class
     */
    NMEASentence getNMEASentence(final String rawSentence) {
        NMEASentence instance = null;
        try {
            Boolean isValid = NMEASentence.isValidRawSentence(rawSentence);
            if (isValid) {
                String type = null;
                try {
                    type = TalkerSentence.getTypeFromTag(NMEASentence.getTag(rawSentence));                    
                } catch (Throwable t) {
                    LOG.error("Unable to identify type for raw sentence: " + rawSentence);
                    instance = new UnparsableSentence(rawSentence);
                }
                
                // If we identified the type for the sentence parse it if supported
                if (type != null) {
                    Class<? extends NMEASentence> clazz = SENTENCE_CLASSES.get(type.toLowerCase());
                    
                    if (clazz != null) {
                        try {
                            instance = clazz.getConstructor(String.class).newInstance(rawSentence);
                        } catch (InvocationTargetException e) {
                            // If an error occurs parsing the message, treat it as an unsupported
                            // except log the error
                            instance = new UnparsableSentence(rawSentence);
                            LOG.error("Failed to parse sentence: " + rawSentence);
                            LOG.debug("Parsing error", e);
                        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                            // We should never see these.  If we do simply halt processing.
                            LOG.error("Unexpected exception", e);
                        }
                    } else {
                        instance = new UnsupportedSentence(rawSentence);
                    }
                }
            } else {
                instance = new InvalidSentence(rawSentence);
            }
        } catch (Throwable t) {
            LOG.error("Failed getting NMEA sentence: " + rawSentence);
        }
        
        return instance;
    }
    
    /**
     * Create a map of NMEASentence classes based on the properties file read from the provided 
     * input stream.
     * 
     * @param inStrm An InputStream for a properties file
     * @return The map of NMEA type string -> class extends NMEASentence
     */
    private static void loadSentenceClassMap(final InputStream inStrm) {
        try {
            Properties prop = new Properties();
            prop.load(inStrm);
            
            //
            // Load the sentence classes
            //
            List<String> sentenceKeys = prop.stringPropertyNames().stream()
                                            .filter(ip -> ip.startsWith(SENTENCE_KEY_PREFIX))
                                            .collect(Collectors.toList());
            
            for (String sentenceKey : sentenceKeys) {
                String className = prop.getProperty(sentenceKey);
                String sentenceId = sentenceKey.substring(SENTENCE_KEY_PREFIX.length());
                
                try {
                    Class<?> clazz = Class.forName(className);
                    
                    if (NMEASentence.class.isAssignableFrom(clazz)) {
                        LOG.debug("Preparing NMEASentence class " + clazz.getName());
                        SENTENCE_CLASSES.put(sentenceId.toLowerCase(), clazz.asSubclass(NMEASentence.class));
                    } else {
                        LOG.error("Class " + clazz.getName() + " is not an NMEASentence");
                    }
                } catch (ClassNotFoundException e) {
                    LOG.error("Failed to instantiate sentence class " + className, e);
                }
            }
        } catch (IOException e) {
            LOG.error("Failed to load sentence classes", e);
        } finally {
            if (inStrm != null) {
                try {
                    inStrm.close();
                } catch (IOException e) {}
            }
        }
    }
   
    /**
     * Simple main method to call the factory on a set of provided NMEA sentence strings.
     * 
     * @param args The sentences
     */
    public static void main(String[] args) {
        SentenceFactory sentFact = new SentenceFactory();
        
        for (String sentenceRaw : args) {
            LOG.info("==================================================================");
            LOG.info("Processing: " + sentenceRaw);
            NMEASentence sentence = sentFact.getNMEASentence(sentenceRaw);           
            
            LOG.info("Completed:" + sentence.toString());
        }        
    }
}
