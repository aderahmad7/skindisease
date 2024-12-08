require("dotenv").config();
const Hapi = require("@hapi/hapi");
const modelRoutes = require("./routes/modelRoute");
const authRoutes = require("./routes/authRoutes");
const InputError = require("../exceptions/InputError");
const Jwt = require("@hapi/jwt");

(async () => {
  const server = Hapi.server({
    port: process.env.PORT || 3000,
    host: "localhost",
    routes: {
      cors: {
        origin: ["*"],
      },
    },
  });
  console.log("1");

  // Registrasi plugin JWT
  await server.register(Jwt);
  console.log("2");
  // Konfigurasi metode autentikasi
  server.auth.strategy("jwt", "jwt", {
    keys: process.env.JWT_SECRET, // Gunakan JWT secret dari .env
    verify: {
      // Menambahkan konfigurasi verify
      aud: false, // Sesuaikan dengan audience jika diperlukan
      iss: false, // Sesuaikan dengan issuer jika diperlukan
      sub: false, // Sesuaikan dengan subject jika diperlukan
    },
    validate: async (artifacts, request, h) => {
      // Cek validitas token, bisa cek lebih lanjut jika diperlukan
      return { isValid: true }; // Return true jika token valid
    },
  });
  

  // Terapkan autentikasi pada seluruh routes yang membutuhkan
  server.auth.default("jwt");
  console.log("5");

  try {
    console.log("6");

    console.log("3");
    const routes = [...authRoutes, ...modelRoutes];
    server.route(routes); // Akan dibahas lebih lanjut setelah pembahasan extension.
    console.log("4");

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
