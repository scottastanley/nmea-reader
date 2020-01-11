package com.bb.nmea.rawdataproviders;

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
            
            dp.setOutputStream(outStrm);
            
            rdr = new BufferedReader(new InputStreamReader(inpStrm));
        } catch (IOException e) {
            throw new RuntimeException("Failed configuring NMEA reader", e);
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.info("Shutting down main()...");
                try {
                    dp.stop();
                    
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
