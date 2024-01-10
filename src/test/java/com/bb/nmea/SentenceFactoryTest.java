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
package com.bb.nmea;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.Direction;
import com.bb.nmea.sentences.common.FAAModeIndicator;
import com.bb.nmea.sentences.common.HeadingType;
import com.bb.nmea.sentences.common.SpeedUnits;
import com.bb.nmea.sentences.common.Status;
import com.bb.nmea.sentences.common.WindReference;
import com.bb.nmea.sentences.talker.DBT;
import com.bb.nmea.sentences.talker.DPT;
import com.bb.nmea.sentences.talker.HDG;
import com.bb.nmea.sentences.talker.HDM;
import com.bb.nmea.sentences.talker.MWV;
import com.bb.nmea.sentences.talker.RSA;
import com.bb.nmea.sentences.talker.VTG;

public class SentenceFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testReadConfig() {
        StringBuffer sb = new StringBuffer();
        sb.append("sentence.hdm=com.bb.nmea.sentences.talker.HDM").append("\n");
        sb.append("sentence.rsa=com.bb.nmea.sentences.talker.RSA").append("\n");
        sb.append("mfg.zzz=com.bb.nmea.TestProprietarySentenceMfgZZZ").append("\n");
        
        InputStream cfgStrm = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        
        try {
            SentenceFactory fact = new SentenceFactory(cfgStrm);

            Assert.assertEquals("Incorrect number of manufacturers", 1, fact.numMfgIds());
            Assert.assertTrue("Missing manufacturer ZZZ", fact.containsMfgId("ZZZ"));
            Assert.assertFalse("Missing manufacturer MISSING", fact.containsMfgId("MISSING"));

            Assert.assertEquals("Incorrect number of sentences", 2, fact.numSentenceIds());
            Assert.assertTrue("Missing sentence HDM", fact.containsSentenceId("HDM"));
            Assert.assertTrue("Missing sentence RSA", fact.containsSentenceId("RSA"));
            Assert.assertFalse("Unexpected sentence MISSING", fact.containsSentenceId("MISSING"));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testReadConfigFailMissingSentenceClass() {
        StringBuffer sb = new StringBuffer();
        sb.append("sentence.hdm=com.bb.nmea.sentences.talker.MISSING").append("\n");
        sb.append("sentence.rsa=com.bb.nmea.sentences.talker.RSA").append("\n");
        
        InputStream cfgStrm = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        
        try {
            try {
                new SentenceFactory(cfgStrm);       
                Assert.fail("Should have thrown an exception");
            } catch (RuntimeException t) {
                // Ignore expected exception
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testReadConfigFailMissingMfgClass() {
        StringBuffer sb = new StringBuffer();
        sb.append("mfg.zzz=com.bb.nmea.MISSING").append("\n");
        sb.append("sentence.rsa=com.bb.nmea.sentences.talker.RSA").append("\n");
        
        InputStream cfgStrm = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        
        try {
            try {
                new SentenceFactory(cfgStrm);       
                Assert.fail("Should have thrown an exception");
            } catch (RuntimeException t) {
                // Ignore expected exception
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testHDM() {
        String rawStr = "$APHDM,344.4,M*34";

        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", HDM.class, s.getClass());
            HDM hdmS = HDM.class.cast(s);
            Assert.assertTrue("Message should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw sentence", rawStr, hdmS.getRawSentence());
            Assert.assertEquals("Incorrect tag", "APHDM", hdmS.getTag());
            Assert.assertEquals("Incorrect tag", "AP", hdmS.getTalkerId());
            Assert.assertEquals("Incorrect tag", "HDM", hdmS.getSentenceId());
            Assert.assertEquals("Incorrect tag", "34", hdmS.getChecksum());
            Assert.assertEquals("Incorrect heading", Float.valueOf(344.4F), hdmS.getHeadingDegrees());
            Assert.assertEquals("Incorrect heading type", HeadingType.MAGNETIC, hdmS.getHeadingType());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testAPHDT_InvalidChecksum() {
        String rawStr = "$APHDT,357.9,T*31";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", InvalidSentence.class, s.getClass());
            Assert.assertFalse("Message should be invalid", s.isValid());
            Assert.assertEquals("Incorrect raw sentence", rawStr, s.getRawSentence());
            Assert.assertEquals("Incorrect tag", "APHDT", s.getTag());
            Assert.assertEquals("Incorrect type", "INVALID TAG: APHDT", s.getSentenceId());
            Assert.assertEquals("Incorrect checksum", null, s.getChecksum());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDG_InvalidChecksum() {
        String rawStr = "$APHDG,257.0,,,13.0,E*08";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", InvalidSentence.class, s.getClass());
            Assert.assertFalse("Message should be invalid", s.isValid());
            Assert.assertEquals("Incorrect raw sentence", rawStr, s.getRawSentence());
            Assert.assertEquals("Incorrect tag", "APHDG", s.getTag());
            Assert.assertEquals("Incorrect type", "INVALID TAG: APHDG", s.getSentenceId());
            Assert.assertEquals("Incorrect checksum", null, s.getChecksum());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDTasUnsupported() {
        String rawStr = "$APZZZ,357.3,T*33";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            
            long preTime = System.currentTimeMillis();
            NMEASentence s = fact.getNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect class", UnsupportedSentence.class, s.getClass());
            UnsupportedSentence sUnsp = (UnsupportedSentence) s;
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APZZZ", s.getTag());
            Assert.assertEquals("Invalid type code", "UNSUPPORTED TAG: APZZZ", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "33", s.getChecksum());

            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APZZZ", sUnsp.getUnknownField(0));
            Assert.assertEquals("Invalid field 1 string", "357.3", sUnsp.getUnknownField(1));
            Assert.assertEquals("Invalid field 2 string", "T", sUnsp.getUnknownField(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPRMCasUnsupported() {
        String rawStr = "$GPZZZ,065830,A,3746.830,N,12223.251,W,0.0,000.0,291219,013.0,E,A*0E";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            
            long preTime = System.currentTimeMillis();
            NMEASentence s = fact.getNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect class", UnsupportedSentence.class, s.getClass());
            UnsupportedSentence sUnsp = (UnsupportedSentence) s;
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPZZZ", s.getTag());
            Assert.assertEquals("Invalid type code", "UNSUPPORTED TAG: GPZZZ", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "0E", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPZZZ", sUnsp.getUnknownField(0));
            Assert.assertEquals("Invalid field 1 string", "065830", sUnsp.getUnknownField(1));            
            Assert.assertEquals("Invalid field 2 string", "A", sUnsp.getUnknownField(2));
            Assert.assertEquals("Invalid field 3 string", "3746.830", sUnsp.getUnknownField(3));
            Assert.assertEquals("Invalid field 4 string", "N", sUnsp.getUnknownField(4));
            Assert.assertEquals("Invalid field 5 string", "12223.251", sUnsp.getUnknownField(5));
            Assert.assertEquals("Invalid field 6 string", "W", sUnsp.getUnknownField(6));
            Assert.assertEquals("Invalid field 7 string", "0.0", sUnsp.getUnknownField(7));
            Assert.assertEquals("Invalid field 8 string", "000.0", sUnsp.getUnknownField(8));
            Assert.assertEquals("Invalid field 9 float", "291219", sUnsp.getUnknownField(9));
            Assert.assertEquals("Invalid field 10 string", "013.0", sUnsp.getUnknownField(10));
            Assert.assertEquals("Invalid field 11 string", "E", sUnsp.getUnknownField(11));
            Assert.assertEquals("Invalid field 12 string", "A", sUnsp.getUnknownField(12));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testAPRSA_Unparsable() {
        String rawStr = "$APRSA,Z.6,A,8.2,A*37";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", UnparsableSentence.class, s.getClass());
            Assert.assertFalse("Message should be invalid", s.isValid());
            Assert.assertEquals("Incorrect raw sentence", rawStr, s.getRawSentence());
            Assert.assertEquals("Incorrect tag", "APRSA", s.getTag());
            Assert.assertEquals("Incorrect type", "UNPARSABLE TAG: APRSA", s.getSentenceId());
            Assert.assertEquals("Incorrect checksum", null, s.getChecksum());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDG_Unparsable() {
        String rawStr = "$APHDG,2ZZ.1,5.3,N,13.0,E*64";
                        
        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", UnparsableSentence.class, s.getClass());
            Assert.assertFalse("Message should be invalid", s.isValid());
            Assert.assertEquals("Incorrect raw sentence", rawStr, s.getRawSentence());
            Assert.assertEquals("Incorrect tag", "APHDG", s.getTag());
            Assert.assertEquals("Incorrect type", "UNPARSABLE TAG: APHDG", s.getSentenceId());
            Assert.assertEquals("Incorrect checksum", null, s.getChecksum());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRSA() {
        String rawStr = "$APRSA,8.6,A,8.2,A*55";

        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", RSA.class, s.getClass());
            RSA rsaS = RSA.class.cast(s);
            Assert.assertEquals("Invalid tag", "APRSA", rsaS.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", rsaS.getTalkerId());
            Assert.assertEquals("Invalid type code", "RSA", rsaS.getSentenceId());
            Assert.assertEquals("Invalid checksum", "55", rsaS.getChecksum());
            
            Assert.assertEquals("Invalid dual rudder", Boolean.TRUE, rsaS.isDualRudder());
            Assert.assertEquals("Invalid rudder angle, single rudder", Float.valueOf(8.6f), rsaS.getRudderAngle());
            Assert.assertEquals("Invalid rudder status, single rudder", Status.VALID, rsaS.getRudderStatus());
            Assert.assertEquals("Invalid rudder angle, starboard rudder", Float.valueOf(8.6f), rsaS.getStarboardRudderAngle());
            Assert.assertEquals("Invalid rudder status, starboard rudder", Status.VALID, rsaS.getStarboardRudderStatus());
            Assert.assertEquals("Invalid rudder angle, port rudder", Float.valueOf(8.2f), rsaS.getPortRudderAngle());
            Assert.assertEquals("Invalid rudder status, port rudder", Status.VALID, rsaS.getPortRudderStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testHDG() {
        String rawStr = "$APHDG,252.1,5.3,N,13.0,E*63";

        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence sentObj = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", HDG.class, sentObj.getClass());
            HDG s = HDG.class.cast(sentObj);
            Assert.assertEquals("Invalid tag", "APHDG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDG", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "63", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", Float.valueOf(252.1f), s.getHeadingDegrees());
            Assert.assertEquals("Invalid deviation degrees", Float.valueOf(5.3f), s.getDeviationDegrees());
            Assert.assertEquals("Invalid deviation direction", Direction.NORTH, s.getDeviationDirection());
            Assert.assertEquals("Invalid variation degrees", Float.valueOf(13.0f), s.getVariationDegrees());
            Assert.assertEquals("Invalid variation direction", Direction.EAST, s.getVariationDirection());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testVTG() {
        String rawStr = "$GPVTG,077.2,T,064.0,M,0.1,N,0.2,K,D*25";

        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence sentObj = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", VTG.class, sentObj.getClass());
            VTG s = VTG.class.cast(sentObj);
            Assert.assertEquals("Invalid tag", "GPVTG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "VTG", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", Float.valueOf(77.2f), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", Float.valueOf(64.0f), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid true course", Float.valueOf(0.1f), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid true course", Float.valueOf(0.2f), s.getSpeedOverGroundKmPerHr());
            Assert.assertEquals("Invalid FAA mode", FAAModeIndicator.DIFFERENTIAL_MODE, s.getFaaModeIndicator());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testDBT() {
        String rawStr = "$SDDBT,16.6,f,5.0,M,2.8,F*38";

        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence sentObj = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", DBT.class, sentObj.getClass());
            DBT s = DBT.class.cast(sentObj);
            Assert.assertEquals("Invalid tag", "SDDBT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "SD", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "DBT", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "38", s.getChecksum());
            
            Assert.assertEquals("Invalid depth in feet", Float.valueOf(16.6f), s.getWaterDepthInFeet());
            Assert.assertEquals("Invalid depth in meters", Float.valueOf(5.0f), s.getWaterDepthInMeters());
            Assert.assertEquals("Invalid depth in fathoms", Float.valueOf(2.8f), s.getWaterDepthInFathoms());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testDPT() {
        String rawStr = "$SDDPT,5.3,*7F";

        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence sentObj = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", DPT.class, sentObj.getClass());
            DPT s = DPT.class.cast(sentObj);
            Assert.assertEquals("Invalid tag", "SDDPT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "SD", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "DPT", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "7F", s.getChecksum());
            
            Assert.assertEquals("Invalid depth below transducer", Float.valueOf(5.3f), s.getDepthBelowTransducer());
            Assert.assertEquals("Invalid traansducer offset", null, s.getTransducerOffset());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testMWV() {
        String rawStr = "$ECMWV,359.0,R,0.0,N,A*34";

        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence sentObj = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", MWV.class, sentObj.getClass());
            MWV s = MWV.class.cast(sentObj);
            
            Assert.assertEquals("Invalid tag", "ECMWV", s.getTag());
            Assert.assertEquals("Invalid talked ID", "EC", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MWV", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "34", s.getChecksum());
            
            Assert.assertEquals("Invalid wind angle", Float.valueOf(359.0f), s.getWindAngle());
            Assert.assertEquals("Invalid wind reference", WindReference.RELATIVE, s.getWindReference());
            Assert.assertEquals("Invalid wind speed", Float.valueOf(0.0f), s.getWindSpeed());
            Assert.assertEquals("Invalid speed units", SpeedUnits.KNOTS, s.getSpeedUnits());
            Assert.assertEquals("Invalid status", Status.VALID, s.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
