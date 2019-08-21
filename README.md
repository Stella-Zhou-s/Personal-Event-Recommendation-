# Personal Event Recommendation 

## Business Design
- To design a personalization based event recommendation systems for event search.

## General Instruction
- Design a web service with `RESTful APIs` in Java to handle HTTP requests and responses
- Frontend: an interactive web page with `AJAX` technology implemented with `HTML`, `CSS` and `JavaScript`. The Event Recommendation Website realizes three main functions:
   * **Search** events around users
   * **Favorite** events they like and also delete events they don’t like anymore
   * Get **recommendation of events** around based on their favorite history and distance to where events will be hold
- Backend: use `Java` to process logic request, and some supports are as below:
   * Built with both relational database and NoSQL database (`MySQL` and `MongoDB`) to support data storage from users and items searched in TicketMaster API
   * Design **content-based recommendation algorithm** for event recommendation
- Deploy website server on `Amazon EC2`: [Event Recommendation System](http://52.24.237.51/EventRecommend/)
- Analyze website traffic both online and offline with ELK (`ElasticSearch`, `Logstash` and `Kibana`) and `MapReduce` in MongoDB

## Infrastructure Design
- 3-tier architecture
   * Presentation tier: HTML, CSS, JavaScript
   * Data tier: MySQL, MongoDB
   * Logic tier: Java
- Local and remote development environment

![local environment](https://github.com/donghai1/Personal-Event-Recommendation-/tree/master/demo/local.png)
> Local development environment

![remote environment](https://github.com/donghai1/Personal-Event-Recommendation-/tree/master/demo/remote.png)
> Remote development environment

## API Design
- Logic tier(Java Servlet to RPC)
   * Search
      * searchItems
      * Ticketmaster API
      * parse and clean data, saveItems
      * return response
   * History
      * get, set, delete favorite items
      * query database
      * return response
   * Recommendation
      * recommendItems
      * get favorite history
      * search similar events, sorting
      * return response
   * Login
      * GET: check if the session is logged in
      * POST: verify the user name and password, set session time and marked as logged in
      * query database to verify
      * return response
   * Logout
      * GET: invalid the session if exists and redirect to `index.html`
      * POST: the same as GET
      * return response
   * Register
      * Set a new user into users table/collection in database
      * return response

![APIs design](https://github.com/donghai1/Personal-Event-Recommendation-/tree/master/demo/APIs.png)
> APIs design in logic tier

- TicketMasterAPI
[Official Doc - Discovery API](https://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2/)
- Recommendation Algorithms design
   * **Content-based Recommendation**: find categories from item profile from a user’s favorite, and recommend the similar items with same categories.
   * Present recommended items with ranking based on distance (geolocation of users)

![recommendation algorithm](https://github.com/donghai1/Personal-Event-Recommendation-/tree/master/demo/recommendation.png)
> Process of recommend request

## Database Design
- MySQL
   * **users** - store user information.
   * **items** - store item information.
   * **category** - store item-category relationship
   * **history** - store user favorite history

![mysql](https://github.com/donghai1/Personal-Event-Recommendation-/tree/master/demo/mysql.png)
> MySQL database design

- MongoDB
   * **users** - store user information and favorite history. = (users + history)
   * **items** - store item information and item-category relationship. = (items + category)
   * **logs** – store log information

## Implementation Details
- Design pattern
   * **Builder pattern**: `Item.java`
      * When convert events from TicketMasterAPI to java Items, use builder pattern to freely add fields.
   * **Factory pattern**: `ExternalAPIFactory.java`, `DBConnectionFactory.java`
      * `ExternalAPIFactory.java`: support multiple function like recommendation of event, restaurant, news, jobs… just link to different public API like TicketMasterAPI. Improve extension ability.
      * `DBConnectionFactory.java`: support multiple database like MySQL and MongoDB. Improve extension ability.
   * **Singleton pattern**: `MySQLConnection.java`, `MongoDBConnection.java`
      * Only create specific number of instance of database, and the class can control the instance itself, and give the global access to outerclass

## User Behavior Analysis
- Online (**ElasticSearch**, **Logstash**, **Kibana**)
   * Use Logstash to fetch log (in NoSQL-like form), then store data in ElasticSearch, finally use Kibana to analyze the data in ElasticSearch, getting some tables and graphs like APIs use, request status, geolocation of visitors, etc

![ELK analysis](https://raw.githubusercontent.com/Wangxh329/EventRecommendation/master/img_font_icon_sources/doc/elk.png)
> Remote development environment

- Offline (**MapReduce in MongoDB**)
   * Copy-paste some logs from Tomcat server
   * Purify log data and store in MongoDB
   * Do ``mapreduce()`` in MongoDB
   * Get a list of timebucket-count in descending order of count, then find the peak time of website traffic
