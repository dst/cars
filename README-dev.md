# Development

## Lombok
You need Lombok plugin for IntelliJ or Eclipse, otherwise you will see a lot of compilation errors.
Enable annotation processing in Settings -> Compiler -> Annotations processors

## New library
- Add gradle dependency
- Download gradle dependency: $ ./gradlew assemble
- Add IDE dependency:
    - IDEA: ./gradlew idea
    - Eclipse: ./gradlew eclipse

## Code quality
    
### Code coverage
    $ ./gradlew jacocoTestReport
    $ browser build/reports/jacoco/test/html/index.html

### Tests
    $ ./gradlew test
    $ browser build/reports/tests/index.html
    
### All at once
    $ cd tools
    $ ./generate-reports.sh
