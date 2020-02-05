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
package com.bb.nmea_reader;

import java.util.Formatter;

import com.fazecast.jSerialComm.SerialPort;

public class ListPorts {

    public ListPorts() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort p : ports) {
            String desc = ListPorts.printSerialPortSummary(p);
            System.out.println(desc);
        }
    }
    
    static String printSerialPortSummary(final SerialPort p) {
        StringBuffer buff = new StringBuffer();
        Formatter form = new Formatter(buff);
        
        form.format("%s: %s\n", p.getSystemPortName(), p.getDescriptivePortName());
        form.format("    desc: %s\n", p.getPortDescription());
        form.format("    baud: %-12d       parity: %-12d\n", p.getBaudRate(), p.getParity());
        form.format("    numDataBits: %-8d    numStopBits: %-8d\n", p.getNumDataBits(), p.getNumStopBits());
        form.format("    readTimeout: %-8d    writeTimeout: %-8d\n", p.getReadTimeout(), p.getWriteTimeout());
        form.format("    inpStream=null: %-8s\n", (p.getInputStream() == null));
        form.close();
        
        return buff.toString();
    }
}
