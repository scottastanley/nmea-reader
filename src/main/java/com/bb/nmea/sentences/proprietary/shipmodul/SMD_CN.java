package com.bb.nmea.sentences.proprietary.shipmodul;

/**
 * ShipModul Proprietary Sentence: CN
 * 
 * Channel Number indicator. This sentence appears before each sentence in the
 * stream and indicates which channel in the MiniPlex the sentence was received on.
 * 
 * Reference: miniplex-42usb.pdf
 * 
 * @author Scott Stanley
 */
public class SMD_CN extends AbstractShipModulSentence {

	public SMD_CN(final String rawSentence) {
		super(rawSentence);
	}
	
	public Integer getChannel() {
		return this.getFieldAsInteger(1);
	}
}
