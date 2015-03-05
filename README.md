1. This project is a console developed using Maven.

2. Using following command to generate "recipeFinder-1.0.0-jar-with-dependencies.jar" file in "target" directory:
	mvn clean package
	
3. Copy "recipeFinder-1.0.0-jar-with-dependencies.jar" jar file in "target" directory to executing directory,

4. Create a new directory "log" under executing directory if it does not exist.

5. Using following command to find recipe:
	java -jar recipeFinder-1.0.0-jar-with-dependencies.jar <fridge csv list file> <recipe json file>
  

