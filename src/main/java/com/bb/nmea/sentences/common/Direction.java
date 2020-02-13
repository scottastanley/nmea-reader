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
