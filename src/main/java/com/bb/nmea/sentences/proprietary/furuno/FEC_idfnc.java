package com.bb.nmea.sentences.proprietary.furuno;

/**
 * Furuno Proprietary Sentence: idfnc
 * 
 * Command for specifying functions in the device. I do not have a good definition for
 * this sentence. At least not defining what the fields mean.
 * 
 * @author Scott Stanley
 */
public class FEC_idfnc extends AbstractFurunoSentence {

	public FEC_idfnc(final String rawSentence) {
		super(rawSentence);
	}
	
	public String getField1() {
		return getField(2);
	}
	
	public Float getField2() {
		return getFieldAsFloat(3);
	}
}
