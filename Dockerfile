FROM openjdk:11-jre-slim

COPY /build/libs/receipt-analysis-*.jar receipt-analysis.jar

CMD ["java", "-jar", "receipt-analysis.jar"]
