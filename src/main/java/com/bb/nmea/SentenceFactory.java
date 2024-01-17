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
    
    private final Map<String,ProprietarySentenceManufacturer> m_proprietaryMfgs = new HashMap<String,ProprietarySentenceManufacturer>();
    private final Map<String,Class<? extends NMEASentence>> m_sentenceClasses = new HashMap<String,Class<? extends NMEASentence>>();

    /**
     * Create an instance of the SentenceFactory.
     */
    SentenceFactory() {
        // Load the included NMEASentence classes
        InputStream inStrm = SentenceFactory.class.getClassLoader().getResourceAsStream(BASE_SENTENCE_LIST_FILE);
        loadSentenceConfig(inStrm);
    }
    
    /**
     * Create an instance of the SentenceFactory.
     */
    SentenceFactory(final InputStream inStrm) {
        loadSentenceConfig(inStrm);
    }
    
    /**
     * Get the number of manufacturer IDs configured
     * 
     * @return The number of manufacturer IDs
     */
    public int numMfgIds() {
        return m_proprietaryMfgs.size();
    }
    
    /**
     * Get the number of sentence IDs configured
     * 
     * @return The number of sentence IDs
     */
    public int numSentenceIds() {
        return m_sentenceClasses.size();
    }
    
    /**
     * Does this sentence factory contain the given manufacturer ID
     * 
     * @param mfgId The manufacturer ID
     * @return True if we have this manufacturer ID
     */
    public boolean containsMfgId(final String mfgId) {
        return m_proprietaryMfgs.containsKey(mfgId.toLowerCase());
    }
    
    /**
     * Does this sentence factory contain the given sentence ID
     * 
     * @param sentenceId The sentence ID
     * @return True if we have this sentence ID
     */
    public boolean containsSentenceId(final String sentenceId) {
        return m_sentenceClasses.containsKey(sentenceId.toLowerCase());
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
                if (ProprietarySentence.isProprietarySentence(rawSentence)) {
                    String mfgId = ProprietarySentenceManufacturer.getManufacturerID(rawSentence);
                    ProprietarySentenceManufacturer mfg = m_proprietaryMfgs.get(mfgId.toLowerCase());
                    
                    if (mfg != null) {
                        String proprietarySentenceId = mfg.getManufacturerSentenceId(rawSentence);
                        String sentenceId = ProprietarySentence.getProprietarySentenceId(mfgId, proprietarySentenceId);
                        
                        instance = parseSentence(sentenceId, rawSentence);                        
                    } else {
                        instance = new UnsupportedManufacturer(rawSentence);
                    }
                } else {
                    instance = getTalkerSentence(rawSentence);                    
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
     * Construct an NMEASentance for a talker sentence string
     * 
     * @param rawSentence The raw sentence string
     * @return The NMEASentence
     */
    private NMEASentence getTalkerSentence(final String rawSentence) {
        String type = null;
        try {
            type = TalkerSentence.getTypeFromTag(NMEASentence.getTag(rawSentence));                    
        } catch (Throwable t) {
            LOG.error("Unable to identify type for raw sentence: " + rawSentence);
            return new UnparsableSentence(rawSentence);
        }
        
        // If we identified the type for the sentence parse it if supported
        return parseSentence(type, rawSentence);
    }
    
    /**
     * Get the registered class for the given sentence type and parse the provided 
     * raw sentence as this type.
     * 
     * @param type The sentence type
     * @param rawSentence The raw sentence
     * @return The NMEASentence
     */
    private NMEASentence parseSentence(final String type, final String rawSentence) {
        NMEASentence instance = null;

        if (type != null) {
            Class<? extends NMEASentence> clazz = m_sentenceClasses.get(type.toLowerCase());
            
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
        
        return instance;
    }
    
    /**
     * Create a map of NMEASentence classes based on the properties file read from the provided 
     * input stream.
     * 
     * @param inStrm An InputStream for a properties file
     * @return The map of NMEA type string -> class extends NMEASentence
     */
    private void loadSentenceConfig(final InputStream inStrm) {
        try {
            Properties prop = new Properties();
            prop.load(inStrm);
            
            //
            // Load the proprietary sentence manufacturers
            //
            List<String> mfgKeys = prop.stringPropertyNames().stream()
                .filter(ip -> ip.startsWith(MFG_KEY_PREFIX))
                .collect(Collectors.toList());

            for (String mfgKey : mfgKeys) {
                String className = prop.getProperty(mfgKey);

                try {
                    Class<?> clazz = Class.forName(className);
                    
                    if (ProprietarySentenceManufacturer.class.isAssignableFrom(clazz)) {
                        LOG.debug("Preparing ProprietarySentenceManufacturer class " + clazz.getName());
                        
                        Object newInst = clazz.getConstructor().newInstance();
                        ProprietarySentenceManufacturer instance = ProprietarySentenceManufacturer.class.cast(newInst);
                        m_proprietaryMfgs.put(instance.getManufacturerId().toLowerCase(), instance);
                    } else {
                        LOG.error("Class " + clazz.getName() + " is not an NMEASentence");
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
                         IllegalArgumentException | InvocationTargetException | NoSuchMethodException | 
                         SecurityException e) {
                    LOG.error("Failed to instantiate manufacturer instance " + className, e);
                    throw new RuntimeException("Failed to instantiate manufacturer instance " + className,
                                               e);
                }
            }            
            

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
                        m_sentenceClasses.put(sentenceId.toLowerCase(), clazz.asSubclass(NMEASentence.class));
                    } else {
                        LOG.error("Class " + clazz.getName() + " is not an NMEASentence");
                    }
                } catch (ClassNotFoundException e) {
                    LOG.error("Failed to instantiate sentence class " + className, e);
                    throw new RuntimeException("Failed to instantiate sentence class " + className,
                                               e);
                }
            }
        } catch (IOException e) {
            LOG.error("Failed to load sentence classes", e);
            throw new RuntimeException("Failed to load sentence classes", e);
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
