FROM cescoffier/native-base:latest
COPY build/*-runner /application
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]