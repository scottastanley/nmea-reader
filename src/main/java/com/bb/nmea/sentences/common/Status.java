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
package com.bb.nmea.sentences.common;

/**
 * An enumeration representing a general Status field from an NMEA sentence.  There
 * seems to be a lot of variability in how status fields are defined, however for
 * many it seems to be a raw value of A=VALID and V=INVALID.  However, in some
 * documentation the only thing specific is that A=VALID.  The static method
 * for returning Status values operates on the latter definition, a raw value of A
 * is valid and all others are invalid.
 * 
 * Be very careful when using this that the definition is as desired!!
 * 
 * @author Scott Stanley
 */
public enum Status {
    VALID("A"),
    INVALID("V");
    
    private String m_rawStatus;
    
    /**
     * Construct a status
     * 
     * @param rawStatus
     */
    private Status(final String rawStatus) {
        m_rawStatus = rawStatus;
    }
    
    /**
     * Get the Status enumeration value based on the provided
     * raw value.
     * 
     * @param rawStatus The raw status string
     * @return The Status enumeration value
     */
    public static Status getStatus(final String rawStatus) {
        if (rawStatus.equals(Status.VALID.m_rawStatus)) {
            return Status.VALID;
        } else {
            return Status.INVALID;
        }
    }
    
    /**
     * Get the raw status value for this Status.
     * 
     * @return The string raw representation
     */
    public String getRawStatus() {
        return m_rawStatus;
    }
}
