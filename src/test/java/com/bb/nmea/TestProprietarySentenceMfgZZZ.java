package com.bb.nmea;

/**
 * A test proprietary sentence manufacturer. Follows the format,
 * 
 * $PZZZCUSTID,1.0
 * 
 */
public class TestProprietarySentenceMfgZZZ extends ProprietarySentenceManufacturer {

    public TestProprietarySentenceMfgZZZ() {
        super("ZZZ");
    }

    @Override
    public String getManufacturerSentenceId(String rawSentence) {
        String mfgSentenceId = rawSentence.substring(5, rawSentence.indexOf(","));
        return mfgSentenceId;
    }

}
