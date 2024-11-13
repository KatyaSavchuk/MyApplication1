package com.example.myapplication1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageUri: Uri

    private val launcher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                binding.imageView.setImageURI(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageUri = generateImageUri()

        binding.create.setOnClickListener {
            launcher.launch(imageUri)
        }

        binding.send.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/jpeg"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("hodovychenko@op.edu.ua"))
                putExtra(Intent.EXTRA_SUBJECT, "DigiJED Savchuk Kateryna ")
                putExtra(Intent.EXTRA_TEXT, " ")
                putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            startActivity(emailIntent)
        }
    }

    private fun generateImageUri(): Uri {
        val values  = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "selfie_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }
        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values )
            ?: throw IOException("Failed to generate URI")
    }
}