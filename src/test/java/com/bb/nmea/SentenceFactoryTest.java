package com.bb.nmea;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.HDM;
import com.bb.nmea.sentences.InvalidSentence;

import junit.framework.Assert;

public class SentenceFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
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
            Assert.assertEquals("Incorrect tag", "HDM", hdmS.getTypeCode());
            Assert.assertEquals("Incorrect tag", "34", hdmS.getChecksum());
            Assert.assertEquals("Incorrect heading", 344.4F, hdmS.getHeadingDegrees());
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
            Assert.assertEquals("Incorrect tag", "AP", s.getTalkerId());
            Assert.assertEquals("Incorrect tag", "HDT", s.getTypeCode());
            Assert.assertEquals("Incorrect tag", "31", s.getChecksum());
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
            Assert.assertEquals("Incorrect tag", "AP", s.getTalkerId());
            Assert.assertEquals("Incorrect tag", "HDG", s.getTypeCode());
            Assert.assertEquals("Incorrect tag", "08", s.getChecksum());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

}
