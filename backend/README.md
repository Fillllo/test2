# Backend

Spring Boot 3.5 REST API for team calendar events. Connects to PostgreSQL.

## Running

1. Ensure PostgreSQL is running and create a database named `calendar`.
2. Update `src/main/resources/application.properties` if needed.
3. Build and run:

```bash
mvn spring-boot:run
```

The API expects event `startTime` and `endTime` values in
`yyyy-MM-dd'T'HH:mm` format. Seconds may be provided as well.
