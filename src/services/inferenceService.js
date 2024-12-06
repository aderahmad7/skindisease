const tf = require("@tensorflow/tfjs-node");
const InputError = require("../exceptions/InputError");

async function predictClassification(model, image) {
  try {
    const tensor = tf.node
      .decodeJpeg(image)
      .resizeNearestNeighbor([224, 224])
      .expandDims()
      .toFloat();

    const prediction = model.predict(tensor);
    const score = await prediction.data();
    const confidenceScore = Math.max(...score) * 100;

    const classes = [
      "Acne and rosacea",
      "Eczema",
      "Psoriasis",
      "Nail fungus",
      "Seborrheic keratosis",
      "Tinea ringworm",
      "Warts molloscum",
    ];

    const classResult = tf.argMax(prediction, 1).dataSync()[0];
    const label = classes[classResult];

    let description;

    if (label === "Acne and rosacea") {
      description =
        "Acne adalah kondisi kulit akibat penyumbatan pori-pori oleh minyak, sel kulit mati, dan bakteri yang menimbulkan jerawat, komedo, atau pustula, sering muncul di wajah, punggung, dan dada. Rosacea adalah gangguan kronis yang menyebabkan kemerahan, pembuluh darah terlihat, dan kulit sensitif di wajah, terutama pipi, hidung, dan dagu.";
    }
    if (label === "Eczema") {
      description =
        "Merupakan peradangan kulit kronis yang ditandai dengan kulit kering, gatal, merah, dan pecah-pecah, sering kali terkait alergi atau asma. Eczema atopik adalah bentuk paling umum yang menyerang anak-anak hingga dewasa.";
    }
    if (label === "Psoriasis") {
      description =
        "Penyakit autoimun kronis yang mempercepat regenerasi sel kulit, menyebabkan bercak merah, sisik putih keperakan, dan gatal atau nyeri, biasanya muncul di siku, lutut, kulit kepala, atau punggung bawah.";
    }
    if (label === "Nail fungus") {
      description =
        " Infeksi jamur yang menyerang kuku, membuatnya tebal, rapuh, berubah warna, atau bahkan terlepas, lebih sering terjadi pada kuku kaki akibat lingkungan lembap dan kurangnya sirkulasi udara.";
    }
    if (label === "Seborrheic keratosis") {
      description =
        "Pertumbuhan kulit non-kanker berbentuk bercak kasar berwarna coklat, hitam, atau kuning yang sering muncul pada orang tua dan umumnya tidak berbahaya, meski kadang mengganggu secara estetika.";
    }
    if (label === "Tinea ringworm") {
      description =
        "Infeksi jamur yang menghasilkan ruam melingkar dengan tepi lebih merah dibanding tengahnya, menyerupai cincin, dan dapat muncul di tubuh, kepala, atau kaki, sering menular melalui kontak langsung.";
    }
    if (label === "Warts molloscum") {
      description =
        "Warts adalah kutil berupa benjolan kasar akibat infeksi HPV yang umumnya tidak nyeri kecuali di telapak kaki. Molluscum contagiosum adalah infeksi virus yang menyebabkan benjolan kecil berbentuk kubah dengan bagian tengah cekung, sering menyerang anak-anak.";
    }

    return { confidenceScore, label, description };
  } catch (error) {
    throw new InputError(`Terjadi kesalahan dalam melakukan prediksi`);
  }
}

module.exports = predictClassification;
