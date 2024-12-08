const admin = require("../utils/firebase");
const db = admin.firestore(); // Inisialisasi Firestore

// Fungsi untuk menyimpan data
async function storeData(id, data) {
  const predictCollection = db.collection("predictions");
  await predictCollection.doc(id).set(data);
}

// Fungsi untuk mengambil data
async function getData(email) {
  try {
    const predictCollection = db.collection("predictions");
    const snapshot = await predictCollection
      .where("email", "==", email) // Filter berdasarkan email
      .orderBy("createdAt", "desc") // Urutkan berdasarkan createdAt terbaru
      .get();

    if (snapshot.empty) {
      return []; // Jika tidak ada data, kembalikan array kosong
    }

    return snapshot.docs.map((doc) => ({
      id: doc.id,
      ...doc.data(),
    }));
  } catch (err) {
    console.error("Error in getData:", err);
    throw err; // Lempar error agar handler menangani
  }
}

module.exports = { storeData, getData };
