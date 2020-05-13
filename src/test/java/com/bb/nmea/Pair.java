package com.bb.nmea;

public class Pair<K extends Object,V extends Object> {
    private final K m_key;
    private final V m_value;
    
    public Pair(final K key, final V value) {
        m_key = key;
        m_value = value;
    }

    /**
     * @return the key
     */
    public K getKey() {
        return m_key;
    }

    /**
     * @return the value
     */
    public V getValue() {
        return m_value;
    }
}
