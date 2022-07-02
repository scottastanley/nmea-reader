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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.dataproviders.TestPassThroughDataProvider;
import com.bb.nmea.sentences.DBT;
import com.bb.nmea.sentences.DPT;
import com.bb.nmea.sentences.GLL;
import com.bb.nmea.sentences.HDG;
import com.bb.nmea.sentences.HDM;
import com.bb.nmea.sentences.MTW;
import com.bb.nmea.sentences.MWV;
import com.bb.nmea.sentences.RSA;
import com.bb.nmea.sentences.VTG;
import com.bb.nmea.sentences.ZDA;

public class NMEASentanceProviderTest {
////  "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
////+ "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
////+ "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
////+ "$APHDG,339.9,,,13.0,E*01" 
////+ "$APHDM,339.9,M*33\n"
////+ "$APRSA,8.8,A*3E\n" 
////+ "$APHDT,352.7,T*30\n"
////+ "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n"
////+ "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
////+ "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
////+ "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
////+ "$APHDG,339.7,,,13.0,E*0F\n" 
////+ "$APHDM,339.7,M*3D\n"
////+ "$APRSA,3.7,A*3A\n" 
////+ "$APHDT,352.7,T*30\n"
////+ "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n";


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testSingleSentenceSingleProvider() {
        String[] origStr = {
                "$APHDM,339.7,M*3D"
            };
        byte[][] origBytes = getBytes(origStr);
        
        ExpectedResults expRes = new ExpectedResults();
        expRes.addResult(new ExpectedSentence(origStr[0], "AP", "HDM", HDM.class));
        
        try {
            TestPassThroughDataProvider dp = new TestPassThroughDataProvider(origBytes);
            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp);
            
            TestNMEAListener list = new TestNMEAListener();
            nmeaSP.addListener(list);
            nmeaSP.start();
            nmeaSP.stop();
            
            List<NMEASentence> res = list.getNMEASentences();
            validateSentences(res, expRes);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testSingleSentenceTwoProvider() {
        String[] origStr1 = {
            "$APHDM,339.7,M*3D"
        };
        
        String[] origStr2 = {
            "$GPHDM,339.9,M*35"
        };
        
        byte[][] origBytes1 = getBytes(origStr1);
        byte[][] origBytes2 = getBytes(origStr2);
        
        ExpectedResults expRes = new ExpectedResults();
        expRes.addResult(new ExpectedSentence(origStr1[0], "AP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr2[0], "GP", "HDM", HDM.class));
        
        try {
            TestPassThroughDataProvider dp1 = new TestPassThroughDataProvider(origBytes1);
            TestPassThroughDataProvider dp2 = new TestPassThroughDataProvider(origBytes2);
            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp1, dp2);
            
            TestNMEAListener list = new TestNMEAListener();
            nmeaSP.addListener(list);
            nmeaSP.start();
            nmeaSP.stop();
            
            List<NMEASentence> res = list.getNMEASentences();
            validateSentences(res, expRes);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testSingleSentenceThreeProvider() {
        String[] origStr1 = {
            "$APHDM,339.7,M*3D"
        };
        
        String[] origStr2 = {
            "$GPHDM,339.9,M*35"
        };
        
        String[] origStr3 = {
            "$APHDM,335.2,M*34"
        };
        
        byte[][] origBytes1 = getBytes(origStr1);
        byte[][] origBytes2 = getBytes(origStr2);
        byte[][] origBytes3 = getBytes(origStr3);
        
        ExpectedResults expRes = new ExpectedResults();
        expRes.addResult(new ExpectedSentence(origStr1[0], "AP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr2[0], "GP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr3[0], "AP", "HDM", HDM.class));
        
        try {
            TestPassThroughDataProvider dp1 = new TestPassThroughDataProvider(origBytes1);
            TestPassThroughDataProvider dp2 = new TestPassThroughDataProvider(origBytes2);
            TestPassThroughDataProvider dp3 = new TestPassThroughDataProvider(origBytes3);
            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp1, dp2, dp3);
            
            TestNMEAListener list = new TestNMEAListener();
            nmeaSP.addListener(list);
            nmeaSP.start();
            nmeaSP.stop();
            
            List<NMEASentence> res = list.getNMEASentences();
            validateSentences(res, expRes);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testMultiSentenceSingleProvider() {
        String[] origStr = {
                "$APHDM,339.7,M*3D",
                "$GPHDM,333.7,M*31",
                "$IIHDM,332.7,M*27",
                "$GNHDM,345.7,M*2E"
            };
        byte[][] origBytes = getBytes(origStr);
        
        ExpectedResults expRes = new ExpectedResults();
        expRes.addResult(new ExpectedSentence(origStr[0], "AP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr[1], "GP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr[2], "II", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr[3], "GN", "HDM", HDM.class));
        
        try {
            TestPassThroughDataProvider dp = new TestPassThroughDataProvider(origBytes);
            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp);
            
            TestNMEAListener list = new TestNMEAListener();
            nmeaSP.addListener(list);
            nmeaSP.start();
            nmeaSP.stop();
            
            List<NMEASentence> res = list.getNMEASentences();
            validateSentences(res, expRes);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testMultiSentenceTwoProvider() {
        String[] origStr1 = {
            "$APHDM,339.7,M*3D",
            "$GPHDM,333.7,M*31",
            "$IIHDM,332.7,M*27",
            "$GNHDM,345.7,M*2E"
        };
        
        String[] origStr2 = {
            "$QAHDM,339.9,M*32",
            "$FDHDM,333.7,M*24",
            "$RRHDM,332.7,M*27",
            "$ZKHDM,345.7,M*36"
        };
        
        byte[][] origBytes1 = getBytes(origStr1);
        byte[][] origBytes2 = getBytes(origStr2);
        
        ExpectedResults expRes = new ExpectedResults();
        expRes.addResult(new ExpectedSentence(origStr1[0], "AP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr1[1], "GP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr1[2], "II", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr1[3], "GN", "HDM", HDM.class));
        
        expRes.addResult(new ExpectedSentence(origStr2[0], "QA", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr2[1], "FD", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr2[2], "RR", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr2[3], "ZK", "HDM", HDM.class));
        
        try {
            TestPassThroughDataProvider dp1 = new TestPassThroughDataProvider(origBytes1);
            TestPassThroughDataProvider dp2 = new TestPassThroughDataProvider(origBytes2);
            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp1, dp2);
            
            TestNMEAListener list = new TestNMEAListener();
            nmeaSP.addListener(list);
            nmeaSP.start();
            nmeaSP.stop();
            
            List<NMEASentence> res = list.getNMEASentences();
            validateSentences(res, expRes);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testMultiSentenceThreeProvider() {
        String[] origStr1 = {
            "$APHDM,339.7,M*3D",
            "$GPHDM,333.7,M*31",
            "$IIHDM,332.7,M*27",
            "$GNHDM,345.7,M*2E"
        };
        
        String[] origStr2 = {
            "$GPHDM,339.9,M*35",
            "$APHDM,333.7,M*37",
            "$GNHDM,332.7,M*2E",
            "$IIHDM,345.7,M*27"
        };
        
        String[] origStr3 = {
            "$APHDM,335.2,M*34",
            "$INHDM,333.7,M*68",
            "$GAHDM,332.7,M*21",
            "$GRHDM,345.7,M*32"
        };
        
        byte[][] origBytes1 = getBytes(origStr1);
        byte[][] origBytes2 = getBytes(origStr2);
        byte[][] origBytes3 = getBytes(origStr3);
        
        ExpectedResults expRes = new ExpectedResults();
        expRes.addResult(new ExpectedSentence(origStr1[0], "AP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr1[1], "GP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr1[2], "II", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr1[3], "GN", "HDM", HDM.class));
        
        expRes.addResult(new ExpectedSentence(origStr2[0], "GP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr2[1], "AP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr2[2], "GN", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr2[3], "II", "HDM", HDM.class));
        
        expRes.addResult(new ExpectedSentence(origStr3[0], "AP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr3[1], "IN", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr3[2], "GA", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr3[3], "GR", "HDM", HDM.class));
        
        try {
            TestPassThroughDataProvider dp1 = new TestPassThroughDataProvider(origBytes1);
            TestPassThroughDataProvider dp2 = new TestPassThroughDataProvider(origBytes2);
            TestPassThroughDataProvider dp3 = new TestPassThroughDataProvider(origBytes3);
            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp1, dp2, dp3);
            
            TestNMEAListener list = new TestNMEAListener();
            nmeaSP.addListener(list);
            nmeaSP.start();
            nmeaSP.stop();
            
            List<NMEASentence> res = list.getNMEASentences();
            validateSentences(res, expRes);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testMultiSentenceRealSentences() {
        String[] origStr = {
                "$APHDM,339.7,M*3D",
                "$APRSA,8.6,A*30",
                "$APHDG,096.2,,,13.0,E*0C",
                "$GPVTG,077.2,T,064.0,M,0.1,N,0.2,K,D*25",
                "$SDDBT,16.5,f,5.0,M,2.8,F*3B",
                "$SDDPT,5.0,*7C",
                "$ECMWV,269.0,R,1.2,N,A*35",
                "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56",
                "$VWMTW,18.50,C*2E",
                "$GPZDA,190107.66,10,05,2020,,*6C"
            };
        byte[][] origBytes = getBytes(origStr);
        
        ExpectedResults expRes = new ExpectedResults();
        expRes.addResult(new ExpectedSentence(origStr[0], "AP", "HDM", HDM.class));
        expRes.addResult(new ExpectedSentence(origStr[1], "AP", "RSA", RSA.class));
        expRes.addResult(new ExpectedSentence(origStr[2], "AP", "HDG", HDG.class));
        expRes.addResult(new ExpectedSentence(origStr[3], "GP", "VTG", VTG.class));
        expRes.addResult(new ExpectedSentence(origStr[4], "SD", "DBT", DBT.class));
        expRes.addResult(new ExpectedSentence(origStr[5], "SD", "DPT", DPT.class));
        expRes.addResult(new ExpectedSentence(origStr[6], "EC", "MWV", MWV.class));
        expRes.addResult(new ExpectedSentence(origStr[7], "GP", "GLL", GLL.class));
        expRes.addResult(new ExpectedSentence(origStr[8], "VW", "MTW", MTW.class));
        expRes.addResult(new ExpectedSentence(origStr[9], "GP", "ZDA", ZDA.class));
        
        try {
            TestPassThroughDataProvider dp = new TestPassThroughDataProvider(origBytes);
            NMEASentenceProvider nmeaSP = new NMEASentenceProvider(dp);
            
            TestNMEAListener list = new TestNMEAListener();
            nmeaSP.addListener(list);
            nmeaSP.start();
            nmeaSP.stop();
            
            List<NMEASentence> res = list.getNMEASentences();
            validateSentences(res, expRes);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Validate the list of NMEASentences.
     * 
     * @param res
     * @param expTalkerIds
     * @param expTypeCodes
     * @param expValidities
     * @param expClasses
     */
    private void validateSentences(final List<NMEASentence> res, final ExpectedResults expRes) {
        // Validate the number of sentences
        Assert.assertEquals("Wrong number of sentences", expRes.size(), res.size());
                
        // Validate each sentence
        for (int n = 0; n < res.size(); n++) {
            NMEASentence sentence = res.get(n);

            Assert.assertNotNull("Null Sentence " + n, sentence);

            ExpectedSentence expSent = expRes.getExpected(sentence.getRawSentence());

            Assert.assertEquals("Incorrect type code " + n, expSent.m_typeCode, sentence.getTypeCode());
            Assert.assertTrue("Incorrect class " + n, expSent.m_clazz.isInstance(sentence));
            
            if (TalkerSentence.class.isInstance(sentence)) {
                TalkerSentence tSentence = TalkerSentence.class.cast(sentence);
                Assert.assertEquals("Incorrect talker ID " + n, expSent.m_talkerId, tSentence.getTalkerId());
            }
        }
        
        if (expRes.size() != 0) {
            expRes.printRemaining();
            Assert.assertEquals("Did not receive all sentences", 0, expRes.size());
        }
    }
    
    private byte[][] getBytes(final String[] strs) {
        byte[][] bytes = new byte[strs.length][];
        for (int n = 0; n < strs.length; n++) {
            bytes[n] = (strs[n] + "\n").getBytes();
        }
        
        return bytes;
    }
    
    /**
     * 
     * @author Scott Stanley
     */
    class ExpectedSentence {
        String m_rawSentence;
        Boolean m_validity;
        String m_talkerId;
        String m_typeCode;
        Class<? extends NMEASentence> m_clazz;
        
        public ExpectedSentence(String rawSentence, String talkerId, String typeCode,
                                    Class<? extends NMEASentence> clazz) {
            m_rawSentence = rawSentence;
            m_talkerId = talkerId;
            m_typeCode = typeCode;
            m_clazz = clazz;
        }
    }
    
    class ExpectedResults {
        Map<String,Pair<Integer, ExpectedSentence>> m_expResults = new HashMap<String,Pair<Integer, ExpectedSentence>>();
        
        public void addResult(final ExpectedSentence r) {
            if (m_expResults.containsKey(r.m_rawSentence)) {
                Pair<Integer, ExpectedSentence> pOrig = m_expResults.get(r.m_rawSentence);
                Integer cnt = pOrig.getKey() + 1;
                
                Pair<Integer, ExpectedSentence> pNew = new Pair<Integer, ExpectedSentence>(cnt, r);
                m_expResults.put(r.m_rawSentence, pNew);
            } else {
                Integer cnt = Integer.valueOf(1);
                Pair<Integer, ExpectedSentence> p = new Pair<Integer, ExpectedSentence>(cnt, r);

                m_expResults.put(r.m_rawSentence, p);
            }
        }
        
        public ExpectedSentence getExpected(final String rawNmeaStr) {
            if (m_expResults.containsKey(rawNmeaStr)) {
                Pair<Integer, ExpectedSentence> p = m_expResults.remove(rawNmeaStr);
                
                ExpectedSentence s = p.getValue();
                Integer cnt = p.getKey();
                cnt = cnt - 1;
                
                if (cnt > 0) {
                    Pair<Integer, ExpectedSentence> pNew = new Pair<Integer, ExpectedSentence>(cnt, s);
                    m_expResults.put(rawNmeaStr, pNew);
                }
                
                return s;
            } else {
                return null;
            }
        }
        
        public int size() {
            int total = 0;
            for (Pair<Integer, ExpectedSentence> p : m_expResults.values()) {
                total = total + p.getKey();
            }
            
            return total;
        }
        
        public void printRemaining() {
            System.out.println("Remaining Results:");
            for (Pair<Integer, ExpectedSentence> p : m_expResults.values()) {
                System.out.println("  " + p.getKey() + " x " + p.getValue().m_rawSentence);
            }
        }
    }
}
