FROM maven:3.6.1-jdk-8
# add app
COPY . /app

# set working directory
WORKDIR /app

# fetch dependencies and build the app
RUN mvn clean install -DskipTests -P docker

# run the application
ENTRYPOINT ["mvn","spring-boot:run", "-P docker"]