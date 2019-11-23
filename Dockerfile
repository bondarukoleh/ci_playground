FROM node:10.17

WORKDIR /app

COPY ./src .
COPY ./package-lock.json .
COPY ./package.json .

RUN npm i --no-audit

EXPOSE 3000

CMD npm start