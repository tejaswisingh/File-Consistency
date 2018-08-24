# File-Consistency

The project contains lib and src folders .

There is src folder  contains four java files.

MultiServer - This file contains entire code of the server.

Client1, Client2, Client3  - These three file are the code of the three clients.

The zip also has automictically generated class path file and project path file.

The lib folder contains 5 jar files that you have to import to the project by right clicking on project name in eclipse then selecting properties and then going to buildpath option and then clicking on add jar option and select all 5 of them and then click on apply and close. 

MultiServer.java file must run first followed by Client1.java, Client2.java, Client3.java. 

Some of the parameters in this have been statically defined. Like the identical  test.txt file in three  static predefined directories - C:/Users/tejas/Desktop/Client1/test.txt , C:/Users/tejas/Desktop/Client1/test.txt, C:/Users/tejas/Desktop/Client1/test.txt  and the updated files are being pushed to a static pre defined directory - C:/Users/tejas/Desktop/Server/.

Comments have been provided in the entire code for better understanding of the code. 

Change any test.txt in any predefined directories and wait 10 seconds for it to detect changes and push changes to server.

