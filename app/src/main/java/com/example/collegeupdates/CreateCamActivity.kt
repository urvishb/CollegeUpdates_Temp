package com.example.collegeupdates

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.collegeupdates.models.Notice
import com.example.collegeupdates.models.Post
import com.example.collegeupdates.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_create_cam.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

private const val TAG = "CreateCamActivity"
private const val PICK_PHOTO_CODE = 1234
private const val REQUEST_CODE = 42
private lateinit var photoFile : File
private const val FILE_NAME = "photo.jpg"
private var fragVal = "events"
const val CurrentFragment = "CurrentFragment"

class CreateCamActivity : AppCompatActivity() {

    private var signedInUser: User? = null
    private var photoUri: Uri? = null
    private lateinit var firestoreDb : FirebaseFirestore
    private lateinit var storageReference : StorageReference

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cam)

        storageReference = FirebaseStorage.getInstance().reference

        firestoreDb = FirebaseFirestore.getInstance()
        firestoreDb.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid as String)
            .get()
            .addOnSuccessListener { userSnapshot ->
                signedInUser = userSnapshot.toObject(User::class.java)
                Log.i(TAG, "signed in user: $signedInUser")
            }
            .addOnFailureListener{ exception ->
                Log.i(TAG, "Failure fetching signed in user", exception)
            }

        btnPickImage.setOnClickListener {
            Log.i(TAG, "Open up image picker on device")
            val imagePickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            imagePickerIntent.type = "image/*"
            if(imagePickerIntent.resolveActivity(packageManager) != null)
            {
                startActivityForResult(imagePickerIntent, PICK_PHOTO_CODE)
            }
        }

        btnTakePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            photoFile = getPhotoFile(FILE_NAME)

            // this doesn't work for API >= 24 (Starting from 2016)
            //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)

            val fileProvider = FileProvider.getUriForFile(this, "com.example.collegeupdates.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }

        }

        btnSubmit.setOnClickListener {
            // pushing data on firebase via our submit button
            handleSubmitButtonClick()
        }


        val suggestions: Array<String> = resources.getStringArray(R.array.suggestionLocations)

        val searchLocAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestions)

        etLocation.threshold = 0
        etLocation.setAdapter(searchLocAdapter)
        etLocation.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) etLocation.showDropDown() }


    }



    private fun getPhotoFile(fileName : String) : File {
        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    private fun handleSubmitButtonClick() {
        // all kinds of error checking before uploading the image
        if (photoUri == null)
        {
            Toast.makeText(this, "No photo selected", Toast.LENGTH_SHORT).show()
            return
        }


        if (etLocation.text.isBlank())
        {
            Toast.makeText(this, "Location cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if(signedInUser == null)
        {
            Toast.makeText(this, "No signed in user, Please Login", Toast.LENGTH_SHORT).show()
            return
        }
        btnSubmit.isEnabled = false

        // How Submit works >>>

        val photoUploadUri = photoUri as Uri

        val photoReference = storageReference.child("images/${System.currentTimeMillis()}-photo.jpg") // adding photo to image directory with name as current time in Milliseconds
        // Upload the image to Firebase Storage
        photoReference.putFile(photoUploadUri)
            .continueWithTask { photoUploadTask ->
                Log.i(TAG, " uploaded bytes: ${photoUploadTask.result?.totalByteCount}")
                // Retrieve image url of the uploaded photo
                photoReference.downloadUrl
            }.continueWithTask {downloadUrlTask ->
                // Create a post object with the image URL and add that to the post collection
                fragVal = intent.getStringExtra(FragmentValue).toString()

                if (fragVal.equals("events"))
                {
                    val post = Post(
                        etLocation.text.toString(),
                        downloadUrlTask.result.toString(),
                        System.currentTimeMillis(),
                        signedInUser)
                    firestoreDb.collection("posts").add(post)
                }
                else
                {
                    val notice = Notice(
                        etLocation.text.toString(),
                        downloadUrlTask.result.toString(),
                        System.currentTimeMillis(),
                        signedInUser)
                    firestoreDb.collection("notices").add(notice)
                }


            }.addOnCompleteListener { postCreationTask ->
                btnSubmit.isEnabled = true
                if (!postCreationTask.isSuccessful) {
                    Log.e(TAG, "Exception during firebase operations", postCreationTask.exception)
                    Toast.makeText(this, "Failed to save post", Toast.LENGTH_SHORT).show()
                }
                etLocation.text.clear()
                imageView.setImageResource(0)
                Toast.makeText(this, "Uploaded Successfully!", Toast.LENGTH_SHORT).show()
                val profileIntent = Intent(this, ProfileActivity::class.java)
                val homeIntent = Intent(this, MainActivity::class.java)
                homeIntent.putExtra(CurrentFragment, fragVal)
                profileIntent.putExtra(EXTRA_USERNAME, signedInUser?.username)
                startActivity(homeIntent)
                finish()

            }



        // all these asynchronous operations hare handled by Tasks API for errors and flow maintenance


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == PICK_PHOTO_CODE)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                photoUri = data?.data
                Log.i(TAG, "photoUri $photoUri")
                imageView.setImageURI(photoUri)
            }
            else
            {
                Toast.makeText(this, "Image Picker Action canceled", Toast.LENGTH_SHORT).show()
            }
        }

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
//            val takenImage = data?.extras?.get("data") as Bitmap
//            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            photoUri = Uri.fromFile(photoFile)
            Log.i(TAG, "photoUri $photoUri")
            imageView.setImageURI(photoUri)
            //imageView.setImageBitmap(takenImage)
        } else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}