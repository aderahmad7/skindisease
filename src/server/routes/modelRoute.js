const {
  postPredictHandler,
  getHistoriesHandler,
} = require("../handler/modelHandler");

const routes = [
  {
    path: "/predict",
    method: "POST",
    handler: postPredictHandler,
    options: {
      payload: {
        maxBytes: 1000 * 1000,
        allow: "multipart/form-data",
        multipart: true,
      },
    },
  },
  {
    path: "/predict/histories",
    method: "GET",
    handler: getHistoriesHandler,
  },
];

module.exports = routes;
