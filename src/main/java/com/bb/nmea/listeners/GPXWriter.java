package com.bb.nmea.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.bb.nmea.NMEAListener;
import com.bb.nmea.NMEASentence;
import com.bb.nmea.sentences.GLL;
import com.bb.nmea.sentences.MTW;
import com.bb.nmea.sentences.MWV;

import io.jenetics.jpx.GPX;
import io.jenetics.jpx.GPX.Builder;
import io.jenetics.jpx.Track;
import io.jenetics.jpx.TrackSegment;
import io.jenetics.jpx.WayPoint;

/**
 * 
 * https://github.com/jenetics/jpx
 * 
 * @author sstanley
 */
public class GPXWriter extends NMEAListener {
    private final TrackSegment.Builder m_trckSegBldr;
    private StatsHolder m_stats = new StatsHolder();

    public GPXWriter() {
        m_trckSegBldr = TrackSegment.builder();
    }

    @Override
    public void processEvent(NMEASentence sentence) {
        if (sentence.isValid()) {
            if (sentence.getTypeCode().equals("GLL")) {
                GLL gll = (GLL) sentence;
                
                WayPoint.Builder wpBldr = WayPoint.builder();
                WayPoint p = wpBldr.lat(gll.getLatitude().getDecimalLatitude(gll.getLatitudeDir()))
                                    .lon(gll.getLongitude().getDecimalLongitude(gll.getLongitudeDir()))
                                    .build();
                
                m_trckSegBldr.addPoint(p);
            } else if (StatsHolder.m_statsSentenceTypes.contains(sentence.getTypeCode())) {
                m_stats.setSentence(sentence);
            } 
        }
    }

    @Override
    public void stop() {
        Builder bldr = GPX.builder();

        Track.Builder trackBldr = Track.builder();
        trackBldr.addSegment(m_trckSegBldr.build());

        bldr.addTrack(trackBldr.build());
        GPX gpx = bldr.build();
        
        
        FileOutputStream oStrm = null;
        try {
            oStrm = new FileOutputStream(new File("testFile.gpx"));
            GPX.write(gpx, oStrm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oStrm != null)
                try {
                    oStrm.close();
                } catch (IOException e) {}
        }
    }
    
    private static class StatsHolder {
        static final Set<String> m_statsSentenceTypes = new HashSet<String>();
        static {
            m_statsSentenceTypes.addAll(Arrays.asList("MTW", "MWV"));
        }
        
        private MTW m_mtw = null;
        private MWV m_mwv = null;
        
        void setSentence(final NMEASentence sent) {
            switch (sent.getTypeCode()) {
                case "MTW":
                    m_mtw = MTW.class.cast(sent);
                    break;
                    
                case "MWV":
                    m_mwv = MWV.class.cast(sent);
                    break;
                    
                default:
                    throw new RuntimeException("Unsupported sentence type: " + sent.getTypeCode());
            }
        }
        
    }
}
