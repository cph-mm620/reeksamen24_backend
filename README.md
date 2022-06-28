*This project requires that you have a functional droplet up and running with tomcat and SQL

# How to use the startcode

## To deploy with a push to github

### Change the following line in the mavenworkflow.yml file

in line 5

```yml
  - master
```

to

```yml
  - main
```

and then line 41

```yml
  mysql database: 'startcode_test'
```

to

```yml
  mysql database: 'your test database'
```

### Change the following lines in the pom.xml file

in line 6

```html
    <artifactId>devops-starter</artifactId>
```

to

```html
    <artifactId>name of project</artifactId>
```

in line 19

```html
    <remote.server>Your domain name here</remote.server>
```

in line 23

```html
    <db.name>Your database name here</db.name>
```

***

#### change secrets

go to your github project settings, secrets, actions and make 2 secrets

REMOTE_USER

and set it to the username of your tomcat

REMOTE_PW

and set it to the password for your tomcat

***

### Making a database

you can make an empty database, then change the database and password in the persistance file

and then you can run the main method in the Populator (src/main/java/facades/Populator.java)

this can generate dummy data in the database, all you need to do is change the userName and pass

remember not to put that on github if it is sensitive

you can do the same with the test database (src/main/java/utils/SetupTestUsers.java)

When you store passwords in the database they get hashed so that if someone hacks the database they cant see your real password

***

### REST endpoints in the startcode

#### api/login

- to log in

#### api/info/all 

- gets the number of users

#### api/info/user

- only a user role allowed 

- gives a message {msg: "Hello to User: " + currentUser + "}

#### api/info/admin

- only an admin role allowed

- gives a message {msg: "Hello to (admin) User: " + currentUser + "}
***

### Where is stuff in this project

src/main/java/entities ( the entities I made + the ones from start code (User, Role) )

src/main/java/dtos ( the DTOs I made )

src/main/java/facades/Facade.java ( the facade for ManySide CRUD )

src/test/java/facades/FacadeTest.java ( tests for all facade functions )

src/main/java/security/LoginEndpoint.java 
( the login function and endpoint, I have added a little to it, so I can see what roles the users have when they log in, ther is a way to get that from the token, but I don´t know how to do that )

src/main/java/security/SignUpEndpoint.java ( the signup function and endpoint )

src/main/java/rest/DemoResource.java ( the start code functions )

src/main/java/rest/MyResource.java ( a resource I made, with all the CRUD endpoints )

src/main/java/rest/ApplicationConfig.java ( where you add resources, I added min on line 29 )


------------------------------------------------------------------------------------------------------------------------
*This project is meant as start code for projects and exercises given in Flow-1+2 (+3 using the security-branch) at http://cphbusiness.dk in the Study Program "AP degree in Computer Science"*

*Projects which are expected to use this start-code are projects that require all, or most of the following technologies:*
- *JPA and REST*
- *Testing, including database test*
- *Testing, including tests of REST-API's*
- *CI and CONTINUOUS DELIVERY*

## Flow 2 week 1

### Preconditions
*In order to use this code, you should have a local developer setup + a "matching" droplet on Digital Ocean as described in the 3. semester guidelines*

### Getting Started

This document explains how to use this code (build, test and deploy), locally with maven, and remotely with maven controlled by Github actions
- [How to use](https://docs.google.com/document/d/1rymrRWF3VVR7ujo3k3sSGD_27q73meGeiMYtmUtYt6c/edit?usp=sharing)

### JPA snippets

### Setup in Intellij
- open view->too windows->persistence
- open the Database tab and create a new data source (remember to point to a database event though this is already written in the persistence unit. This is necessary in order to use the JPQL console)
- in the persistence window right click the pu or an entity and choose "console"
- write a jpql query in the console and execute it.
### In netbeans it is much simpler
- just right click the pu and choose: "Run JPQL query"

### Create model in workbench (cannot be done from Intellij - No model designer yet)
- file-> new model
- dobbelclick the mydb icon and change to relevant database (create one first if needed)
- click the Add Diagram icon
- click the table icon in the left side panel and click in the squared area to insert new table
- dobbelclick the new table and change name and add columns (remember to add a check mark in 'ai' for the primary key)
- do the process again to add a second table
- now in the panel choose the 'non identifying relationship' on to many
- click first on the child table (the one that should hold the foreign key) and then on the parent. A new relationship was now added.
- When done with designing - goto top menu: Database->forward engineer.
  - Check that all settings looks right and click continue
  - click continue again (no changes needed here)
  - Make sure the 'Export mysql table objects' is checked and Show filter to make sure that all your tables are in the 'objects to process' window -> click continue
  - Verify that the generated script looks right -> click continue
  - click close and open the database to see the new tables, that was just created.

### create entities from database in Intellij (Persistence mappings)
- From inside the Persistence window:
- Right-click a persistence unit, point to Generate Persistence Mapping and select By Database Schema.
- Select the
  - data source
  - package
  - tick tables to include
  - open tables to see columns and add the ones with mapped type: Collection<SomeEntity> and SomeEntity
  - click OK.

### In netbeans it is much easier
- Right click project name -> new -> persistence -> Entity classes From Database -> choose database connection from list -> add the tables you need -> Finish



