package com.bb.nmea;

import java.util.ArrayList;
import java.util.List;

public class TestNMEAListener implements NMEAListener {
    List<NMEASentence> m_rcvdSentences = new ArrayList<NMEASentence>();

    public TestNMEAListener() {
    }

    @Override
    public void processEvent(NMEASentence sentence) {
        m_rcvdSentences.add(sentence);
    }
    
    public List<NMEASentence> getNMEASentences() {
        return m_rcvdSentences;
    }
}
