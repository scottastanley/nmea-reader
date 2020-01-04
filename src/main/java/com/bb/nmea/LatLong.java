package com.bb.nmea;

public class LatLong {
    final Integer m_degrees;
    final Float m_minutes;

    public LatLong(final String str) {
        m_degrees = Integer.valueOf(str.substring(0, str.indexOf(".") - 2));
        m_minutes = Float.valueOf(str.substring(str.indexOf(".") - 2, str.length()));
    }
    
    /**
     * Get the whole degrees
     * 
     * @return 
     */
    public Integer getDegrees() {
        return m_degrees;
    }
    
    /**
     * Get the fractional minutes.
     * 
     * @return 
     */
    public Float getMinutes() {
        return m_minutes;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (LatLong.class.isInstance(obj)) {
            LatLong other = LatLong.class.cast(obj);
            
            return m_degrees == other.m_degrees && 
                    m_minutes == other.getMinutes();
        } else {
            return false;
        }
    }
}
