import tensorflow as tf
from tensorflow import keras
import numpy as np
from flask import Flask, request, jsonify
import cv2

# Muat model yang sudah ada
model = keras.models.load_model('modelV3.h5')

# Definisikan labels sesuai urutan output model
LABELS = [
    'Acne and rosacea',
    'Eczema', 
    'Nail Fungus',
    'Psoriasis',
    'Seborrheic keratosis',
    'Tinea ringworm',
    'Warts molloscum'
]

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    try:
        # Periksa apakah file gambar ada
        if 'image' not in request.files:
            return jsonify({
                'error': 'No image file uploaded',
                'status': 'error'
            }), 400
        
        # Ambil file gambar
        image_file = request.files['image']
        
        # Baca gambar menggunakan OpenCV
        image_array = cv2.imdecode(np.frombuffer(image_file.read(), np.uint8), cv2.IMREAD_COLOR)
        
        # Preprocessing gambar (sesuaikan dengan kebutuhan model Anda)
        # Contoh: resize, normalisasi
        image_resized = cv2.resize(image_array, (224, 224))  # Sesuaikan ukuran
        image_normalized = image_resized / 255.0  # Normalisasi
        
        # Tambahkan dimensi batch
        input_data = np.expand_dims(image_normalized, axis=0)
        
        # Prediksi menggunakan model
        prediction = model.predict(input_data)
        
        # Dapatkan indeks label dengan probabilitas tertinggi
        predicted_index = np.argmax(prediction[0])
        predicted_label = LABELS[predicted_index]
        max_probability = prediction[0][predicted_index]
        
        # Siapkan detail prediksi
        detailed_prediction = [
            {"label": label, "probability": float(prob)} 
            for label, prob in zip(LABELS, prediction[0])
        ]
        
        return jsonify({
            'predicted_label': predicted_label,
            'predicted_probability': float(max_probability),
            'detailed_predictions': detailed_prediction,
            'status': 'success'
        }), 200
    
    except Exception as e:
        return jsonify({
            'error': str(e),
            'status': 'error'
        }), 400

@app.route('/health', methods=['GET'])
def health_check():
    return jsonify({
        'status': 'healthy',
        'model_loaded': model is not None
    }), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)