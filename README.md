# SearchRobot
Test task for Java Incubator
***
## Instructions

First of all, you should check data for connection to database. Open file **SearchRobot/src/searchrobot/GatewayDB.java** and edit fields:
```java
DB_USER;
DB_PASS;
DB_CONNECTION; //edit hostname, port, database
DB_TABLE;
```

After that, open console and move to **SearchRobot** directory. To build the application, enter in console:
```console
  javac -sourcepath src -d bin src/searchrobot/Main.java
  echo main-class: searchrobot.Main>manifest.mf
  echo class-path: jdbc/postgresql.jar >>manifest.mf
  jar -cmf manifest.mf searchrobot.jar -C bin .
```

Finally, you can run the application with command:
```console
  java -jar searchrobot.jar
```
