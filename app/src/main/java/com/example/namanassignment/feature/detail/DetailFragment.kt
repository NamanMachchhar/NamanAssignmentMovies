package com.example.namanassignment.feature.detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.namanassignment.R
import com.example.namanassignment.databinding.DetailFragmentBinding
import com.example.namanassignment.util.LocaleManager
import com.example.namanassignment.util.SharePreferenceManager
import com.example.namanassignment.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment(R.layout.detail_fragment) {

    private val binding by viewBinding(DetailFragmentBinding::bind)
    private val viewModel by viewModel<DetailViewModel>()
    private val args by navArgs<DetailFragmentArgs>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private val CAMERA_REQUEST_CODE = 1321

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (SharePreferenceManager.getInstance(requireContext())!!.getComment() != ""){
            Toast.makeText(requireContext(),SharePreferenceManager.getInstance(requireContext())!!.getComment(),Toast.LENGTH_LONG).show()
        }

        val movieId = args.movieId
        loadMovie(movieId)

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    handleCameraImage(result.data)
                }
            }

        binding.btnSetProfile.setOnClickListener {
            setupPermissions()
        }

        binding.btnSubmit.setOnClickListener {
            if (TextUtils.isEmpty(binding.etComment.text.toString())){
                Toast.makeText(requireContext(),getString(R.string.please_enter_comment),Toast.LENGTH_SHORT).show()
            }else{
                SharePreferenceManager.getInstance(requireContext())!!.saveComment(binding.etComment.text.toString().trim())
                Toast.makeText(requireContext(),getString(R.string.comment_saved),Toast.LENGTH_SHORT).show()
                binding.etComment.setText("")
            }
        }
    }

    private fun loadMovie(movieId: Int) {
        lifecycleScope.launchWhenCreated {
            val movie = viewModel.getMovieById(movieId = movieId) ?: return@launchWhenCreated
            binding.tvTitle.text = movie.title
            Glide.with(binding.root)
                .load(movie.imageBackdropUrl)
                .fitCenter()
                .into(binding.ivBackdropPath)
            binding.tvOverview.text = movie.overview
        }
    }

    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.ivProfile.setImageBitmap(bitmap)
    }


    private fun makeRequest() {
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.CAMERA)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.CAMERA)) {
                Log.e("DetailsFragment", "if")
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Camera permission is required for this app to take the picture.")
                        .setTitle("Permission required")
                            builder.setPositiveButton("OK"
                            ) { dialog, id ->
                        makeRequest()
                    }

                    val dialog = builder.create()
                dialog.show()
            } else {
                makeRequest()
            }
        }else{
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1)
            resultLauncher.launch(cameraIntent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                } else {
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1)
                    resultLauncher.launch(cameraIntent)
                }
            }
        }
    }
}