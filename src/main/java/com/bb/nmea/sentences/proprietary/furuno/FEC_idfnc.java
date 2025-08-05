/*
 * Copyright 2025 Scott Alan Stanley
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
