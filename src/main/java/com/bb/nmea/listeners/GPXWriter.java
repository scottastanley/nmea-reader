package com.bb.nmea.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bb.nmea.NMEAListener;
import com.bb.nmea.NMEASentence;
import com.bb.nmea.sentences.GLL;
import com.bb.nmea.sentences.MTW;
import com.bb.nmea.sentences.MWV;
import com.bb.nmea.sentences.ZDA;

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
    private final List<WayPoint> m_statsWaypoints = new ArrayList<WayPoint>();
    private ZDA m_lastProcessedZda = null;
    
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
            } else if (sentence.getTypeCode().equals("ZDA")) {
                ZDA currZda = (ZDA) sentence;
                ZDA zdaWhenStatsLastSaved = m_stats.getZdaWhenLastSaved();
                if (zdaWhenStatsLastSaved != null) {
                    Duration dur = Duration.between(zdaWhenStatsLastSaved.getLocalDateTime(), currZda.getLocalDateTime());
                    if (dur.toMinutes() > 1) {
                        WayPoint statsWp = getStatsWaypoint(currZda, m_stats);
                        m_statsWaypoints.add(statsWp);
                        
                        m_stats = new StatsHolder();
                        m_stats.setZdaWhenLastSaved(currZda);
                    }
                } else {
                    WayPoint statsWp = getStatsWaypoint(currZda, m_stats);
                    m_statsWaypoints.add(statsWp);
                    
                    m_stats = new StatsHolder();
                    m_stats.setZdaWhenLastSaved(currZda);
                }
                
                m_lastProcessedZda = currZda;
            }
        }
    }
    
    private WayPoint getStatsWaypoint(final ZDA zda, final StatsHolder stats) {
        StringBuffer statBuff = new StringBuffer();
        if (stats.m_mtw != null) {
            statBuff.append("Water Temp: ").append(stats.m_mtw.getMeanWaterTemp()).append(" ").append(stats.m_mtw.getUnits()).append("\n");
        }
        
        if (stats.m_mwv != null) {
            statBuff.append("Wind: ")
            .append(stats.m_mwv.getWindSpeed()).append(" ").append(stats.m_mwv.getSpeedUnits())
            .append(stats.m_mwv.getWindReference()).append(" at ")
            .append(stats.m_mwv.getWindAngle()).append(" degrees").append("\n");
        }
        
        WayPoint.Builder wpBldr = WayPoint.builder();
        WayPoint p = wpBldr.name("HERE: ")
                            .lat(37.79621505737305).lon(-122.33399963378906)
                            .desc(statBuff.toString())
                            .build();
        return p;
    }

    @Override
    public void stop() {
        Builder bldr = GPX.builder();
        
        for (WayPoint wp : m_statsWaypoints) {
            bldr.addWayPoint(wp);
        }

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
        
        private ZDA m_zdaWhenLastSaved = null;
        private ZDA m_lastZda = null;
        private MTW m_mtw = null;
        private MWV m_mwv = null;
        
        ZDA getZdaWhenLastSaved() {
            return m_zdaWhenLastSaved;
        }
        
        void setZdaWhenLastSaved(final ZDA zda) {
            m_zdaWhenLastSaved = zda;
        }
        
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
