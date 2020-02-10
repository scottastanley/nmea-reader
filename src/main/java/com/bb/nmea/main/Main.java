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
package com.bb.nmea.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.bb.nmea.DataProvider;
import com.bb.nmea.NMEADataProcessorException;
import com.bb.nmea.NMEAListener;
import com.bb.nmea.NMEASentenceProvider;
import com.bb.nmea.listeners.SentenceLogger;
import com.bb.nmea.rawdataproviders.inputfile.InputFileDataProvider;
import com.bb.nmea.rawdataproviders.port.PortListenerDataProvider;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    public Main() {
    }

    public static void main(String[] args) {
        Main m = new Main();
        m.run();
    }
    
    private void run() {
        Configuration config = new Configuration();
        
        //
        // Configure all data providers
        //
        List<DataProvider> dataProviders = new ArrayList<DataProvider>();
        
        // Configure the port data providers
        List<PortConfig> portConfigs = config.getPortConfigs();
        for (PortConfig portConfig : portConfigs) {
            PortListenerDataProvider dp = new PortListenerDataProvider(portConfig.getDescriptor(), portConfig.getBaudRate());
            dataProviders.add(dp);
        }
        
        // Configure the file data providers
        List<FileConfig> fileConfigs = config.getFileConfigs();
        for (FileConfig fileConfig : fileConfigs) {
            File file = new File(fileConfig.getFilename());
            InputFileDataProvider dp = new InputFileDataProvider(file, fileConfig.getPauseMillis());
            dataProviders.add(dp);
        }
        
        List<NMEAListener> listeners = new ArrayList<NMEAListener>();
        listeners.add(new SentenceLogger());
        
        //
        // Set up the NMEA sentence provider and start it
        //
        NMEASentenceProvider nmeaProv = null;
        try {
            nmeaProv = new NMEASentenceProvider(dataProviders.toArray(new DataProvider[0]));
            
            // Add the listeners
            for (NMEAListener l : listeners) {
                nmeaProv.addListener(l);
            }
            
            // Start processing data
            nmeaProv.start();
            
            // Wait on the user before proceeding
            waitOnUserInput();
        } catch (NMEADataProcessorException e) {
            LOG.error("Failed setting up the NMEASentenceProvider", e);
            System.exit(-1);
        } finally {
            // Stop the sentence provider
            if (nmeaProv != null) {
                try {
                    nmeaProv.stop();
                } catch (NMEADataProcessorException e) {
                    LOG.error("Error stopping NMEA sentence provider", e);
                }
            }
        }
    }
    
    /**
     * Open a user console and wait on the user to enter "exit" before returning
     */
    private void waitOnUserInput() {
        Scanner s = new Scanner(System.in);
        
        String input = "";
        while (! input.equalsIgnoreCase("exit")) {
            LOG.info("\nEnter \"exit\" to terminate.\n");
            input = s.nextLine();
        }
        
        s.close();
    }
}
