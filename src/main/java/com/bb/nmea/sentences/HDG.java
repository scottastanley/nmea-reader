package com.bb.nmea.sentences;

import com.bb.nmea.NMEASentence;
import com.bb.nmea.sentences.common.Direction;

/**
 * HDG : Heading, Deviation and Variation
 * 
 * @author Scott Stanley
 */
public class HDG 
        extends NMEASentence {
    private final Float m_headingDegrees;
    private final Float m_deviationDegrees;
    private final Direction m_deviationDirection;
    private final Float m_variationDegrees;
    private final Direction m_variationDirection;

    public HDG(String rawSentence) {
        super(rawSentence);
        
        m_headingDegrees = this.getFieldAsFloat(1);
        m_deviationDegrees = this.getFieldAsFloat(2);
        m_deviationDirection = this.getFieldAsDirection(3);
        m_variationDegrees = this.getFieldAsFloat(4);
        m_variationDirection = this.getFieldAsDirection(5);
    }

    /**
     * @return the headingDegrees
     */
    public Float getHeadingDegrees() {
        return m_headingDegrees;
    }

    /**
     * @return the deviationDegrees
     */
    public Float getDeviationDegrees() {
        return m_deviationDegrees;
    }

    /**
     * @return the deviationDirection
     */
    public Direction getDeviationDirection() {
        return m_deviationDirection;
    }

    /**
     * @return the variationDegrees
     */
    public Float getVariationDegrees() {
        return m_variationDegrees;
    }

    /**
     * @return the variationDirection
     */
    public Direction getVariationDirection() {
        return m_variationDirection;
    }
}
