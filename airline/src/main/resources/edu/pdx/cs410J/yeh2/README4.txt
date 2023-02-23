CS410P: Advanced Java Programming, Winter 2023 - Dan Jang, February 2023 [#4]
Description: Project #4 focuses on the storage of airline information into XML files.
Project #4 will both be able to dump, parse, and convert(er) into & of XML files.
Specifically, we will be able to interconvert between valid airline data, airline objects, XML files, & text files (vice-versa with the included Converter class!)

usage: java -jar target/airline-2023.0.0.jar [options] <args>
       args are (in this order):
           airline The name of the airline
           flightNumber The flight number
           src Three-letter code of departure airport
           depart Departure date and time (am/pm)
           dest Three-letter code of arrival airport
           arrive Arrival date and time (am/pm)
       options are (options may appear in any order):
           -xmlFile file Where to read/write the airline info
           -textFile file Where to read/write the airline info
           -pretty file Pretty print the airline’s flights to
                        a text file or standard out (file -)
           -print Prints a description of the new flight
           -README Prints a README for this project and exits