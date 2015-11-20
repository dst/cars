statusListener(OnConsoleStatusListener)

String logPattern = "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"

appender("CONSOLE", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = logPattern
    }
}

appender("FILE", RollingFileAppender) {
    append = true
    file = "logs/cars.log"
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "logs/cars.%d{yyyy-MM-dd}.log.gz"
        maxHistory = 5
    }
    encoder(PatternLayoutEncoder) {
        pattern = logPattern
    }
}

root(INFO, ["CONSOLE", "FILE"])
logger("com.stefanski.cars", DEBUG)

scan("30 seconds")

