import httpClient from './HttpClient';

const END_POINT = '/cells';


const getAllCells = () => httpClient.get(END_POINT);

export {
  getAllCells
}
