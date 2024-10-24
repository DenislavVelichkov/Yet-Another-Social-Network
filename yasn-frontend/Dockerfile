FROM node:16

RUN mkdir -p /app

WORKDIR /app

COPY . /app/

COPY package*.json /app/

RUN #npm install
RUN npm install --legacy-peer-deps

EXPOSE 4200

ENTRYPOINT ["npm", "start"]
