# base image
FROM node:12.10.0

# set working directory
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

# install and cache app dependencies
COPY package.json /app/package.json
RUN npm install -g @angular/cli@7.3.9
RUN npm install

# add app
COPY . /app

# start app
CMD ng serve --proxy-config proxy.docker.conf.json --host 0.0.0.0