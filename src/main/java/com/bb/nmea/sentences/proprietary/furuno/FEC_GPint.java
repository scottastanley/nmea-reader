/*
 * Copyright 2023 Scott Alan Stanley
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
package com.bb.nmea.sentences.proprietary.furuno;

import java.util.ArrayList;
import java.util.List;

/**
 * Furuno Proprietary Sentence: GPint
 * 
 * Request/set log output intervals.
 * 
 * Reference: Furuno_GT8031_Protocol_Specification_Rev1.pdf
 * 
 * @author Scott Stanley
 */
public class FEC_GPint extends AbstractFurunoSentence {
    private List<String> m_logOutputIntervals = new ArrayList<String>();
    
    public FEC_GPint(final String rawSentence) {
        super(rawSentence);
        
        for (int n = 2; n <= getNumFields() - 1; n++) {
            m_logOutputIntervals.add(this.getField(n));
        }
    }
    
    /**
     * Get the log output intervals requested.
     * 
     * @return The list of log output intervals requested.
     */
    public List<String> getLogOutputIntervals() {
        return m_logOutputIntervals;
    }
}
