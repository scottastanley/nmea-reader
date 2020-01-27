package com.bb.nmea;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SentenceReaderThreadGroup 
        extends ThreadGroup {
    private static final Logger LOG = Logger.getLogger(SentenceReaderThreadGroup.class);
    private final List<Thread> m_threads = new ArrayList<Thread>();

    /**
     * Create a new SentenceReaderThreadGroup.
     *  
     * @param name 
     */
    public SentenceReaderThreadGroup() {
        super("Sentence Reader Thread Group");
    }
    
    /**
     * Create a Thread in this ThreadGroup for the given Runnable and
     * start it.
     * 
     * @param runnable The runnable to start on the new thread
     */
    void createStartThreadForRunnable(final Runnable runnable) {
        Thread thrd = new Thread(this, runnable);
        thrd.setName("Sentence Reader " + m_threads.size());
        m_threads.add(thrd);
        
        LOG.debug("Creating thread " + thrd.getName() + " starting");
        thrd.start();
    }
    
    /**
     * Join all of the threads in this ThreadGroup.
     * 
     * @throws InterruptedException 
     */
    void joinAll() 
            throws InterruptedException {
        for (Thread thrd : m_threads) {
            thrd.join();
        }
    }
    
    /**
     * Wait on all sentence reader threads to start
     */
    void waitOnAllAlive() {
        for (Thread thrd : m_threads) {
            if (! thrd.isAlive()) {
                try {
                    LOG.debug("Waiting on thread to start: " + thrd.getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    LOG.info("Interrupted waiting on threads to start.  Ignoring...");
                }
            } else {
                LOG.info("Thread is alive: " + thrd.getName());
            }
        }
    }
}
