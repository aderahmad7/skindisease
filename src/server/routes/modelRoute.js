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
        maxBytes: 5000 * 1000,
        allow: "multipart/form-data",
        multipart: true,
      },
      auth: "jwt",
    },
  },
  {
    path: "/predict/histories",
    method: "GET",
    handler: getHistoriesHandler,
    options: {
      auth: "jwt", // Hanya memerlukan token JWT untuk akses
    },
  },
];

module.exports = routes;
