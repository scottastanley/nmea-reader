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
package com.bb.nmea.listeners;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bb.nmea.NMEAListener;
import com.bb.nmea.NMEASentence;

/**
 * A simple example {@link com.bb.nmea.NMEAListener} which accepts and counts all NMEA sentences.
 * The list of sentences observed with counts are dumped to the log when the
 * stop() method is called. Sentence types are based on the type() rather than the class, so UnsupportedSentences
 * are counted based on the underlying type.
 * 
 * @author Scott Stanley
 */
public class StatsCollector extends NMEAListener {
    private static final Logger LOG = LogManager.getLogger(StatsCollector.class);
    private static final String INVALID_TYPE = "INVALID";
    private final Map<String,Counter> m_counters = new HashMap<String,Counter>();


    public StatsCollector() {
    }

    @Override
    public void processEvent(NMEASentence sentence) {
        
        if (sentence.isValid()) {
            incrementCounter(sentence.getTypeCode());
        } else {
            incrementCounter(INVALID_TYPE);
        }
    }
    
    /**
     * Increment the counter for the specified sentence type.
     * 
     * @param type The sentence type
     */
    private void incrementCounter(final String type) {
        Counter ctr = m_counters.get(type);
        
        if (ctr == null) {
            ctr = new Counter();
            m_counters.put(type, ctr);
        }
        
        ctr.m_counter += 1;
    }

    @Override
    public void stop() {
        for (String type : m_counters.keySet()) {
            LOG.info(type + ": " + m_counters.get(type));
        }
    }
    
    /**
     * The class used to maintain counters
     */
    class Counter {
        private long m_counter = 0;
        
        public String toString() {
            return Long.toString(m_counter);
        }
    }
}
