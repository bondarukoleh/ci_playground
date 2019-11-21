const express = require('express')

const app = express()
const port = 3000;

app.get('/', (req, res) => {
  res.send('Hello from Server');
})

const server = app.listen(port, () => {
  console.log(`Sever is running on port ${server.address().port}`)
})