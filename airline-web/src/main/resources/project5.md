CS410P [Adv. Java] - Project #5 Mindmap Pre-Plan - Dan Jang, 3/9/23

Main Functionalities & Requirements
- [ ] AirlineServlet.java (REST Access to Airline)
- [ ] URL #1
- [ ] URL #2
- [ ] Multiple Airline Support
- [ ] AirlineRestClient.java
- [ ] Project5.java
- [ ] Parameters & Options
- [ ] -search option
- [ ] Only airline required,  src & dest are optional. No other arguments.
- [ ] Pretty Print all direct flights from src airport & arrive at dest airport.
- [ ] -host hostname
- [ ] -port port
- [ ] Functionalities & Requirements
- [ ] Add a flight to the server
- [ ] Search for a flight between two airports
- [ ] Print Message If No Direct Flights Between src & dest
- [ ] Pretty print all flights within an airline

Error Handling Specifications
- [ ] If… Invalid Command-line Syntax
- [ ] If… only specified either one of the  -hostname or -port
- [ ] If… Incorrect Day or Time Formatting
- [ ] If… the server connection cannot be made
- [ ] If… REST URL Data is not formatted correctly

Core Project Objectives -

AirlineServlet
- [ ] URL #1 - http://host:port/airline/flights?airline=name
- [ ] GET returns all Flights in XML formatting
- [ ] POST creates new flight from six (6) HTTP parameters - airline, flightNumber, src ,depart, dest, & arrive.
- [ ] URL #2 - http://host:port/airline/flights?airline=name&src=airport&dest=airport
- [ ] GET returns all of Airline’s flights in XML formatting

AirlineRestClient
- [ ]

Project5
- [ ] Integrate Project4.java legacy functions & methods
- [ ] Command-line parsing
- [ ] Interlink with AirlineServlet

Legacy Code Re-Integration
- [ ] Test

Test Cases -

AirlineServlet
- [ ] If… no direct flights between specified two airports
- [ ] If… a airport specified does not exist
- [ ] If… airline data is misconfigured
- [ ] If… data given to REST URL is incorrectly formatted
- [ ] If…

Project5
- [ ] If…
- [ ] If…
- [ ] If…
- [ ] If…
- [ ] If…

XMLDumper (& XMLParser…?)
- [ ] If…
- [ ] If…
- [ ] If…
- [ ] If…
- [ ] If…

Re-Integrated Legacy Code
- [ ] If…
- [ ] If…
- [ ] If…
- [ ] If…
- [ ] If… 
