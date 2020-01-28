package com.bb.nmea;

import java.util.regex.Pattern;

/**
 * The abstract base for all NMEA sentences.
 * 
 * @author Scott Stanley
 */
public abstract class NMEASentence {
    final static String SENTENCE_PATTERN = "^[\\!\\$].*\\*[0-9A-F]{2}$";
    
    final private String m_rawSentence;
    final private long m_collectedTimestamp;
    private Boolean m_isValid;
    private String m_checksum;
    private String m_tag;
    private String[] m_fields;

    /**
     * Create a new NMEASentence initializing the common details.
     * 
     * @param rawSentence
     */
    public NMEASentence(final String rawSentence) {
        m_collectedTimestamp = System.currentTimeMillis();
        m_rawSentence = rawSentence;
        
        // Validate the sentence pattern
        m_isValid = true;
        m_isValid = m_isValid && Pattern.matches(SENTENCE_PATTERN, rawSentence);
        
        // Extract and validate the checksum
        if (m_isValid) {
            m_checksum = rawSentence.substring(rawSentence.length() - 2, rawSentence.length());
            
            String calcChecksum = calculateChecksum(rawSentence);
            m_isValid = m_isValid && m_checksum.equals(calcChecksum);
            
            // Extract the tag
            m_tag = SentenceFactory.getTag(rawSentence);
            m_isValid = m_isValid && m_tag.length() == 5;
        }
        
        // Parse out the raw fields
        if (m_isValid) {
            m_fields = rawSentence.substring(1, rawSentence.length() - 3).split(",");
        }
    }
    
    /**
     * Calculate the checksum of this sentence based on the content of the
     * raw sentence string.
     * 
     * @param rawSentence
     * @return The calculated checksum
     */
    private String calculateChecksum(final String rawSentence) {
        // Extract the relevant portion of the string and remove all special characters
        String chkSumStr = rawSentence.substring(1, rawSentence.length() - 3);
        chkSumStr = chkSumStr.replace("$", "").replace("I", "").replace("*", "");
        
        byte calcChecksum = 0;
        for (byte b : chkSumStr.getBytes()) {
            calcChecksum ^= b;
        }
        String calcHexChecksum = Integer.toHexString(calcChecksum).toUpperCase();
        
        if (calcHexChecksum.length() == 1) {
            calcHexChecksum = "0" + calcHexChecksum;
        }
        
        return calcHexChecksum;
    }
    
    /**
     * Get the original raw sentence received.
     * 
     * @return The original raw sentence
     */
    public String getRawSentence() {
        return m_rawSentence;
    }
    
    /**
     * Get the system time when this NMEA sentence was received by the
     * system.
     * 
     * @return The timestamp from when the sentence was collected
     */
    public long getCollectedTimestamp() {
        return m_collectedTimestamp;
    }

    /**
     * Get the tag for this NMEA sentence.
     * 
     * @return The tag value
     */
    public String getTag() {
        return m_tag;
    }
    
    /**
     * Get the talked ID of this NMEA sentence.
     * 
     * @return The talker ID
     */
    public String getTalkerId() {
        return m_isValid ? m_tag.substring(0, 2) : null;
    }
    
    /**
     * Get the type code for this NMEA sentence.
     * 
     * @return The type code
     */
    public String getTypeCode() {
        return m_isValid ? SentenceFactory.getTypeFromTag(m_tag) : null;
    }

    /**
     * Get the checksum provided in the original NMEA sentence.
     * 
     * @return The checksum from the original sentence
     */
    public String getChecksum() {
        return m_checksum;
    }
    
    /**
     * Does this NMEA sentence pass the base validation.  This validation insures
     * the sentence has the correct string pattern, that the tag has the proper length and
     * that the checksum of the data in the sentence can be validated against the provided
     * checksum.
     * 
     * @return True of this sentence has passed validation
     */
    public Boolean getIsValid() {
        return m_isValid;
    }

    /**
     * Get the value of the specified field index as a string.
     * 
     * @param index The index of the field to retrieve
     * @return The field value
     */
    protected String getField(final int index) {
        return m_fields != null ? m_fields[index] : null;
    }
    
    /**
     * Get the value of the specified field index as a floating point value.
     * 
     * @param index The index of the field to retrieve
     * @return The field value
     */
    protected Float getFieldAsFloat(final int index) {
        return m_fields != null ? Float.valueOf(getField(index)) : null;
    }

    /**
     * Get the value of the specified field index as an integer value.
     * 
     * @param index The index of the field to retrieve
     * @return The field value
     */
    protected Integer getFieldAsInteger(final int index) {
        return m_fields != null ? Integer.valueOf(getField(index)) : null;
    }
    
    /**
     * Get the value of the specified field index as a LatLong instance.
     * 
     * @param index The index of the field to retrieve
     * @return The field value
     */
    protected LatLong getFieldAsLatLong(final int index) {
        return m_fields != null ? new LatLong(getField(index)) : null;
    }
}
