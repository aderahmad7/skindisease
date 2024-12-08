const mysql = require("mysql2");

// Membuat koneksi ke database MySQL
const db = mysql.createConnection({
  host: "localhost", // Sesuaikan dengan host MySQL Anda
  user: "root", // Sesuaikan dengan username MySQL Anda
  password: "", // Sesuaikan dengan password MySQL Anda
  database: "skindesease", // Sesuaikan dengan nama database Anda
});

// Fungsi untuk menyimpan data ke MySQL
async function storeData(id, data) {
  return new Promise((resolve, reject) => {
    const query =
      "INSERT INTO predictions (id, email, result, description, confidenceScore, createdAt) VALUES (?, ?, ?, ?, ?, ?)";
    const values = [
      id,
      data.email,
      data.result,
      data.description,
      data.confidenceScore,
      data.createdAt,
    ];

    db.execute(query, values, (err, result) => {
      if (err) {
        console.error("Error saving data to MySQL:", err);
        reject(err);
      } else {
        resolve(result);
      }
    });
  });
}

// Fungsi untuk mengambil data dari MySQL
async function getData() {
  return new Promise((resolve, reject) => {
    db.execute("SELECT * FROM predictions", (err, results) => {
      if (err) {
        console.error("Error fetching data from MySQL:", err);
        reject(err);
      } else {
        resolve(results);
      }
    });
  });
}

module.exports = { storeData, getData };
