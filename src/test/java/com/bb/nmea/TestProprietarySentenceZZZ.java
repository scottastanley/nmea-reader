package com.bb.nmea;

public class TestProprietarySentenceZZZ extends ProprietarySentence {
    private Float m_floatVal;

    public TestProprietarySentenceZZZ(String rawSentence) {
        super(rawSentence);
        
        m_floatVal = this.getFieldAsFloat(1);
    }

    @Override
    public String getManufacturerSentenceId() {
        return this.getTag().substring(4);
    }

    public Float getFloatVal() {
        return m_floatVal;
    }
}
