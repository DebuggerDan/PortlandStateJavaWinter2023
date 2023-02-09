CS410P: Advanced Java Programming, Winter 2023 - Dan Jang, February 2023 [#3]
Description: Project #3 focuses on using sorting Java libraries as well as the usage of dates.

usage: java -jar target/airline-2023.0.0.jar [options] <args>
       args are (in this order):
           airline The name of the airline
           flightNumber The flight number
           src Three-letter code of departure airport
           depart Departure date and time (am/pm)
           dest Three-letter code of arrival airport
           arrive Arrival date and time (am/pm)
       options are (options may appear in any order):
           -pretty file Pretty print the airline’s flights to
                        a text file or standard out (file -)
           -textFile file Where to read/write the airline info
           -print Prints a description of the new flight
           -README Prints a README for this project and exits