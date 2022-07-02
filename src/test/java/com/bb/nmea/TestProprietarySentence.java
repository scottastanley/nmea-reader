package com.bb.nmea;

public class TestProprietarySentence extends ProprietarySentence {

    TestProprietarySentence(String rawSentence) {
        super(rawSentence);
    }

    @Override
    protected String initSentenceId() {
        return this.getSentenceIdFromTag();
    }
}
