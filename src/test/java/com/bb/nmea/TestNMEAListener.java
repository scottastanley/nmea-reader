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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestNMEAListener extends NMEAListener {
    private static final Logger LOG = LogManager.getLogger(TestNMEAListener.class);
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

    @Override
    public void stop() {        
    }
}
