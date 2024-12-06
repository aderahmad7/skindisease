const tf = require("@tensorflow/tfjs-node");

class CustomLayer extends tf.layers.Layer {
  constructor(config) {
    super(config);
  }

  // Implementasi lapisan kustom
  call(input) {
    return input;
  }

  // Registrasi lapisan kustom
  static get className() {
    return "TFOpLambda";
  }
}

class L2 {
  static className = "L2";

  constructor(config) {
    return tf.regularizers.l1l2(config);
  }
}
tf.serialization.registerClass(CustomLayer);
tf.serialization.registerClass(L2);

// Daftarkan lapisan kustom agar TensorFlow.js mengenalnya
// Fungsi untuk memuat model
async function loadModel() {
  try {
    // if (!process.env.MODEL_URL) {
    //   throw new Error("MODEL_URL environment variable is not set.");
    // }
    console.log("Loading model...");
    const model = await tf.loadLayersModel(".");
    console.log("Model loaded successfully");
    return model;
  } catch (error) {
    console.error("Error loading model:", error.message);
    throw error;
  }
}

module.exports = loadModel;
