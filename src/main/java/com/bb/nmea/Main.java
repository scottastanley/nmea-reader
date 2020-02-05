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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.apache.log4j.Logger;

import com.bb.nmea.rawdataproviders.port.PortListenerDataProvider;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);

    public Main() {
    }

    public static void main(String[] args) {
        PortListenerDataProvider dp = new PortListenerDataProvider();
        
        BufferedReader rdr;
        try {
            PipedInputStream inpStrm = new PipedInputStream();
            PipedOutputStream outStrm = new PipedOutputStream(inpStrm);
            
            DataProvider dpRaw = (DataProvider) dp;
            dpRaw.setOutputStream(outStrm);
            
            rdr = new BufferedReader(new InputStreamReader(inpStrm));
        } catch (IOException e) {
            throw new RuntimeException("Failed configuring NMEA reader", e);
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.info("Shutting down main()...");
                try {
                    dp.stopChild();
                    
                    if (rdr != null) {
                        rdr.close();
                    }
                } catch (DataProviderException | IOException e) {
                    LOG.error("Exception on shutdown", e);
                }
            }
        });

        try {
            dp.start();
            
            String sentenceStr = null;
            while((sentenceStr = rdr.readLine()) != null) {
                LOG.info(sentenceStr);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed reading NMEA sentences", e);
        } catch (DataProviderException e) {
            LOG.error("Failed starting data provider", e);
        }
    }

}
