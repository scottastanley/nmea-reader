package com.bb.nmea;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;

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
    private static final Logger LOG = Logger.getLogger(SentenceFactory.class);

    private static final String BASE_SENTENCE_LIST_FILE = "sentences.properties";
    private static final Map<String,Class<? extends NMEASentence>> SENTENCE_CLASSES = new HashMap<String,Class<? extends NMEASentence>>();
    static {
        // Load the included NMEASentence classes
        InputStream inStrm = SentenceFactory.class.getClassLoader().getResourceAsStream(BASE_SENTENCE_LIST_FILE);
        SENTENCE_CLASSES.putAll(SentenceFactory.loadSentenceClassMap(inStrm));
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
        String type = getTypeFromTag(getTag(rawSentence));
        Class<? extends NMEASentence> clazz = SENTENCE_CLASSES.get(type.toLowerCase());
        
        NMEASentence instance = null;
        try {
            instance = clazz.getConstructor(String.class).newInstance(rawSentence);
            Assert.assertNotNull("Null instance", instance);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            LOG.error("Failed to instantiate sentence: " + rawSentence, e);
        }
        
        return instance;
    }
    
    /**
     * Parse the tag from the provided raw sentence string.
     * 
     * @param rawSentence The raw NMEA sentence
     * @return The tag value
     */
    static String getTag(final String rawSentence) {
        return rawSentence.substring(1, rawSentence.indexOf(","));
    }
    
    /**
     * Parse the NMEA sentence type from the provided tag value.
     * 
     * @param tag The NMEA tag value
     * @return The NMEA type field
     */
    static String getTypeFromTag(final String tag) {
        return tag.substring(2, 5);
    }
    
    /**
     * Create a map of NMEASentence classes based on the properties file read from the provided 
     * input stream.
     * 
     * @param inStrm An InputStream for a properties file
     * @return The map of NMEA type string -> class extends NMEASentence
     */
    private static Map<String,Class<? extends NMEASentence>> loadSentenceClassMap(final InputStream inStrm) {
        Map<String,Class<? extends NMEASentence>> classMap = new HashMap<String,Class<? extends NMEASentence>>();
        
        try {
            Properties prop = new Properties();
            prop.load(inStrm);
            
            for (String sentenceType : prop.stringPropertyNames()) {
                String className = prop.getProperty(sentenceType);
                
                try {
                    Class<?> clazz = Class.forName(className);
                    
                    if (NMEASentence.class.isAssignableFrom(clazz)) {
                        LOG.debug("Preparing NMEASentence class " + clazz.getName());
                        classMap.put(sentenceType.toLowerCase(), clazz.asSubclass(NMEASentence.class));
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
        
        return classMap;
    }
}
