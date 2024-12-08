const jwt = require("jsonwebtoken");
const admin = require("../../utils/firebase");
require("dotenv").config();

// Secret key untuk JWT
const JWT_SECRET = process.env.JWT_SECRET;

// Fungsi untuk login
async function loginHandler(request, h) {
  const { email } = request.payload;
  console.log(email);

  try {
    // Verifikasi kredensial ke Firebase
    const user = await admin.auth().getUserByEmail(email);

    if (!user) {
      return h.response({ message: "User not found" }).code(404);
    }

    // Asumsikan bahwa validasi password dilakukan di front-end sebelum sampai sini.

    // Generate JWT
    const token = jwt.sign({ uid: user.uid, email: user.email }, JWT_SECRET, {
      expiresIn: "1h",
    });

    return h
      .response({
        message: "Login successful",
        token,
      })
      .code(200);
  } catch (error) {
    console.error(error);
    return h.response({ message: "Invalid email or password" }).code(401);
  }
}

module.exports = {
  loginHandler,
};
