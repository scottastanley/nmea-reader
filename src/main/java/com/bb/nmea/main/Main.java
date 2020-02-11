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
import com.bb.nmea.NMEASentenceProviderException;
import com.bb.nmea.NMEAListener;
import com.bb.nmea.NMEASentenceProvider;
import com.bb.nmea.dataproviders.inputfile.InputFileDataProvider;
import com.bb.nmea.dataproviders.port.PortListenerDataProvider;
import com.bb.nmea.listeners.SentenceLogger;

/**
 * The Main class for the example client using the {@link com.bb.nmea.NMEASentenceProvider} to 
 * listen to NMEA sentences.
 * This java application is written in a configurable way to allow listening to multiple
 * ports or reading from source files using the provided {@link com.bb.nmea.DataProvider}s,
 * {@link com.bb.nmea.dataproviders.port.PortListenerDataProvider} and 
 * {@link com.bb.nmea.dataproviders.inputfile.InputFileDataProvider}.  Details on how to 
 * configure and run this example client are defined in {@link com.bb.nmea.main}.
 * 
 * @author Scott Stanley
 */
public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    public static void main(String[] args) {
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
        } catch (NMEASentenceProviderException e) {
            LOG.error("Failed setting up the NMEASentenceProvider", e);
            System.exit(-1);
        } finally {
            // Stop the sentence provider
            if (nmeaProv != null) {
                try {
                    nmeaProv.stop();
                } catch (NMEASentenceProviderException e) {
                    LOG.error("Error stopping NMEA sentence provider", e);
                }
            }
        }
    }
    
    /**
     * Open a user console and wait on the user to enter "exit" before returning
     */
    private static void waitOnUserInput() {
        Scanner s = new Scanner(System.in);
        
        String input = "";
        while (! input.equalsIgnoreCase("exit")) {
            LOG.info("\nEnter \"exit\" to terminate.\n");
            input = s.nextLine();
        }
        
        s.close();
    }
}
