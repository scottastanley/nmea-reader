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
