package com.bb.nmea.sentences;

import com.bb.nmea.HeadingType;
import com.bb.nmea.NMEASentence;

/**
 * HDM: Magnetic Heading
 * 
 * @author Scott A Stanley
 */
public class HDM extends NMEASentence {
    private final Float m_headingDegrees;
    private final HeadingType m_headingType;

    public HDM(String rawSentence) {
        super(rawSentence);
        
        m_headingDegrees = this.getFieldAsFloat(1);
        m_headingType = HeadingType.getHeadingType(this.getField(2));
    }

    /**
     * Get the heading in degrees.
     * 
     * @return The heading in degrees
     */
    public Float getHeadingDegrees() {
        return m_headingDegrees;
    }

    /**
     * Get the heading type, magnetic or true.
     * 
     * @return The heading type
     */
    public HeadingType getHeadingType() {
        return m_headingType;
    }
}
