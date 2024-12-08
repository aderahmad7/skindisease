const admin = require("firebase-admin");
const serviceAccount = require("./skindisease-ed00e-firebase-adminsdk-5sl2v-1c3c8b1e01.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

module.exports = admin;
