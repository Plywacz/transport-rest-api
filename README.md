# transport-rest-api
 see endpoints at: https://app.swaggerhub.com/apis/e8197/transport-api/1.0.0#/info

Simple rest-api written in java 11 using Spring Framework. Endpoints secured with Java Web Tokens. App enables you to to add              transits to data base for particular driver. App gets distance between  given addresses itself from remote API                            (http://open.mapquestapi.com). App shares basic crud enndpoints for drivers and some endpoints to  generate simple JSON reports          based on done transits such as: 
  - get report about specific driver that gives informations about total kilometers, longest ever transit, most expensive transit ever       done by this driver.  Moreover it includes informations about distance, longest transit, most expensive transit done per month as a     list. 
  - get raport about average distance, average price, and total distance done by every driver in system in current month to current day.
  - get repot about total price, and total distance done by every driver in system in given period of time

## Main used technologies:
  - Project set up on Spring Boot
  - Java Web Token to secure endpoints
  - Spring Data to obtain infromation from database
  - MySql database
  - simple db model generated with JPA annotations sucha as @OneToMany etc
  - Jackson ObjectMapper to get information from http://open.mapquestapi.com
  
## Testing with postman:
  - Deploy MySql DB on your pc, then configure connection with this DB in your IDE, config file src.main.resources -> "application-sql-       config.yml". **Remember to adjust url to your DB** !!! DB model should be generated automatically by hibernate for you.
  - Register yourself using register enpoint (see info about endpointss https://app.swaggerhub.com/apis/e8197/transport-api/1.0.0#/info)
  - Use registered credentials to to login, when successfully logged, app will return you token which gives you acces to remaining             endpoints
  - Add obtained token to every  Request you send with postman, go to postman, Headers and and "Authorization" key and put obtained value.     Every Authorization key's value must contain "Bearer" word , full value example "Bearer                                                   eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG0iLCJleHAiOjE1ODA3Mzk3MzIsImlhdCI6MTU4MDcyMTczMn0.qq_Pk03s4wN7TVB2OA16FkZc3esEEv3wmS-                   NkYlp7Nb4ofeWCyyHIsSpIofPH_AH7P4MgmVz7nzd-u4mQijSrw".
  - To generate reports: first create driver, then add him some transits then you can generate reports.
  
   


