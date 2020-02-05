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
    private String m_checksum;
    private String m_tag;
    private String[] m_fields;
    private Boolean m_isValid = true;

    /**
     * Create a new NMEASentence initializing the common details.
     * 
     * @param rawSentence
     */
    public NMEASentence(final String rawSentence) {
        m_collectedTimestamp = System.currentTimeMillis();
        m_rawSentence = rawSentence;
        m_checksum = rawSentence.substring(rawSentence.length() - 2, rawSentence.length());
        m_tag = NMEASentence.getTag(rawSentence);
        m_fields = rawSentence.substring(1, rawSentence.length() - 3).split(",");
    }
    
    /**
     * Calculate the checksum of this sentence based on the content of the
     * raw sentence string.
     * 
     * @param rawSentence
     * @return The calculated checksum
     */
    private static String calculateChecksum(final String rawSentence) {
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
        return m_tag.substring(0, 2);
    }
    
    /**
     * Get the type code for this NMEA sentence.
     * 
     * @return The type code
     */
    public String getTypeCode() {
        return NMEASentence.getTypeFromTag(m_tag);
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
     * Get the value of the specified field index as a string.  The indexes
     * of fields in the sentence begin as 0 with the tag field.
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
     * Evaluate whether the provided raw sentence is a valid NMEA sentence.  A sentence
     * is considered valid if it meets the following criteria;
     * 
     * 1) The raw sentence matches the regular expression in the SENTENCE_PATTERN variable
     * 2) The checksum in the sentence matches one calculated based on the provided sentence content
     * 
     * @param rawSentence The raw NMEA sentence
     * @return True if the sentence is valid
     */
    static Boolean isValidRawSentence(final String rawSentence) {
       Boolean isValid = Pattern.matches(SENTENCE_PATTERN, rawSentence);
        
        // Extract and validate the checksum
        if (isValid) {
            String origchecksum = rawSentence.substring(rawSentence.length() - 2, rawSentence.length());
            String calcChecksum = calculateChecksum(rawSentence);
            isValid = isValid && origchecksum.equals(calcChecksum);
        }
        
        return isValid;
    }

    /**
     * Is this NMEASentence valid?
     * 
     * @return true if this sentence is valid
     */
    public Boolean isValid() {
        return m_isValid;
    }

    /**
     * Flag the sentence as invalid.
     */
    protected void setInValid() {
        m_isValid = Boolean.FALSE;
    }
}
