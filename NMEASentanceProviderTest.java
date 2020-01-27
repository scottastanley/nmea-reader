package com.bb.nmea;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.rawdataproviders.TestPassThroughDataProvider;
import com.bb.nmea.sentences.HDM;

import junit.framework.Assert;

public class NMEASentanceProviderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSingleSentence() {
        String origStr = 
                "$APHDM,339.7,M*3D";
        String[] expTalkerIds = {"AP"};
        String[] expTypeCodes = {"HDM"};
        Boolean[] expValidities = {Boolean.TRUE};
        Class<?>[] expClasses = {HDM.class};

        try {
            TestPassThroughDataProvider dp = new TestPassThroughDataProvider();
            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp);
            
            TestNMEAListener list = new TestNMEAListener();
            nmeaSP.addListener(list);
            
            dp.passThroughBytes(origStr.getBytes());
            nmeaSP.stop();
            
            List<NMEASentence> res = list.getNMEASentences();
            
            validateSentences(res, expTalkerIds, expTypeCodes, expValidities, expClasses);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

//    @Test
//    public void testAA() {
////        String origStr = 
////                "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
////              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
////              + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
////              + "$APHDG,339.9,,,13.0,E*01" 
////              + "$APHDM,339.9,M*33\n"
////              + "$APRSA,8.8,A*3E\n" 
////              + "$APHDT,352.7,T*30\n"
////              + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n"
////              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
////              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
////              + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
////              + "$APHDG,339.7,,,13.0,E*0F\n" 
////              + "$APHDM,339.7,M*3D\n"
////              + "$APRSA,3.7,A*3A\n" 
////              + "$APHDT,352.7,T*30\n"
////              + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n";
////        
////        String[] expTalkerIds = { "GP", "GP", "GP", "AP", "AP", "AP", "AP", "GP", "GP", "GP", "GP", "AP", "AP", "AP",
////                "AP", "GP" };
////        String[] expTypeCodes = { "VTG", "VTG", "GLL", "HDG", "HDM", "RSA", "HDT", "RMC", "VTG", "VTG", "GLL", "HDG",
////                "HDM", "RSA", "HDT", "RMC" };
////        Boolean[] expValidities = { Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
////                Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE,
////                Boolean.TRUE, Boolean.TRUE, Boolean.TRUE };
////        Class<?>[] expClasses = { HDM.class, HDM.class, HDM.class, HDM.class, HDM.class, HDM.class, HDM.class,
////                HDM.class, HDM.class, HDM.class, HDM.class, HDM.class, HDM.class, HDM.class, HDM.class, HDM.class };
//
//      String origStr = 
//      "$APHDM,339.7,M*3D";
//String[] expTalkerIds = {"AP"};
//String[] expTypeCodes = {"HDM"};
//Boolean[] expValidities = {Boolean.TRUE};
//Class<?>[] expClasses = {HDM.class};
//
//        try {
//            TestPassThroughDataProvider dp = new TestPassThroughDataProvider();
//            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp);
//            
//            TestNMEAListener list = new TestNMEAListener();
//            nmeaSP.addListener(list);
//            
//            dp.passThroughBytes(origStr.getBytes());
//            nmeaSP.stop();
//            
//            List<NMEASentence> res = list.getNMEASentences();
//            
//            validateSentences(res, expTalkerIds, expTypeCodes, expValidities, expClasses);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Assert.fail("Caught unexpected exception: " + e.getMessage());
//        }
//    }
    
    /**
     * Validate the list of NMEASentences.
     * 
     * @param res
     * @param expTalkerIds
     * @param expTypeCodes
     * @param expValidities
     * @param expClasses
     */
    private void validateSentences(final List<NMEASentence> res, final String[] expTalkerIds,
                                  final String[] expTypeCodes, final Boolean[] expValidities,
                                  final Class<?>[] expClasses) {
        Assert.assertEquals("Expected type code length doesn't match talked IDs", expTalkerIds.length, expTypeCodes.length);
        Assert.assertEquals("Expected validities length doesn't match talked IDs", expTalkerIds.length, expValidities.length);
        Assert.assertEquals("Expected classes length doesn't match talked IDs", expTalkerIds.length, expClasses.length);
        
        Assert.assertEquals("Wrong number of sentences", expTalkerIds.length, res.size());
        
        for (int n = 0; n < res.size(); n++) {
            NMEASentence sentence = res.get(n);
            Assert.assertEquals("Incorrect talker ID " + n, expTalkerIds[n], sentence.getTalkerId());
            Assert.assertEquals("Incorrect type code " + n, expTypeCodes[n], sentence.getTypeCode());
            Assert.assertEquals("Incorrect validity " + n, expValidities[n], sentence.getIsValid());
            Assert.assertTrue("Incorrect class " + n, expClasses[n].isInstance(sentence));
        }
    }
}
