FROM gradle
COPY ./ ./
ENV SPRING_PROFILES_ACTIVE=prod
RUN ./gradlew clean build
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/giftcertificate-0.0.1.jar"]
