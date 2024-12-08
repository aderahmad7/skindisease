const admin = require("firebase-admin");
const serviceAccount = require("./skindisease-ed00e-firebase-adminsdk-5sl2v-4bc66788e3.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

module.exports = admin;
