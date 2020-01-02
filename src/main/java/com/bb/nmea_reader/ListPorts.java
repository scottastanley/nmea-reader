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
