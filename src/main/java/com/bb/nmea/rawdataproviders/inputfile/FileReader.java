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
package com.bb.nmea.rawdataproviders.inputfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

import com.bb.nmea.DataProvider;
import com.bb.nmea.DataProviderException;

public class FileReader implements Runnable {
    private static final Logger LOG = Logger.getLogger(FileReader.class);
    private final DataProvider m_dp;
    private final InputStream m_inpStrm;
    private final CountDownLatch m_latch;
    
    private Integer m_maxBytesPerBlock = 64;
    private Long m_delay = 10L;

    public FileReader(final DataProvider dp, final InputStream inpStrm, 
                      final CountDownLatch latch) {
        m_dp = dp;
        m_inpStrm = inpStrm;
        m_latch = latch;
    }

    @Override
    public void run() {
        Random ran = new Random(System.currentTimeMillis());
        
        // Initialize the buffer
        int numBytesToRead = ran.nextInt(m_maxBytesPerBlock) + 1;
        LOG.debug("Num bytes to read: " + numBytesToRead);
        byte[] buffer = new byte[numBytesToRead];
        
        int numBytesRead = 0;
        try {
            while ((numBytesRead = m_inpStrm.read(buffer)) != -1) {
                m_dp.provideData(buffer, 0, numBytesRead);
                
                // Change the buffer size
                numBytesToRead = ran.nextInt(m_maxBytesPerBlock) + 1;
                LOG.debug("Num bytes to read: " + numBytesToRead);
                buffer = new byte[numBytesToRead];

                if (m_delay >= 0) {
                    Thread.sleep(m_delay);
                }
            }
        } catch (IOException e) {
            LOG.error("Failed reading source file", e);
        } catch (DataProviderException e) {
            LOG.error("Failed providing data", e);
        } catch (InterruptedException e) {
            LOG.info("Thread interrupted");
            // Ignore this one since this is thrown in the natural stop process
        } finally {
            
            // Close the input stream
            if (m_inpStrm != null) {
                try {
                    LOG.info("Closing input stream");
                    m_inpStrm.close();
                } catch (IOException ex) {}
            }
            
            // Count down on the latch to indicate we completed
            m_latch.countDown();
        }
    }

    /**
     * @return the maxBytesPerBlock
     */
    public Integer getMaxBytesPerBlock() {
        return m_maxBytesPerBlock;
    }

    /**
     * @param maxBytesPerBlock the maxBytesPerBlock to set
     */
    public void setMaxBytesPerBlock(Integer maxBytesPerBlock) {
        m_maxBytesPerBlock = maxBytesPerBlock;
    }

    /**
     * @return the delay
     */
    public Long getDelay() {
        return m_delay;
    }

    /**
     * @param delay the delay to set
     */
    public void setDelay(Long delay) {
        m_delay = delay;
    }
}
