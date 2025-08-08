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
 * Furuno Proprietary Sentence: GPatt
 * 
 * True heading, pitching, rolling.
 * 
 * References:
 * Furuno Satellite Compass, SC-120, Operator's Manual (sc120_operators_manual_p1.pdf)
 * Interface Specifications, Simrad Sonar Systems (fishSO_interface_spec_en_us.pdf)
 */
public class FEC_GPatt extends AbstractFurunoSentence {
	
	private final Float m_trueHeading;
	private final Float m_pitchAngleDeg;
	private final Float m_rollAngleDeg;

	/**
	 * @param rawSentence
	 */
	public FEC_GPatt(String rawSentence) {
		super(rawSentence);
		
		m_trueHeading = this.getFieldAsFloat(2);
		m_pitchAngleDeg = this.getFieldAsFloat(3);
		m_rollAngleDeg = this.getFieldAsFloat(4);
	}

	/**
	 * @return the trueHeading
	 */
	public Float getTrueHeading() {
		return m_trueHeading;
	}

	/**
	 * @return the pitchAngleDeg
	 */
	public Float getPitchAngleDeg() {
		return m_pitchAngleDeg;
	}

	/**
	 * @return the rollAngleDeg
	 */
	public Float getRollAngleDeg() {
		return m_rollAngleDeg;
	}
}
