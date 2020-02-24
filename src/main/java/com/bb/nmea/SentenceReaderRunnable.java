/*
 * Copyright 2020 Scott Alan Stanley
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bb.nmea;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A runnable object which reads data provided by the {@link com.bb.nmea.DataProvider}s.  Each 
 * data provider is provided with a piped output stream to which all piped data is written.
 * The corresponding piped input stream is provided to an instance of SentenceReaderRunnable
 * and started running in a thread.  All data provided by the data provider is read from the 
 * piped input stream a single line at a time and the provided raw NMEA sentence is
 * parsed to create an instance of {@link com.bb.nmea.NMEASentence}.  These sentences are
 * then passed to the {@link com.bb.nmea.NMEASentenceProvider} to be passed to each of the
 * listeners which have been registered.
 * 
 * @author Scott Stanley
 *
 */
public class SentenceReaderRunnable implements Runnable {
    private static final Logger LOG = LogManager.getLogger(SentenceReaderRunnable.class);
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
