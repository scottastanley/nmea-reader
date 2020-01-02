package com.bb.nmea_reader;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class Main implements NMEAListener {
    private static HashSet<String> m_uniqueSentenceIds = new HashSet<String>();
    private static Integer m_numSentencesRead = 0;

    public Main() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        NMEAReader nmeaRdr = new NMEAReader();
        try {
            nmeaRdr.addListener(new Main());
            nmeaRdr.start();
        } catch (NMEAReaderException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newSentence(String str) {
        if (str.startsWith("$") && 
                str.charAt(str.length()-3) == '*') {
            String sentence = str.substring(1, 6);
            m_uniqueSentenceIds.add(sentence);
            
            m_numSentencesRead+=1;
        }
        
        if ((m_numSentencesRead % 50) == 0) {
            System.out.println(m_uniqueSentenceIds);
        }
    }
}
