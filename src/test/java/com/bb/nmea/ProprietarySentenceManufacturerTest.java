package com.bb.nmea;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProprietarySentenceManufacturerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        String mfgId = "ZZZ";
        String rawSentence = "$PZZZCUSTID,1.0";
        String mfgSentenceId = "CUSTID";
        
        try {
            ProprietarySentenceManufacturer mfg = new TestProprietarySentenceMfgZZZ();
            
            Assert.assertEquals("Incorrect manufcturer ID",  mfgId, mfg.getManufacturerId());
            
            Assert.assertEquals("Incorrect mfg sentence ID", mfgSentenceId, mfg.getManufacturerSentenceId(rawSentence));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testGetManufacturerId() {
        String rawProprietaryStr1 = "$PFEC,idfnc,A,1*2A";
        String mfgId1 = "FEC";
        String rawProprietaryStr2 = "$PSMDCN,1*1A";
        String mfgId2 = "SMD";
        
        try {
            Assert.assertEquals("Incorrect MFG ID 1", mfgId1, 
                                ProprietarySentenceManufacturer.getManufacturerID(rawProprietaryStr1));
            Assert.assertEquals("Incorrect MFG ID 2", mfgId2, 
                                ProprietarySentenceManufacturer.getManufacturerID(rawProprietaryStr2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
