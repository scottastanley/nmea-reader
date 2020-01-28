package com.bb.nmea;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class SentenceReaderRunnable implements Runnable {
    private static final Logger LOG = Logger.getLogger(SentenceReaderRunnable.class);
    private SentenceFactory m_sentFactory = new SentenceFactory();
    private BufferedReader m_sentRdr;
    private final NMEASentenceProvider m_nmeaSentProv;

    public SentenceReaderRunnable(final BufferedReader sentRdr, final NMEASentenceProvider nmeaSentProv) {
        m_sentRdr = sentRdr;
        m_nmeaSentProv = nmeaSentProv;
    }

    @Override
    public void run() {
        LOG.info("Starting");
        String sentenceStr = null;
        try {
            while((sentenceStr = m_sentRdr.readLine()) != null) {
                LOG.debug("New sentence: " + sentenceStr);
                NMEASentence sentence = m_sentFactory.getNMEASentence(sentenceStr);
                m_nmeaSentProv.newEvent(sentence);
            }
        } catch (IOException e) {
            LOG.error("Failed reading NMEA sentence", e);
        } finally {
            if (m_sentRdr != null) {
                try {
                    m_sentRdr.close();
                    m_sentRdr = null;
                } catch (IOException e) {}
            }
        }
        
        LOG.info("Stopped");
    }
}
