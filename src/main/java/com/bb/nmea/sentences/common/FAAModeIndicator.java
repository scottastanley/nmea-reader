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
 * FAA Mode Indicator, NMEA 2.3 and above
 * Signal integrity information required by the FAA
 * 
 * References say for sentences containing this as well as the Status 
 * indicator, this value is dominant.  For FAA Modes A and D, the Status 
 * indicator should report "A" (ie. data valid) and status should be "V" 
 * (ie. data invalid) for all other values. Not confident on this...
 * 
 * A = Autonomous mode 
 * D = Differential Mode 
 * E = Estimated (dead-reckoning) mode 
 * F = RTK Float mode 
 * M = Manual Input Mode 
 * N = Data Not Valid 
 * P = Precise (4.00 and later) 
 * R = RTK Integer mode 
 * S = Simulated Mode
 * 
 * @author Scott Stanley
 */
public enum FAAModeIndicator {
    AUTONOMOUS_MODE("A"),
    DIFFERENTIAL_MODE("D"),
    ESTIMATED("E"),
    RTK_FLOAT_MODE("F"),
    MANUAL_INPUT_MODE("M"),
    DATA_NOT_VALID("N"),
    PRECISE("P"),
    RTK_INTEGER_MODE("R"),
    SIMULATED_MODE("S");
    
    private final String m_rawValue;

    /**
     * 
     * @param rawValue
     */
    private FAAModeIndicator(final String rawValue) {
        m_rawValue = rawValue;
    }
    
    /**
     * Get the FAAModeIndicator for the provided raw value.
     * 
     * @param rawStr The raw FAA mode from an NMEA sentence
     * @return The appropriate enumeration value
     */
    public static FAAModeIndicator getFAAModeIndicator(final String rawStr) {
        // Find the appropriate mode
        FAAModeIndicator result = null;
        for (FAAModeIndicator mode : FAAModeIndicator.values()) {
            result = rawStr.equals(mode.m_rawValue) ? mode : null;
            
            if (result != null)
                break;
        }
        
        // Throw an exception if an unexpected value is observed
        if (result == null) {
            throw new RuntimeException("Unsupported raw mode: " + rawStr);
        }
        
        return result;
    }

    /**
     * @return the rawValue
     */
    public String getRawValue() {
        return m_rawValue;
    }
}
