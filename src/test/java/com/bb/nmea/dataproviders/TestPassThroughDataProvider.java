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
package com.bb.nmea.dataproviders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bb.nmea.DataProvider;
import com.bb.nmea.DataProviderException;

public class TestPassThroughDataProvider extends DataProvider {
    private static final Logger LOG = LogManager.getLogger(TestPassThroughDataProvider.class);
    final byte[][] m_testByteArrays;
    
    public TestPassThroughDataProvider(final byte[][] testByteArrays) {
        m_testByteArrays = testByteArrays;
    }

    @Override
    public void start() throws DataProviderException {
        LOG.debug("Starting");
        for (byte[] b : m_testByteArrays) {
            LOG.debug("Providing data: " + new String(b));
            this.provideData(b, 0, b.length);
        }
        LOG.debug("Done");
    }

    @Override
    public void stop() throws DataProviderException {
        LOG.debug("Stopping");
    }
}
