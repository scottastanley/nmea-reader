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
package com.bb.nmea.sentences.common;

public class Longitude extends LatLong {

    public Longitude(String str) {
        super(str);
    }
    
    /**
     * Get this longitude value as a decimal using the provided direction.
     * 
     * @param dir The E/W direction represented by this longitude
     * @return The decimal value
     */
    public Float getDecimalLongitude(final Direction dir) {
        if (dir.equals(Direction.NORTH) || dir.equals(Direction.SOUTH)) {
            throw new RuntimeException("Longitude can not be NORTH or SOUTH");
        }
        
        float sign = dir.equals(Direction.EAST) ? 1.0f : -1.0f;
        float value = this.getDegrees().floatValue() + this.getMinutes()/60.0f;
        return sign * value;
    }

}
