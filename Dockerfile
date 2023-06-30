FROM gradle
COPY ./ ./
ENV SPRING_PROFILES_ACTIVE=prod
RUN ./gradlew clean build --exclude-task=test
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "build/libs/giftcertificate-0.0.1.jar"]
