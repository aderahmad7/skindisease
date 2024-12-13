const { storeData, getData } = require("../../services/storeData");
const axios = require("axios");
const crypto = require("crypto");
const FormData = require("form-data");

async function postPredictHandler(request, h) {
  const { image, email } = request.payload; // Ambil file gambar dari payload
  const id = crypto.randomUUID();
  const createdAt = new Date().toISOString();

  const descriptions = {
    "Acne and rosacea":
      "Acne adalah kondisi kulit akibat penyumbatan pori-pori oleh minyak, sel kulit mati, dan bakteri yang menimbulkan jerawat, komedo, atau pustula, sering muncul di wajah, punggung, dan dada. Rosacea adalah gangguan kronis yang menyebabkan kemerahan, pembuluh darah terlihat, dan kulit sensitif di wajah, terutama pipi, hidung, dan dagu.",
    Eczema:
      "Merupakan peradangan kulit kronis yang ditandai dengan kulit kering, gatal, merah, dan pecah-pecah, sering kali terkait alergi atau asma. Eczema atopik adalah bentuk paling umum yang menyerang anak-anak hingga dewasa.",
    Psoriasis:
      "Penyakit autoimun kronis yang mempercepat regenerasi sel kulit, menyebabkan bercak merah, sisik putih keperakan, dan gatal atau nyeri, biasanya muncul di siku, lutut, kulit kepala, atau punggung bawah.",
    "Nail fungus":
      "Infeksi jamur yang menyerang kuku, membuatnya tebal, rapuh, berubah warna, atau bahkan terlepas, lebih sering terjadi pada kuku kaki akibat lingkungan lembap dan kurangnya sirkulasi udara.",
    "Seborrheic keratosis":
      "Pertumbuhan kulit non-kanker berbentuk bercak kasar berwarna coklat, hitam, atau kuning yang sering muncul pada orang tua dan umumnya tidak berbahaya, meski kadang mengganggu secara estetika.",
    "Tinea ringworm":
      "Infeksi jamur yang menghasilkan ruam melingkar dengan tepi lebih merah dibanding tengahnya, menyerupai cincin, dan dapat muncul di tubuh, kepala, atau kaki, sering menular melalui kontak langsung.",
    "Warts molloscum":
      "Warts adalah kutil berupa benjolan kasar akibat infeksi HPV yang umumnya tidak nyeri kecuali di telapak kaki. Molluscum contagiosum adalah infeksi virus yang menyebabkan benjolan kecil berbentuk kubah dengan bagian tengah cekung, sering menyerang anak-anak.",
  };

  try {
    // Periksa apakah `image` adalah buffer
    if (!Buffer.isBuffer(image)) {
      throw new Error("Invalid image format in payload");
    }

    // Buat form data untuk mengirim buffer ke API Python
    const formData = new FormData();
    formData.append("image", image, {
      filename: "uploaded_image.jpg", // Tetapkan nama file manual
      contentType: "image/jpeg", // Tetapkan tipe konten
    });

    // Kirim POST request ke API Python
    const response = await axios.post(
      "https://model-api-151581457522.asia-southeast2.run.app/predict",
      formData,
      {
        headers: {
          ...formData.getHeaders(),
        },
      }
    );

    // Ambil data dari respons API
    const { predicted_label, predicted_probability, detailed_predictions } =
      response.data;

    // Ambil deskripsi berdasarkan label yang diprediksi
    const description =
      descriptions[predicted_label] || "Deskripsi tidak ditemukan";

    // Siapkan objek data untuk disimpan
    const data = {
      id: id,
      email: email,
      result: predicted_label,
      description: description, // Ambil deskripsi berdasarkan label yang diprediksi
      confidenceScore: predicted_probability * 100, // Persentase confidence score
      createdAt: createdAt,
    };

    // Simpan data (gunakan fungsi storeData jika diperlukan)
    await storeData(id, data);

    // Berikan respons sukses
    const responsePayload = h.response({
      status: "success",
      message: "Model is predicted successfully",
      data,
    });
    responsePayload.code(201);
    return responsePayload;
  } catch (error) {
    console.error("Error during prediction:", error.message);

    // Tangani jika terjadi kesalahan
    const errorResponse = h.response({
      status: "fail",
      message:
        error.message || "Failed to predict the model. Please try again.",
    });
    errorResponse.code(500);
    return errorResponse;
  }
}

async function getHistoriesHandler(request, h) {
  try {
    const { email } = request.query; // Ambil parameter email dari query
    const histories = await getData(email); // Panggil fungsi dengan email
    const result = histories.map((doc) => ({
      id: doc.id,
      history: {
        id: doc.id,
        email: doc.email,
        result: doc.result,
        description: doc.description,
        confidenceScore: doc.confidenceScore,
        createdAt: doc.createdAt,
      },
    }));

    return h.response({
      status: "success",
      data: result,
    });
  } catch (err) {
    console.error("Error in getHistoriesHandler:", err); // Log error ke console
    return h
      .response({
        status: "fail",
        message: "An internal server error occurred",
      })
      .code(500);
  }
}

module.exports = { postPredictHandler, getHistoriesHandler };
