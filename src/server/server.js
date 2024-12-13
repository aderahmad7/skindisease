require("dotenv").config();
const Hapi = require("@hapi/hapi");
const modelRoutes = require("./routes/modelRoute");
const InputError = require("../exceptions/InputError");

(async () => {
  const server = Hapi.server({
    port: process.env.PORT || 4000,
    host: "0.0.0.0",
    routes: {
      cors: {
        origin: ["*"],
      },
    },
  });

  try {
    const routes = modelRoutes;
    server.route(routes); // Akan dibahas lebih lanjut setelah pembahasan extension.

    server.ext("onPreResponse", function (request, h) {
      const response = request.response;
      if (response.isBoom) {
        const newResponse = h.response({
          status: "fail",
          message: response.output.payload.message, // pesan disini
        });
        newResponse.code(response.output.payload.statusCode);
        return newResponse;
      }
      if (response instanceof InputError) {
        const newResponse = h.response({
          status: "fail",
          message: response.message,
        });
        newResponse.code(response.statusCode);
        return newResponse;
      }

      return h.continue;
    });

    await server.start();
    console.log(`Server start at: ${server.info.uri}`);
  } catch (error) {
    console.error("Failed to start server:", error.message);
    process.exit(1);
  }
})();
