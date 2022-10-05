[![Java CI with Maven](https://github.com/coderhop-libraries/spring-rebound/actions/workflows/maven.yml/badge.svg)](https://github.com/coderhop-libraries/spring-rebound/actions/workflows/maven.yml)
# spring-rebound
Library for providing exception handling for spring based REST APIs

This lib aims at providing extension to `ResponseEntityExceptionHandler` and handle all 4xx as well as 500 exception.

### What spring-bound does

spring bound provide a simple exception handling for user exceptions(4xx). Here is the defined Exception classes for most used USER Errors.

| HTTP ERROR CODE | STATUS                | EXCEPTION CLASS       |
| --------------- | --------------------- | --------------------- |
| 400             | BAD REQUEST           | BadRequestException   |
| 401             | UNAUTHORIZED          | UnauthorizedException |
| 403             | FORBIDDEN             | ForbiddenException    |
| 404             | NOT FOUND             | DataNotFoundException |
| 500             | INTERNAL_SERVER_ERROR | SystemException       |

```json
{
    "errorMessage": "Internal Sever Error : Please Try later[Refer logs for details]",
    "category": "Internal Server Error",
    "httpErrorCode": 500,
    "transactionId": "9779f2387e8f3143",
    "timestamp": "2022-10-04T16:10:09.345+00:00"
}
```

Above is the Exception Message generated by spring-rebound.



### How To Use

- [x] Add soring rebound dependency

```
	<dependency>
	<groupId>com.coderhop</groupId>
	<artifactId>spring-rebound</artifactId>
	<version>1.0.7</version>
	</dependency>
```

- [x] Make sure that `com.coderhop` is added in `ComponentScan` in order to add exception handling classes.

```
@ComponentScan("com.coderhop")
```

- [x]  As required throw these exception from services/other API classes like below

  ```
  throw new com.coderhop.rebound.exception.model.DataNotFoundException("New Data found for Id: 0001 ");
  //other example
  //throw new com.coderhop.rebound.exception.model.BadRequestException("Invalid Request : Employee Id cant be null");
  //throw new com.coderhop.rebound.exception.model.SystemException("Processing Logic Failed due to connection Failure");
  ```

  Here is the exception message generated for DataNotFoundException

  ```json
  {
      "errorMessage": "New Data found for Id: 0001 ",
      "category": "Not Found",
      "httpErrorCode": 404,
      "transactionId": "3fbad17306c97c6e",
      "timestamp": "2022-10-05T03:00:33.489+00:00"
  }
  ```

- [x] spring-rebound piggy back  distributed tracing library [sleuth](https://spring.io/projects/spring-cloud-sleuth) on transactionId. In case application is not using  [sleuth](https://spring.io/projects/spring-cloud-sleuth) it will return http request sessionId as transactionId.

  
