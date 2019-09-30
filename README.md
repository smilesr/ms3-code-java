# ms3-code-java

### The objectives are:

+ Consume a CSV file, parse the data, and insert valid records into a SQLite DB.
+ Verify each record needs to ensure it contains the right number of data elements (10).
+ Write the records that did not have the right number of data elements to a .csv file.  ** NOT COMPLETED YET **
+ After processing the data, write the following information to a .log file: a. # of records received b. # of records successful c. # of records failed  ** NOT COMPLETED YET **
+ Explain steps for getting app to run ** NOT COMPLETED YET **

### Additional notes:

I cleaned up the data before running the program to eliminate all of the extra trailing commas. I did this by running:
```
sed 's/,,,,,//g' ms3Interview-copy.csv > cleanedms3Interview.csv
```
The cleaned-up .csv file is what is included in this directory.
