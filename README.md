# NMEA Reader
The NMEA Reader is a library for processing and parsing raw NMEA data in to java objects.  The model is developed to allow multiple data sources as well as multiple clients receiving the processed data.

## Core Components
The library is composed of three primary component types, **DataProviders** which are the sources of raw NMEA data, **NMEAListeners** or clients of the parsed NMEA data and the **NMEASentenceProvider** which connects the two.  All NMEA data provided to the listeners is in the form of sub-classes of the class **NMEASentence**.

### DataProviders
All raw data providers must extend the class _DataProvider_.  This base class provides the core logic used by the _NMEASentenceProvider_ for connecting the raw data provided by the _DataProvider_ to the parsing logic and subsequently to the listeners.  The concrete implementation of the _DataProvider_ class submits raw data for processing by calling the method ``provideData(byte[] data, int offset, int numBytes)`` on the super class _DataProvider_.  The concrete data provider is expected to implement the method ``start()`` which starts the processing of data and the method  ``stop()`` in which the data provider should halt processing and clean up all resources in use.  How the raw NMEA data is read and provided to the underlying system is entirely up to the data provider implementation.

Two data providers are included in the library, the _PortListenerDataProvider_ which reads the raw data from a serial port and the _InputFileDataProvider_ which reads raw NMEA sentences from an input.  The primary usage for the second date provider is for development and debugging purposes when a live NMEA data source is not available.  The provided _SentenceLogger_ listener saves NMEA in a format consistent with the _InputFileDataProvider_.

### NMEAListeners
All clients for the NMEA data must implement the interface _NMEAListener_. _NMEASentence_s are provided to the client by call backs to the method ``processEvent(NMEASentence sentence)``.  Any processing in this method should be kept to minimal levels of complexity to minimize the work done on the parsing thread.  Slowing this thread will potentially impact the rate at which sentences are provided to the system since all clients are served by the same thread.

During the shutdown process, the _NMEASentenceProvider_ will call the method ``stop()`` on the listener to allow it to close any open resources.

### NMEASentenceProvider
The _NMEASentenceProvider_ is the core driver for the processing. All data providers from which raw data will should be processed are provided when the NMEA sentence provider is constructed and listeners are registered through the method ``addListener(NMEAListener listener)``. For each data provider processed by the _NMEASentenceProvider_ a thread is constructed to parse the data from this data provider and provide the _NMEASentence_ to the listeners. This parsing thread and the data provider are connected through a pair of piped output and input streams. Processing of data is started and stopped by calling the methods ``start()`` and ``stop()``.  

## Usage
An example Java application is provided in the class ``com.bb.nmea.main.Main``.  This client is configurable using a properties file and supports reading NMEA data from files and serial ports.  

The basic pattern for using the _NMEASentenceProvider_ is as follows;
``java
List<DataProvider> dataProviders = 

``

## References
* [NMEA Revealed](https://gpsd.gitlab.io/gpsd/NMEA.html|"FOO")
* [GPS - NMEA sentence information](http://aprs.gids.nl/nmea/)
* [The NMEA 0183 Protocol](https://www.tronico.fi/OH6NT/docs/NMEA0183.pdf)
* [The NMEA 0183 Information sheet, Everything you wanted to know about NMEA 0183 (but were afraid to ask)](https://www.actisense.com/wp-content/uploads/2017/07/NMEA-0183-Information-sheet-issue-4-1-1.pdf), Actisense.
