Report

Consider possible adversaries, abuse scenarios and vulnerabilities to exploit. Create at least 5 possible abuse cases or user stories

	The current Trackr application consists of manually created and managed bank accounts, as a very basic budgeting application, so it would not offer a lot of truly sensitive information for an attacker. In order to create more meaningful attack scenarios, I have expanded the system’s capabilities in this report to include storing actual bank account information, like account numbers. Additionally, a significantly more mature system that connects to external banks through a network to source information would provide much more incentive for a malicious user to attack the system. For the rest of this report I am building threat models and analyzing security weaknesses based on these expanded capabilities though they are not implemented yet in the code. 
	A few possible adversaries would be:
	A threat group interested in making the system unusable for legitimate users to extract a random from system administrators.
	A malicious user attempting to gain unauthorized access to legitimate user accounts to gain sensitive information, like bank account numbers or email addresses.
	A malicious user attempting to compromise transaction information stored in the database. This could be done again in a ransom scenario, or simply to sow chaos.

	Using the above general adversary types, I have constructed the below 5 abuse cases. 


1.	As a malicious user, I want to exploit weak authentication mechanisms to gain access to user accounts, so that I can collect their stored bank account information.

2.	As a hacker group, I want to overwhelm the Trackr application service with excessive requests, so that I can render the application unusable for legitimate users.

3.	As a hacker group, I want to exploit the lack of database encryption, so that I can gain unauthorizes access to legitimate user information, such as transaction details.


4.	As a hacker group, I want to compromise account transaction details through injection attacks, so that I can disrupt account balances.

5.	As an attacker, I want to execute malicious scripts in legitimate users' browsers, so that I can gain access to user information.

Use those abuse cases as the root of attack trees to show detailed possible paths to achieve them. Try to have at least 3 levels of depth in your attack tree. Each attack tree lists subgoals or conditions in a hierarchy structure to achieve the ultimate goal of the attack described by the root node, with the leaf nodes being specific, tangible actions, steps, conditions, or outcomes that an attack must achieve first. The leaf nodes can be specific well-known vulnerability exploits or attacks. The leaf nodes should be easily assessed in terms of possibility, complexity, and cost, so the risk analysis can be done on the root nodes.

Lucidchart Link: https://lucid.app/lucidchart/b74f21b9-0a8b-4881-be71-3d2a5cc9b08a/edit?view_items=BINOr881wuN1&invitationId=inv_f8a0a9f9-1fc4-4ffd-aea4-97b0a23970b7

1.	As a malicious user, I want to exploit weak authentication mechanisms to gain access to user accounts, so that I can collect their stored bank account information.


I used reference [1] for this attack tree.

2.	As a hacker group, I want to overwhelm the Trackr application service with excessive requests, so that I can render the application unusable for legitimate users.


I used references [2] and [3] to construct the attack tree.

3.	As a malicious user, I want to exploit the lack of database encryption, so that I can gain unauthorizes access to legitimate user information, such as transaction details.

I used reference [4] to find DB exploit strategies for this attack tree.


4.	As a hacker group, I want to exploit the lack of database encryption, so that I can gain unauthorizes access to legitimate user information, such as transaction details.

I used reference [5] for this attack tree.

5.	As an attacker, I want to execute malicious scripts in legitimate users' browsers, so that I can gain access to user information.


I used reference [8] to help build this attack tree.

Identify existing and additional security requirements to help mitigate those abuse cases.

Existing Security Requirements

The system does not currently have many security requirements, but they are:

1.	A user shall create a password to use for authentication.

2.	A user shall only have access to a bank account that is tied to that user.

Additional Security Requirements

	One weakness that revealed itself in multiple attack tree nodes is the simplicity of the passwords used in the Trackr system. Requiring 12 characters of numbers, upper case, and lower case letters for a password increases the amount of time needed to brute force a password from immediate to 24 years [4]. I have created a few new requirements around user passwords. 

1.	A user password shall be required to be 12 characters long and contain numbers, lower case letters, and upper case letters.

2.	After 3 failed login attempts, a user shall be blocked from logging into the system again for 5 minutes.

3.	The database layer shall be encrypted.

4.	The service shall be deployed behind a firewall to control traffic.

5.	The service shall use a Content Distribution Network (CDN) to limit and control network traffic.

6.	The service shall validate all user input.

7.	JWT tokens shall have an expiration time of one hour maximum.


List all entry points and API endpoints. Identify possible attack vectors among those interfaces.

