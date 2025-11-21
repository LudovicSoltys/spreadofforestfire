import httpClient from './HttpClient';

const END_POINT = '/fires';

const getAllFires = () => httpClient.get(END_POINT);

const createFires = (simulate, values) => httpClient.post(END_POINT, { dryRun: simulate, targets: values });

const nextFires = (simulate) => httpClient.post(END_POINT + "/next", { dryRun: simulate});

export {
  getAllFires,
  createFires,
  nextFires
}
