const predictClassification = require("../../services/inferenceService");
const { storeData, getData } = require("../../services/storeData");
const crypto = require("crypto");

async function postPredictHandler(request, h) {
  const { image } = request.payload;
  const { model } = request.server.app;

  const { confidenceScore, result, suggestion } = await predictClassification(
    model,
    image
  );
  const id = crypto.randomUUID();
  const createdAt = new Date().toISOString();

  const data = {
    id: id,
    result: result,
    description: description,
    confidenceScore: confidenceScore,
    createdAt: createdAt,
  };

  const response = h.response({
    status: "success",
    message: "Model is predicted successfully",
    data,
  });
  response.code(201);
  return response;
}

async function getHistoriesHandler(request, h) {
  const histories = await getData();
  const result = [];
  histories.forEach((doc) => {
    result.push({
      id: doc.id,
      history: {
        result: doc.data().result,
        createdAt: doc.data().createdAt,
        suggestion: doc.data().suggestion,
        id: doc.data().id,
      },
    });
  });
  return h.response({
    status: "success",
    data: result,
  });
}

module.exports = { postPredictHandler, getHistoriesHandler };
