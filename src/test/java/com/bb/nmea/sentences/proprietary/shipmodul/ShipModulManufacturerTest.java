package com.bb.nmea.sentences.proprietary.shipmodul;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ShipModulManufacturerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSMDCN() {
        String sentence = "$PSMDCN,1*1A";
        
        try {
        	ShipModulManufacturer idEx = new ShipModulManufacturer();
            String result = idEx.getManufacturerSentenceId(sentence);
            
            Assert.assertEquals("Incorrect sentence ID", "CN", result);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail("Unexpected exception: " + ex.getMessage());
        }
    }
}
