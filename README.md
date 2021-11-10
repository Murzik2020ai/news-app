# >Good news project
made by Alex Bocharov skype bam271074

an online news portal that allows journalists to upload articles in .zip format, after which they will be automatically added to the main page of the site.
The application consists of two pages:

-/ (http://localhost:9000/)

-/article (http://localhost:9000/article)

### Documentation

Technology stack: Java-1.11.0, Spring-Boot 2.5.6, in-memory database H2 1.0, Tomcat 9.0.54.

In order to launch the application on Ubuntu 20.04 operating system do the following:

1.Open terminal and do command "sudo apt update"

2.Install java 11 "sudo apt-get install openjdk-11-jdk"

3.Install maven "sudo apt install maven"

4.Check the installation "mvn -version"

5.Copy this repository form github

6.Goto to the directory with the project and run "mvn clean install"

7.Run the app "mvn spring-boot:run"

8.Go to (http://localhost:9000/) in your browser.

