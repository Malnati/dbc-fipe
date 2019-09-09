FROM maven:3.6.1-jdk-8
# add app
COPY . /app

# set working directory
WORKDIR /app

RUN mvn clean install -DskipTests -P docker

# package application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","./target/fipe-backend-0.0.1-SNAPSHOT.jar "]