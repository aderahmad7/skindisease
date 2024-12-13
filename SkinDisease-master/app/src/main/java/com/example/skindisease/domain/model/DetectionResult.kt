package com.example.skindisease.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection_result")
data class DetectionResult(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "uri")
    val uri: String = ""
)

val fakeResults = listOf(
    DetectionResult(
        name = "Bisul",
        description = "Jenis penyakit kulit yang satu ini pasti sudah tidak asing lagi untuk didengar. Ya, bisul merupakan salah satu penyakit kulit yang sangat umum dialami. Bisul disebabkan oleh infeksi yang menyerang folikel rambut atau kelenjar minyak. Bisul dapat menular melalui kontak kulit bila Anda bersentuhan dengan seseorang yang terinfeksi.",
        uri = ""
    ),
    DetectionResult(
        name = "Jerawat",
        description = "Jerawat merupakan masalah kulit yang cukup normal terjadi. Penyebab jerawat cukup beragam, bisa karena sel kulit mati, kesalahan dalam pemilihan skincare,  pemakaian masker,  atau bahkan stres.\n Jenis penyakit kulit ini timbul ketika folikel kulit tersumbat oleh sebum (minyak kulit), atau juga perubahan hormon. Sering kali jerawat muncul pada bagian wajah. Namun, tak jarang pula jerawat tumbuh di bagian punggung, umumnya karena faktor kebersihan.",
        uri = ""
    ),
    DetectionResult(
        name = "Dermatitis",
        description = "Dermatitis merupakan jenis penyakit kulit yang tidak menular. Kondisi ini disebabkan oleh adanya peradangan.",
        uri = ""
    ),
    DetectionResult(
        name = "Cacar Air",
        description = "Cacar air adalah jenis penyakit kulit yang kerap terjadi pada anak-anak, tetapi tetap ada kemungkinan menyerang orang di usia dewasa. Cacar air disebabkan oleh infeksi virus Varicella zoster dan termasuk penyakit yang bisa menular dengan mudah, terlebih saat imun tubuh sedang lemah.",
        uri = ""
    ),
    DetectionResult(
        name = "Kudis",
        description = "Kudis disebabkan oleh kutu kulit yang berada di lapisan terluar kulit yang menyebabkan kemunculan masalah semacam jerawat, namun terasa gatal. Penyakit kulit yang satu ini dapat menular apabila kutu menyebar ke orang lain melalui kontak fisik secara langsung, atau saat berbagi barang pribadi, seperti baju dan handuk.",
        uri = ""
    ),
    DetectionResult(
        name = "Kurap",
        description = "Kurap adalah salah satu dari macam-macam penyakit kulit yang disebabkan oleh infeksi jamur. Jamur ini biasanya berkembang pada bagian kulit yang lembab dan hangat, seperti di sela-sela jari kaki.",
        uri = ""
    ),
    DetectionResult(
        name = "Herpes",
        description = "Herpes adalah permasalahan pada kulit yang ditandai dengan kemunculan lenting atau lepuhan berwarna merah. Sering kali menyerang bagian wajah, bibir, dan kulit. Penyakit kulit ini disebabkan oleh beberapa jenis virus, di antaranya HSV tipe 1, HSV tipe 2, Varicella zoster, EBV, CMV, HHV 6, HHV 7, dan HHV 8.",
        uri = ""
    ),
    DetectionResult(
        name = "Biduran",
        description = "Biduran ditandai dengan munculnya bentol-bentol berwarna merah dan terasa gatal. Biduran disebabkan oleh banyak faktor, salah satunya yaitu reaksi alergi cuaca atau debu.\nJenis penyakit kulit ini bisa sembuh dengan sendirinya, namun untuk mengurangi rasa gatalnya, Anda dapat mengompres dengan air dingin, menggunakan bedak tabur anti gagal, atau mengonsumsi obat sesuai anjuran dokter.",
        uri = ""
    ),
    DetectionResult(
        name = "Vitiligo",
        description = "Vitiligo adalah jenis kelainan kulit yang dapat terjadi apabila tubuh kekurangan melanin akibat gangguan autoimun. Karenanya, dampak yang ditimbulkan adalah warna kulit tidak merata. Selain menyebabkan warna kulit tidak merata, vitiligo juga ditandai dengan munculnya uban pada alis, rambut, dan bulu mata.",
        uri = ""
    )
)