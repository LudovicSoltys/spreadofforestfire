import httpClient from './HttpClient';

const END_POINT = '/board';


const getCurrentBoard = () => httpClient.get(END_POINT);

const resetBoard = (width, height) => httpClient.post(END_POINT, { attributes: { width, height }});

export {
  getCurrentBoard,
  resetBoard
}
