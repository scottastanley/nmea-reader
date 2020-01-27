package com.bb.nmea;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

public class SentenceReaderRunnable implements Runnable {
    private static final Logger LOG = Logger.getLogger(SentenceReaderRunnable.class);
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
                LOG.debug("HERE: " + sentenceStr);
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
