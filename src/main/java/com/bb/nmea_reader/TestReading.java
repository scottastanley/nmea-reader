package com.bb.nmea_reader;

import java.util.Arrays;

import com.fazecast.jSerialComm.SerialPort;

public class TestReading {

    public TestReading() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) throws InterruptedException {
//        String portDescriptor = "/dev/tty.usbserial-AM009S8I";
        String portDescriptor = "/dev/cu.usbserial-AM009S8I";
        SerialPort port = SerialPort.getCommPort(portDescriptor);
        port.setBaudRate(4800);
        port.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        port.openPort();
                
        String summ = ListPorts.printSerialPortSummary(port);
        System.out.println(summ);
        
        String dollar = "$";
        System.out.print("Example: " + dollar + " bytes:");
        for (byte b : dollar.getBytes()) {
            System.out.print(Integer.toBinaryString(b & 0xFF));
        }        
        System.out.println();
        
        while (true) {
            while (port.bytesAvailable() == 0) {
                Thread.sleep(100);
            }
            
            byte[] bytes = new byte[port.bytesAvailable()];
            StringBuffer message = new StringBuffer();
            int numRead = port.readBytes(bytes, bytes.length);
            System.out.print("Read " + numRead + " bytes.  Data:");
            
            byte[] oneByte = new byte[1];
            for (byte b : bytes) {
                System.out.print(Integer.toBinaryString(b & 0xFF) + " ");
                oneByte[0] = b;
                message.append(new String(oneByte));
            }
            System.out.println();
            System.out.println("    message:" + message.toString());
        }
        
//        InputStream inStrm = port.getInputStream();
//        Reader rdr = new InputStreamReader(inStrm);
//        
//        char[] buffer = new char[1024];
//        try {
//            while (rdr.read(buffer) != -1) {
//                System.out.print(buffer);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

}
