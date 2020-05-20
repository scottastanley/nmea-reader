package com.bb.nmea.listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    
    private LatestSentences m_lastSentences = new LatestSentences();
    private LocalDateTime m_lastWaypointInstant = null;

    public GPXWriter() {
        m_trckSegBldr = TrackSegment.builder();
    }

    @Override
    public void processEvent(NMEASentence sentence) {
        if (sentence.isValid()) {
            // Save the sentence as the most recent one
            m_lastSentences.setSentence(sentence);

            // Process the GLL sentences
            if (sentence.getTypeCode().equals("GLL")) {
                //
                // Save the point on the track segment
                //
                GLL gll = (GLL) sentence;
                
                WayPoint.Builder wpBldr = WayPoint.builder();
                WayPoint p = wpBldr.lat(gll.getLatitude().getDecimalLatitude(gll.getLatitudeDir()))
                                    .lon(gll.getLongitude().getDecimalLongitude(gll.getLongitudeDir()))
                                    .build();
                
                m_trckSegBldr.addPoint(p);
                
                // 
                // Write a waypoint with detailed information if it is time
                //
                LocalDateTime currInstant = getDateTimeForGLL(gll, m_lastSentences.m_zda);
                Duration dur = getDuration(currInstant, m_lastWaypointInstant);
                if ((m_lastWaypointInstant == null && currInstant != null) ||
                    (dur != null && dur.toMinutes() > 1)) {
                    WayPoint statsWp = getStatsWaypoint(m_lastSentences);
                    m_statsWaypoints.add(statsWp);
                    
                    m_lastWaypointInstant = getDateTimeForGLL(gll, m_lastSentences.m_zda);
                }
            }
        }
    }
    
    private Duration getDuration(final LocalDateTime currInstant, final LocalDateTime lastInstant) {
        Duration dur = null;
        if (lastInstant != null && currInstant != null) {
            dur = Duration.between(lastInstant, currInstant);
        }
        return dur;
    }
    
    private LocalDateTime getDateTimeForGLL(final GLL currentGll, final ZDA lastZda) {
        LocalDateTime results = null;
        if (lastZda != null) {
            results = LocalDateTime.of(lastZda.getLocalDate(), currentGll.getUtcTime().getLocalTime());
        }
        
        return results;
    }
    
    private WayPoint getStatsWaypoint(final LatestSentences stats) {
        StringBuffer statBuff = new StringBuffer();
        if (stats.m_mtw != null) {
            statBuff.append("Water Temp: ").append(stats.m_mtw.getMeanWaterTemp()).append(" ").append(stats.m_mtw.getUnits()).append("\n");
        }
        
        if (stats.m_mwv != null) {
            statBuff.append("Wind: ")
            .append(stats.m_mwv.getWindSpeed()).append(" ").append(stats.m_mwv.getSpeedUnits()).append(" at ")
            .append(stats.m_mwv.getWindAngle()).append(" degrees ").append(stats.m_mwv.getWindReference()).append("\n");
        }
        
        WayPoint.Builder wpBldr = WayPoint.builder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = getDateTimeForGLL(stats.m_gll, stats.m_zda).format(formatter);
        WayPoint p = wpBldr.name(dateTime)
                            .lat(stats.m_gll.getLatitude().getDecimalLatitude(stats.m_gll.getLatitudeDir()))
                            .lon(stats.m_gll.getLongitude().getDecimalLongitude(stats.m_gll.getLongitudeDir()))
                            .desc(statBuff.toString())
                            .build();
        return p;
    }

    @Override
    public void stop() {
        Builder bldr = GPX.builder();
        
        m_statsWaypoints.add(getStatsWaypoint(m_lastSentences));
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
    
    private static class LatestSentences {
        private MTW m_mtw = null;
        private MWV m_mwv = null;
        private ZDA m_zda = null;
        private GLL m_gll = null;
        
        void setSentence(final NMEASentence sent) {
            switch (sent.getTypeCode()) {
                case "MTW":
                    m_mtw = MTW.class.cast(sent);
                    break;
                    
                case "MWV":
                    m_mwv = MWV.class.cast(sent);
                    break;
                    
                case "ZDA":
                    m_zda = ZDA.class.cast(sent);
                    break;
                    
                case "GLL":
                    m_gll = GLL.class.cast(sent);
                    break;
                    
                default:
                    // Do nothing
            }
        }
    }
}
