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
package com.bb.nmea.dataproviders.inputfile;

import com.bb.nmea.DataProviderException;

/**
 * The interface for the method which should be called by the FileReader 
 * with data as it is obtained.
 * 
 * @author Scott Stanley
 */
interface DataMethod {
    void provideData(final byte[] data, final int numBytes) 
            throws DataProviderException;
}
