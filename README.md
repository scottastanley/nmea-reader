# NMEA Reader
The NMEA Reader is a library for processing and parsing raw NMEA data in to java objects.  The model is developed to allow multiple data sources as well as multiple clients receiving the processed data.

## Core Components
The library is composed of three primary component types, **DataProviders** which are the sources of raw NMEA data, **NMEAListeners** or clients of the parsed NMEA data and the **NMEASentenceProvider** which connects the two.  All NMEA data provided to the listeners is in the form of sub-classes of the class **NMEASentence**.

### DataProviders
All raw data providers must extend the class _DataProvider_.  This base class provides the core logic used by the **NMEASentenceProvider** for connecting the raw data provided by the _DataProvider_ to the parsing logic and subsequently to the listeners.  The concrete implementation of the _DataProvider_ class submits raw data for processing by calling the method ``provideData(byte[] data, int offset, int numBytes)`` on the super class _DataProvider_.  The concrete data provider is expected to implement the method ``start()`` which starts the processing of data and the method  ``stop()`` in which the data provider should halt processing and clean up all resources in use.

### NMEAListeners

### NMEASentenceProvider


## Usage

## References
* [NMEA Revealed](https://gpsd.gitlab.io/gpsd/NMEA.html|"FOO")
* [GPS - NMEA sentence information](http://aprs.gids.nl/nmea/)
* [The NMEA 0183 Protocol](https://www.tronico.fi/OH6NT/docs/NMEA0183.pdf)
* [The NMEA 0183 Information sheet, Everything you wanted to know about NMEA 0183 (but were afraid to ask)](https://www.actisense.com/wp-content/uploads/2017/07/NMEA-0183-Information-sheet-issue-4-1-1.pdf), Actisense.
