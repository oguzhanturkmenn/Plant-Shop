package com.oguzhanturkmen.myplantapp.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.storage.FirebaseStorage
import com.oguzhanturkmen.myplantapp.R
import com.oguzhanturkmen.myplantapp.databinding.FragmentProfileBinding
import com.oguzhanturkmen.myplantapp.ui.MainActivity
import com.oguzhanturkmen.myplantapp.utils.gecisYap
import com.oguzhanturkmen.myplantapp.utils.makeToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var binding: FragmentProfileBinding
    var selectedImage: Uri? = null
    var selectedBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileFragment = this
        viewModel.getLiveUser()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.userLive.observe(viewLifecycleOwner) {
            Log.e("deneme", it.toString())
            it.let {
                binding.user = it
            }
        }
    }

    private fun saveImageFirebase(image: Uri) {
        val reference = viewModel.firebaseStore.reference
        val imageRef =
            reference.child("profilePhotos")
                .child(viewModel.firebaseAuth.currentUser?.email.toString())

        imageRef.putFile(image).addOnSuccessListener {
            FirebaseStorage.getInstance().reference.child("profilePhotos")
                .child(viewModel.firebaseAuth.currentUser?.email.toString()).downloadUrl.addOnSuccessListener {
                    viewModel.updateImage(it.toString())
                }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val storageIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(storageIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.data
            selectedImage?.let {
                if (Build.VERSION.SDK_INT >= 28) {
                    val source =
                        ImageDecoder.createSource((activity as MainActivity).contentResolver, it)
                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                    binding.imgProfilePicture.setImageBitmap(selectedBitmap)
                    saveImageFirebase(it)
                } else {
                    selectedBitmap =
                        MediaStore.Images.Media.getBitmap(
                            (activity as MainActivity).contentResolver,
                            it
                        )
                    binding.imgProfilePicture.setImageBitmap(selectedBitmap)
                    saveImageFirebase(it)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun loadProfileImageClick() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (activity as MainActivity),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
            )
        } else {
            val storageInten =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(storageInten, 2)
        }
    }

    fun changeLanguage() {
        setLocale("en") //
        recreate(requireActivity())
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)

        requireContext().resources.updateConfiguration(
            configuration,
            requireContext().resources.displayMetrics
        )
    }

    fun logout() {
        viewModel.firebaseAuth.signOut()
        Navigation.gecisYap(
            requireView(),
            ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
        )
    }
}