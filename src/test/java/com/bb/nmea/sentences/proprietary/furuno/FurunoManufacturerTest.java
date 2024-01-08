package com.bb.nmea.sentences.proprietary.furuno;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FurunoManufacturerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGPINT() {
        String sentence = "$PFEC,GPint,ast01*13";
        
        try {
            FurunoManufacturer idEx = new FurunoManufacturer();
            String result = idEx.getManufacturerSentenceId(sentence);
            
            Assert.assertEquals("Incorrect sentence ID", "GPint", result);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail("Unexpected exception: " + ex.getMessage());
        }
    }

    @Test
    public void testIDFNC() {
        String sentence = "$PFEC,idfnc,R,*08";
        
        try {
            FurunoManufacturer idEx = new FurunoManufacturer();
            String result = idEx.getManufacturerSentenceId(sentence);
            
            Assert.assertEquals("Incorrect sentence ID", "idfnc", result);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail("Unexpected exception: " + ex.getMessage());
        }
    }
    
    

}
