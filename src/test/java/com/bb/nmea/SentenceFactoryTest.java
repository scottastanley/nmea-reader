package com.bb.nmea;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.HDM;

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

}
