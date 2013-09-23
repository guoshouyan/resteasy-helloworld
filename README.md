resteasy-quickstart
===================

This is a base project to quickly test concepts for JAX-RS using
Resteasy and Jetty.

It contains a Start class that configures jetty correctly and has
Maven configured such that RestEasy will automatically find your REST
resources.

Getting started
---------------

Perform a git clone of the repository and remove the git bits:

    git clone https://github.com/dashorst/jaxrs-quickstart-resteasy.git
    cd jaxrs-quickstart-resteasy
    rm -rf .git

Now you can import the project into your favorite IDE, use the Maven
integration to import the POM file.

To run the project use the `Start` class located in the `src/test/java`
folder. This will start the embedded Jetty container and make the REST
resources available at port 8080 of localhost.

You can use for example your browser to retrieve the root resource:

    http://localhost:8080

This should retrieve a plain text string:

    Hello, World!

If run using the `curl` command you should expect the following output:

    $ curl http://localhost:8080 --verbose
    > GET / HTTP/1.1
    > User-Agent: curl/7.24.0
    > Host: localhost:8080
    > Accept: */*
    > 
    < HTTP/1.1 200 OK
    < Content-Type: text/plain
    < Content-Length: 13
    < Server: Jetty(8.1.12.v20130726)
    < 
    < Hello, World!
