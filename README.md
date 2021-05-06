
# Sicredi Update Accounts

## :computer: Project 

Application to update clients accounts information

## :rocket: Built with

This project was developed with the following technologies:

-  [SpringBoot](https://spring.io/projects/spring-boot)
-  [Java_11](https://docs.oracle.com/en/java/)

## :information_source: How to run

### Requirements


#### Java 11
#### Maven

### Generate .jar

Go to terminal and type mvn package at the root directory of application

```bash
# to generate the .jar
$ mvn package
```

### Running the .jar

- The jar will be available at /target/sicredi-update-accounts-0.0.1-SNAPSHOT.jar 
- If you want, use the test.csv at resources directory to test.

```bash
# to run the .jar
$ java -jar target/sicredi-update-accounts-0.0.1-SNAPSHOT.jar src/main/resources/csvfiles/test.csv
```

###Seeing the results

The result of process (result.csv) will be available at the same directory of the original file (in this case src/main/resources/csvfiles/). 
