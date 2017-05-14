LITTLE HTTP SERVER - README
===========================

A little HTTP server written as a code assignment. The server will listen on a given port and list the contents of the
directory it is running in.


Pre-requisites
--------------

These are the requirements for obtaining and running the server:

    * Git to obtain the source code
    * Maven 3 to build the source code
    * Java 8 to run the server


Obtaining, building and running the source code
-----------------------------------------------

Once the pre-requisites are met, follow these instructions to obtain, build and run the source code:

    1. git clone https://github.com/dromoli/little-http-server.git
    2. cd little-http-server
    3. mvn package
    4. java -Dlittle.http.server.port=9001 -jar target/littlehttpserver-1.0-SNAPSHOT-jar-with-dependencies.jar

You can specify the port you want the server to listen on. You do that by adding the system property

    -Dlittle.http.server.port=portNumber

when you run the server, where portNumber is a value between 1024 and 65535. If you do not provide a value for this
property, 8080 will be used as a default port number.


Usage
-----

Once the server is up and running, you should point your browser to http://localhost:portNumber/ and you will see a the
contents of the directory where the server is running. By clicking on the links, you can navigate the file structure.
When you click on a directory, its contents are displayed and it will become the current directory. If you click
on a file, its contents will be displayed (to go back, please hit your browser's back button).
