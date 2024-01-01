package com.bb.nmea.listeners;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bb.nmea.NMEAListener;
import com.bb.nmea.NMEASentence;

public class InvalidCollector extends NMEAListener {
    private static final Logger LOG = LogManager.getLogger(InvalidCollector.class);
    private final List<String> m_invalidSentences = new ArrayList<String>();

    @Override
    public void processEvent(NMEASentence sentence) {
        if (! sentence.isValid()) {
            m_invalidSentences.add(sentence.getRawSentence());
        }
    }

    @Override
    public void stop() {
        for (String sentence : m_invalidSentences) {
            LOG.info(sentence);
        }
    }

}
