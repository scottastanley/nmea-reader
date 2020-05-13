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

public class Latitude extends LatLong {

    public Latitude(String str) {
        super(str);
    }
    
    /**
     * Get this latitude value as a decimal using the provided direction.
     * 
     * @param dir The N/S direction represented by this latitude
     * @return The decimal value
     */
    public Float getDecimalLatitude(final Direction dir) {
        if (dir.equals(Direction.EAST) || dir.equals(Direction.WEST)) {
            throw new RuntimeException("Longitude can not be EAST or WEST");
        }
        
        float sign = dir.equals(Direction.NORTH) ? 1.0f : -1.0f;
        float value = this.getDegrees().floatValue() + this.getMinutes()/60.0f;
        return sign * value;
    }
}
