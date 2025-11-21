import axios from 'axios'

const httpClient = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    "Cache-Control": "no-cache",
    "Content-Type": "application/json;charset=utf-8",
    "Access-Control-Allow-Origin": "*",
  },
});

export default httpClient;