Trackr is a Single Page Application, so only one endpoint is technically “exposed” to external clients through the application itself. However, there are more REST endpoints that can be queried directly by bypassing the REACT front end, even through a service like Postman. Example below:



API Endpoints

/Index
This is the main API endpoint for the front end interface and is accessible from the front end client.

Additional Endpoints
There are many more controller endpoints in the app that are masked by Trackr being a Single Page Application, but they are accessible from external REST requests. There are too many to list individually, and the endpoints are grouped into a few different controllers like the BaseController, BankAccountController, and TransactionController classes. I was surprised by how many endpoints the application featured given its simplicity, but it is a good example that modern web application API’s tend to spiral into large attack surfaces. To give an idea…

findAll
findAllTransactionById
createTransaction
deleteBankAccount
modifyTransaction
etc…


Other Entry points

Database

HTTP Headers

Form Data


Attack Map:
Again, I did not list out all of the API endpoints because there were so many, but I included enough to give a good idea of the attack surface, which is quite large given the scope of the application.



Understand the project architecture, identify all components and draw a DFD diagram, which includes data flow, data store, process, interactor/endpoints, and trust boundary. Then perform a STRIDE threat modeling by checking if any component in the diagram is subject to any of these 6 threat categories. List possible threats and the corresponding mitigations.

Components

	The main components of the Trackr system are:

Web Client

		This component does not exist yet, as the Trackr application is not a live, deployed service. In a future, mature state though the overall system would feature web clients that users use to interface with the rest of the system through their machines connected to a network.

Threat categories: spoofing

Possible mitigation strategies: strong passwords, user authentication


Web Server
This component is also theoretical for a deployed version of Trackr, but it would represent the web server that the Trackr Application is deployed to. There are many implementation options here; a common web application architecture know would be a cloud-based containerized deployment of virtual machines on Azure, or Google Cloud services.

	Threat categories: Denial of service, spoofing

Possible mitigation strategies: CDN network or load balancing to limit requests,    strong passwords.


React Front End

	This is the front end component that defines the User Interface the user interacts with.

	Threat categories: spoofing

Possible mitigation strategies: strong passwords

Java Spring Boot Service

	The core logic of the Trackr application belongs to this component. Java Spring Boot provides a REST API connected to a database to run logic on user requests and persists the data. The service uses an ORM, so stored data is also vulnerable from the Java layer. 

Threat categories: tampering, spoofing, repudiation, information disclosure

Possible mitigation strategies: robust user authentication, logging



H2 Database

	The above Spring Boot service is connected to an H2 Database through the JPA ORM. This component stores and persists all the system’s data, like user profile information, passwords, and bank account information provided by legitimate users. Because the database holds all the system’s persistent data, it is vulnerable to basically all STRIDE categories. 

Threat categories: tampering, repudiation, information disclosure,
elevation of privilege

Possible mitigation strategies: implement database level encryption, secure connections with SSL, roles, access control [6]


Data Flow Diagram

Lucidchart Link: https://lucid.app/lucidchart/b74f21b9-0a8b-4881-be71-3d2a5cc9b08a/edit?viewport_loc=974%2C494%2C2086%2C1219%2C0_0&invitationId=inv_f8a0a9f9-1fc4-4ffd-aea4-97b0a23970b7



References

[1] Li, Vicki. “Hacking JSON Web Tokens (JWTs)”. Medium. https://medium.com/swlh/hacking-json-web-tokens-jwts-9122efe91e4a. Oct 27, 2019.

[2] “What is DDos Attack?”. Fortnet. https://www.fortinet.com/resources/cyberglossary/ddos-attack

[3] Nsrav. “Denial of Service” OWASP. https://owasp.org/www-community/attacks/Denial_of_Service

[4] “6 Types of Database Hacks Use to Obtain Unauthorized Access.” Salvation Data. https://www.salvationdata.com/crime-cases/6-types-of-database-hacks-use-to-obtain-unauthorized-access/. June 13, 2022.

[5] “SQL Injection”. OWASP. https://owasp.org/www-community/attacks/SQL_Injection

[6] “Securing your H2”. H2 Database Docs. https://www.h2database.com/html/security.html

[7] “Attack Surface Analysis Cheat Sheet”. OWASP Cheat Sheet Series. https://cheatsheetseries.owasp.org/cheatsheets/Attack_Surface_Analysis_Cheat_Sheet.html

[8] “Cross Site Scripting.” PortSwigger. https://portswigger.net/web-security/cross-site-scripting
