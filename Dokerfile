FROM node:10.17

WORKDIR /app

COPY ./src .

RUN npm i --only=production --no-audit

EXPOSE 3000

CMD npm start