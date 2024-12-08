const { loginHandler } = require("../handler/authHandler");

const routes = [
  {
    method: "POST",
    path: "/login",
    handler: loginHandler,
    options: {
      auth: false, // Tidak ada autentikasi di sini
    },
  },
];

module.exports = routes;
