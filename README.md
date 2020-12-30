# Revature Project 2: One Source Delivery

## Overview
This repo contains an angular project and spring webapp application that are used to run the One Source Delivery website. This is a mock application meant to mimic grocery delivery sites like Instacart or Shipt. It allows users to sign up and browse grocery stores in their areas, build a cart, and pay for their selected groceries to be delivered to them. For the scope of this project we only supported Wal Mart and Kroger and used their respective APIs to retrieve data on available products. I personally think this approach was a little limited for a real life application, because it meant that we could only service companies that had working/accurate APIs, but it was challenging and interesting to implement and one of our group members (Dominic) worked very hard to get it to work.

## Features
- Users can verify that One Source services their zip code and then create an account to browse stores.
- Users can view a list of One Source supported stores in their area and can select one to start building an order.
- Users can browse items listed in their selected stores and add them to a cart
- Users can enter payment information and checkout their order
- Users can view their past orders

## Technologies used
- Java with Spring MVC and hibernate for the backend business logic
- AWS PostgreSQL database for storage
- Angular 8 application with Bootstrap for the frontend.
- Server hosted with TomCat

## How to set up the project
### Server
1. Clone repo and open up the Intellij Project in the Shop Sim Server folder.
2. Use Smart Tomcat or IntelliJ pro to create a Tomcat configuration and set the deployment directory to 'RevatureProject2/ShopSim-Server/src/main/webapp' and set the Context Path to '/ShopSim'
3. Go into the Maven tab of the project and run Maven package to create the WAR.
4. Create an AWS Relational Database and link the webapp to it by creating a db.properties file in the src/main/resources folder with the following format:
```
db.url= {{AWS database url}} 
db.username={{AWS username}}  
db.password={{AWS password}}
```

5. Run the project on the TomCat configuration and the spring webapp should boot up. The server should now be able to process httprequests on localhost:8080

 ### Client
 1. Open a command prompt/terminal in the Shop Sim Client folder
 2. Run npm install to install all of the necessary npm modules
 3. Go to ShopSim-Client/src/environments and create a file called environment.prod.ts.
 4. Create a [Kroger API account](https://developer.kroger.com/) and a [Walmart API account through RapidAPI](https://rapidapi.com/axesso/api/axesso-walmart-data-service) and put the api information in the environment,prod.ts file in the following format:

 ```
 export  const environment = {

production: true,

kroger: {

apiUrl: 'https://api.kroger.com/v1',

authUrl: 'https://api.kroger.com/v1/connect/oauth2/token',

clientId: '{{your kroger client id}}',

clientSecret: '{{your korger client secret}}'

}, walmart: {

apiKeywordUrl: 'https://axesso-walmart-data-service.p.rapidapi.com/wlm/walmart-search-by-keyword?',

apiProductUrl: 'https://axesso-walmart-data-service.p.rapidapi.com/wlm/walmart-lookup-product?',

apiKey: '{{your walmart axesso API key}}',

host: 'axesso-walmart-data-service.p.rapidapi.com'

}

};
```
5. Run ng serve -o in the angular project directory, the angular project should now be running on localhost:4200 and be able to interact with the server on localhost:8080

## What I was responsible for
I have a good understanding of all the different technologies in this project and have walked through them with my teammates, however in terms of coding I only did front end development. I setup the angular project and made the initial versions and base functionality of most web pages for the site. I made the navbar used to navigate the site and developed the way that orders, and stores were displayed. I implemented the zip code checking functionality, helped implement logging in, and made logins persistent through the use of cookies. My teammate Genaro then went through added/updated CSS styling with Bootstrap to all of the web pages, and I worked with him to refactor and bug fix the item search and cart web pages.

## Contributors:
Dominic Marosok - Backend and Kroger/Wal-Mart integration
 Joseph Feldmann - Front end Angular app
 Genaro Barerra - Some backend and improving Front end + CSS
