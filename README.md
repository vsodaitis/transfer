# transfer

### WMBFAQ (What Might Be Frequently Asked Questions)

- **What is this?**\
  A simple REST microservice implementation using Quarkus framework (https://quarkus.io)
- **How to run this in dev (JVM) mode?**\
  Invoke `./mvnw compile quarkus:dev`. Now you have it running on 8080 port
- **How to produce a native executable?**\
  You need to have GraalVM installed and `GRAALVM_HOME` environment variable set. Then you can run `./mvnw package -Pnative`. The resulting executable will reside in `target` directory.
- **Why Java 8?**\
  Currently GraalVM which is needed to produce a native executable doesn't support newer Java versions. This should change pretty soon.
- **What API's are available?**\
  You can check available API's in http://localhost:8080/swagger-ui 
- **What DB technologies are used?**\
  Quarkus has full Hibernate support (with their homemade Panache extension). So this was used in conjunction with H2 in-memory database.
- **Why Maven?**\
  Gradle plugin is broken, when using Panache extension at the moment. As you can guess, this is not a very mature framework yet.

### TODO

- Implement the same microservice with Spring Boot and Spark. After this is done, some performance benchmarks and comparisons can be done
 