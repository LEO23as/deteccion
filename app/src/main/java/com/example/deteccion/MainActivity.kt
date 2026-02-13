package com.example.deteccion

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.io.IOException

class MainActivity : AppCompatActivity(), OnSuccessListener<Text>, OnFailureListener {
    private val REQUEST_GALLERY = 1
    private val REQUEST_CAMERA = 2
    private val PERMISSION_CAMERA_CODE = 100
    private lateinit var mImageView: ImageView
    private lateinit var txtResults: TextView
    private var mSelectedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mImageView = findViewById(R.id.image_view)
        txtResults = findViewById(R.id.txtresults)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun abrirGaleria(view: View) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    fun abrirCamara(view: View) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), PERMISSION_CAMERA_CODE)
        } else {
            lanzarCamara()
        }
    }

    private fun lanzarCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CAMERA_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                lanzarCamara()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            try {
                if (requestCode == REQUEST_CAMERA) {
                    mSelectedImage = data.extras?.get("data") as Bitmap
                } else if (requestCode == REQUEST_GALLERY) {
                    val uri = data.data
                    mSelectedImage = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                }
                mImageView.setImageBitmap(mSelectedImage)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun OCRfx(v: View) {
        if (mSelectedImage != null) {
            val image = InputImage.fromBitmap(mSelectedImage!!, 0)
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            recognizer.process(image)
                .addOnSuccessListener(this)
                .addOnFailureListener(this)
        } else {
            Toast.makeText(this, "Por favor, selecciona una imagen primero", Toast.LENGTH_SHORT).show()
        }
    }

    fun Rostrosfx(v: View) {
        if (mSelectedImage != null) {
            val image = InputImage.fromBitmap(mSelectedImage!!, 0)
            val options = FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .build()
            val detector = FaceDetection.getClient(options)
            detector.process(image)
                .addOnSuccessListener { faces ->
                    if (faces.isEmpty()) {
                        txtResults.text = "No hay rostros"
                    } else {
                        txtResults.text = "Hay ${faces.size} rostro(s)"
                        
                        val drawable = mImageView.drawable as BitmapDrawable
                        val bitmap = drawable.bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        val canvas = Canvas(bitmap)
                        val paint = Paint()
                        paint.color = Color.RED
                        paint.strokeWidth = 5f
                        paint.style = Paint.Style.STROKE
                        
                        for (rostro in faces) {
                            canvas.drawRect(rostro.boundingBox, paint)
                        }
                        mImageView.setImageBitmap(bitmap)
                    }
                }
                .addOnFailureListener(this)
        } else {
            Toast.makeText(this, "Por favor, selecciona una imagen primero", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSuccess(text: Text) {
        txtResults.text = text.text
    }

    override fun onFailure(e: Exception) {
        txtResults.text = "Error: ${e.message}"
    }
}