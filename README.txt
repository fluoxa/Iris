
Getting Started:

(1) install MongoDB
(2) load the training and test images encoded in the imageEntity.json file (to be found in the files/dbDumbs/Jan) in to the mongoDB
(3) load the example neural net encoded in the neuralnetEntity.json file (to be found in the files/dbDumbs/Jan) in to the mongoDB 
(4) build the project using maven (via the pom-file)
(5) start the application with java -jar iris-0.0.1-SNAPSHOT.jar on command line
(6) open any browser and go to localhost:8080 to see digit recognition application
    (first select a neural net from the list on the left hand side then draw any number into the left frame)
(7) to visit the admin pages go to localhost:8080/#!training and localhost:8080/#!neuralnetconfig to train and configure any neural net, respectively
