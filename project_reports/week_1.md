Github Project Link

https://github.com/1-8192/CS763_project
Overview

The original project I will be working on is Trackr. It was a group project for CS 673 and is  a simple application that allows users to track transaction against their bank account, so they can better understand their spending behaviors [1]. The application is built using Spring Boot and React. It is an appropriate project because the application is a relatively simple single page application. The functionality and code should not be overly difficult to understand. The application implements some light authorization and security features using jwt tokens through Auth0. It also implements an H2 Java database and JPA to store permanent data. Additionally, the backend is written in the Java Spring Boot framework, with which I am already somewhat familiar.

Existing Functionalities

User Account creation

	The Tracks app allows a user to create a new account. The user provides their name, username, email address, and a password to create an account. The inputs have some light validation implemented using Spring Boot annotations. Once the account is created, the user is moved to the homescreen. 


User log in

Edit Profile Information

	Once a user has created an account, they can edit account information. 


Add/edit/delete bank account

	Users can add, edit, and delete multiple bank accounts on the application. Accounts belong to a few pre-determined categories, like CHECKING or SAVINGS. Users can set account balances, as well. 


Add/edit/delete transaction tied to accounts

	The Trackr app’s core functionality is adding transactions to track balances in user accounts. The user can add a transaction and tie it to any of their already created accounts. It does not look like accounts balances get updated correctly with new transactions unless the user specifically edits the balance under the “Accounts” tab.


Existing Security Features

	The Trackr app uses Java JWT to impersonate JSON tokens for authentication and session management purposes. Java KWT library is part of the com.Auth0 group [4]. JSON Web Token (JWT) is the standard for sharing information over internet connections [6]. JWT tokens contains encrypted payloads digitally signed by a private key [6]. The application also hashes the user provided password for database storage.
Additionally, The Trackr application contains some light validation for certain model fields, like password, implemented using Spring Boot annotation, which might fall into the compliance category. That seems to be all of the current security features, as the app does not encrypt and protect information stored in the database. JPA may do some light encryption by default, but the application does not currently do any manual encryption for stored data. As a quick example, it seems that JPA Attribute Converters could be used to encrypt data for a future additional security feature [7].
Current implementation stack and build process

The Trackr application is built with Java Spring Boot and a React front end [1]. The repository Readme outlines a few different ways to run the application locally, like through an IDE or mvn CLI commands [1]. I was able to build and run the project locally using docker; however, I did need to add a .babelrc file containing some presets configs to the root of the project in order for it to build properly. The application can easily be built and run by running docker-compose build and docker-compose up in a local terminal. Please see the screenshot to show the homepage on localhost below:



Docker

The existing application already contained a functioning dockerfile in the repository. I confirmed it builds and runs locally (see above as I had to make one tiny adjustment).
Security Tools Research

Sonarqube

	Sonarqube is a Static Application Security Testing tool (SAST) that provides additional code quality measurement reports. It is “a self-managed, automatic code review tool that systematically helps you deliver Clean Code” [2]. Sonarqube integrates into several stages of software development to provide linting during development through an IDE plugin, PR analysis as part of a CI/CD pipeline, and implementing quality gates to block code that fails quality baselines from hitting production [2]. SonarQube supports over 30 programming languages and provides a comprehensive set of tools for static code analysis.
SonarQube performs static code analysis by scanning the source code and identifying various issues, such as code duplications, coding standards violations, security vulnerabilities, and other potential problems. It generates detailed reports and metrics, allowing development teams to track their progress in addressing these issues and maintain code quality over time. The platform integrates with popular build and continuous integration tools, ensuring that code quality checks can be seamlessly incorporated into the development workflow [2].

OWASP ZAP

	OWASP ZAP (now just ZAP) is a penetration testing tool and open source application scanner [3]. ZAP (Zed Attack Proxy) is specifically tailored for web application security testing and is a powerful tool for finding issues like cross-site scripting (XSS), SQL injection, and other common web application vulnerabilities [3]. It provides both automated and manual testing capabilities, making it suitable for a wide range of users, from beginners to seasoned security experts.
ZAP offers features such as automated scanning, spiders for crawling web applications, and the ability to intercept and modify HTTP requests and responses. It also provides a wide range of add-ons and extensions to enhance its functionality, making it a flexible tool for different use cases. ZAP's active community and regular updates ensure it stays up to date with the latest web application security threats and best practices [3].

Clair Container Security Scanner

	Clair is an open-source container scanning tool designed to enhance the security of containerized applications by identifying vulnerabilities in the container images [5]. Ensuring the security of container images has become critical as container technology, like Docker, has grown in popularity. Clair addresses this concern by performing static analysis of container images, specifically looking for known security vulnerabilities in the software components within the image [5].
The core functionality of Clair revolves around comparing the components within a container image against a database of known vulnerabilities. It integrates with container orchestration platforms like Kubernetes, allowing developers and operators to automatically scan images as they are built or deployed [5]. When vulnerabilities are detected, Clair provides detailed information about the issue and its severity within a report.

Github Action Workflow

Sonarqube *Incomplete

	I spent a good bit of time working through setting up a Github action workflow for Sonarqube, but, unfortunately, I have not been able to get it to work yet. I have tried many different configurations, but the sonarqube scanner does not seem to be able to connect to the sonarqube server I am running in the action workflow container no matter what I try: https://github.com/1-8192/CS763_project/actions/workflows/sonarqube.yml. I have tried using both the maven yml configs and the other language configs from the sonarqube docs. All my attempts show the error ERROR: SonarQube server [***] can not be reached Below is a screenshot of sonarqube running locally just to show that the UI part went according to plan. I plan to continue working on this for the next project section.



ZAP

	Because I could not get the Sonarqube action to work, I then tried implementing a ZAP scanner Github action workflow. The DAST scan did work. Here is a link to the .yml file: https://github.com/1-8192/CS763_project/blob/main/.github/workflows/zap.yml. I set up the action to run the application locally and then run the ZAP scan on localhost. 
The artifact is available to download from GH here as a zip file that contains the report. The report shows a couple of Medium level alerts around Header settings for the application.





Publishing Docker Image

	I was able to successfully implement a Github action to publish docker images to Dockerhub on new version releases. Since the repo already had a dockerfile set up, I decided to add in the image publishing action to get some docker work in. You can see below screenshots of the action running successfully, and the published image on Dockerhub. Here is a link to the appropriate .yml file: https://github.com/1-8192/CS763_project/blob/main/.github/workflows/images.yml




References

[1] Trackr Application. https://github.com/BUMETCS673/group-project-team1/

[2] Sonarqube Documentation. https://docs.sonarsource.com/sonarqube/latest/

[3] ZAP Documentation. https://www.zaproxy.org/getting-started/

[4] Java JWT MVN Dependency. https://mvnrepository.com/artifact/com.auth0/java-jwt

[5] “What is Clair”. Redhat. https://www.redhat.com/en/topics/containers/what-is-clair. January 8, 2019.

[6] Das, Sayan. “Implementing JSON Web Token (JWT) Authentication using Spring Security | A Detailed Walkthrough”. Medium. https://medium.com/geekculture/implementing-json-web-token-jwt-authentication-using-spring-security-detailed-walkthrough-1ac480a8d970. December 23, 2021.

[7] Janssen, Thorben. “How to use a JPA Attribute Converter to encrypt your data” https://thorben-janssen.com/how-to-use-jpa-type-converter-to/
