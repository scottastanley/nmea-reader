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

public enum Direction {
    NORTH("N"),
    SOUTH("S"),
    EAST("E"),
    WEST("W");

    private String m_rawDirection;
    
    private Direction(final String rawDirection) {
        m_rawDirection = rawDirection;
    }
    
    /**
     * Get the raw direction for the provided raw direction.
     * 
     * @param rawDirection
     * @return The Direction value
     */
    public static Direction getDirection(final String rawDirection) {
        if (rawDirection.equals(NORTH.getRawDirection())) {
            return Direction.NORTH;
        } else if (rawDirection.equals(SOUTH.getRawDirection())) {
            return Direction.SOUTH;
        } else if (rawDirection.equals(EAST.getRawDirection())) {
            return Direction.EAST;
        } else if (rawDirection.equals(WEST.getRawDirection())) {
            return Direction.WEST;            
        } else {
            throw new RuntimeException("Unsupported raw direction: " + rawDirection);
        }
    }

    /**
     * @return the rawDirection
     */
    public String getRawDirection() {
        return m_rawDirection;
    }
}
