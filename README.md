# transfer

### WMBFAQ (What Might Be Frequently Asked Questions)

- **What is this?**\
  A simple REST microservice implementation using Quarkus framework (https://quarkus.io)
- **How to run this in dev (JVM) mode?**\
  Invoke `./gradlew quarkusDev`. Now you have it running on 8080 port
- **How to produce a native executable?**\
  You need to have GraalVM installed and `GRAALVM_HOME` environment variable set. Then you can run `./gradlew buildNative`. The resulting executable will reside in `build` directory.
- **Why Java 8?**\
  Currently GraalVM which is needed to produce a native executable doesn't support newer Java versions. This should change pretty soon.
- **What DB technologies are used?**\
  Quarkus has full Hibernate support (with their homemade Panache extension). So this was used in conjunction with PostgreSQL database.

### TODO

- Performance benchmarks and comparisons with spring boot microservice can be done
 