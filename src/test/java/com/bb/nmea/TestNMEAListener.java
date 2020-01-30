package com.bb.nmea;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

public class TestNMEAListener implements NMEAListener {
    private static final Logger LOG = Logger.getLogger(TestNMEAListener.class);
    List<NMEASentence> m_rcvdSentences = Collections.synchronizedList(new ArrayList<NMEASentence>());

    public TestNMEAListener() {
    }

    @Override
    public void processEvent(NMEASentence sentence) {
        m_rcvdSentences.add(sentence);
        LOG.debug("Rcvd: " + sentence.getRawSentence() + " " + m_rcvdSentences.size());
    }
    
    public List<NMEASentence> getNMEASentences() {
        return m_rcvdSentences;
    }
}
