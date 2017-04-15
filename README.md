Camel XML Importer
==================
Java Spring project to read posts xml data file using Camel route, process it's data and save it as CSV file.

Steps
-----
 * Read XML File using Camel file component
 * Un-marshal data into Post model using using `stax` splitter
 * Filter posts questions
 * Normalization, by removing step word and convert to lower-case
 * Aggregate multiple posts as a batch and send it process
 * Save the output as CSV

**Note**
This project is spring web project, write REST controller to run the project  
