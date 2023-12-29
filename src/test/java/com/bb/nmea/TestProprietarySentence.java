package com.bb.nmea;

public class TestProprietarySentence extends ProprietarySentence {
    private static String m_manufSentId;

    TestProprietarySentence(String rawSentence) {
        super(rawSentence);
    }
    
    static protected void setManufacturerSentenceId(final String manufSentId) {
        m_manufSentId = manufSentId;
    }
    
    static protected void reset() {
        m_manufSentId = null;
    }

    @Override
    public String getManufacturerSentenceId() {
        return m_manufSentId;
    }
}
