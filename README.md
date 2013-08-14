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

    git clone https://github.com/dashorst/resteasy-quickstart.git
    cd resteasy-quickstart
    rm -rf .git

Now you can import the project into your favorite IDE, use the Maven
integration to import the POM file.

To run the project use the `Start` class located in the `src/test/java`
folder. This will start the embedded Jetty container and make the REST
resources available at port 8080 of localhost.
