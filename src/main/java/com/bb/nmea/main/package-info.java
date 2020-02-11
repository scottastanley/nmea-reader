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

/**
 * This package contains an example client for the {@link com.bb.nmea.NMEASentenceProvider}.  
 * This client is intended to be a usable and configurable example utilizing the data providers
 * and listeners provided as part of the NMEA reader library.  The main method for the client is
 * defined in the class {@link com.bb.nmea.main.Main}.  The rest of the classes in this package
 * are support file providing the configuration logic for the client.
 * <p>
 * The execution of the client is managed through the use of a configuration 
 * file in standard properties file format.  The default name for this file is 
 * <code>nmea_config.properties</code>, however this can be overridden by specifying a file 
 * name using a system level property <code>nmeaConfigFile</code> using the <code>-D</code> 
 * command line option, ie <code>-DnmeaConfigFile=override_config.properties</code>.
 * <p>
 * Two data providers are  supported, the {@link com.bb.nmea.dataproviders.port.PortListenerDataProvider} and 
 * {@link com.bb.nmea.dataproviders.inputfile.InputFileDataProvider}.  Multiple data providers of each 
 * type may be configured.
 * <p>
 * <b>Example: Serial Port Configuration</b>
 * {@code ports.ids=port1,port2}
 * {@code ports.port1.descriptor=/dev/cu.usbseriala}
 * {@code ports.port1.baudRate=9600}
 * {@code   }
 * {@code ports.port2.descriptor=/dev/cu.usbserialb}
 * {@code ports.port2.baudRate=9600}
 * <p>
 * <b>Example: File Configuration</b>
 * {@code files.ids=file1,file2}
 * {@code files.file1.filename=raw_1577648032142.nmea}
 * {@code files.file1.pause_millis=100}
 * {@code   }
 * {@code files.file2.filename=raw_1581191643276.nmea}
 * {@code files.file2.pause_millis=100}
 * <p>
 * Currently, the sole {@link com.bb.nmea.NMEAListener} available is the {@link com.bb.nmea.listeners.SentenceLogger}.
 * This listener simply listens to all provided sentences, valid or invalid, and outputs them to a file
 * <code>raw_######.nmea</code> where the number is a millisecond timestamp when the output file is opened.
 */
package com.bb.nmea.main;