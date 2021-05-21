FROM node:12

RUN mkdir -p /app

WORKDIR /app

COPY . /app/

COPY package*.json /app/

RUN npm install

EXPOSE 4200

ENTRYPOINT ["npm", "start"]
