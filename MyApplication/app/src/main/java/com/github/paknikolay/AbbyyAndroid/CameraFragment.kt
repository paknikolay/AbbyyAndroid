package com.github.paknikolay.AbbyyAndroid;

import android.Manifest
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.ImageCapture
import androidx.camera.view.CameraView
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.github.paknikolay.AbbyyAndroid.db.NoteRepository;
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Thread.sleep
import java.util.*

class CameraFragment:Fragment(), ImageCapture.OnImageSavedListener  {
    private val CAMERA_REQUEST_CODE = 0

    private var cameraView: CameraView? = null
    private var takePictureButton: View? = null

    companion object {
        val TAG:String = "CameraFragment"

        val NAME_KEY:String = "NAME_KEY";

        fun newInstance(@NonNull name:String) : Fragment {
            val fragment:Fragment = CameraFragment();
            val arguments:Bundle = Bundle();

            arguments.putString(NAME_KEY, name);
            fragment.setArguments(arguments);
            return fragment;
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?)
            : View?
    {
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity!!, arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.CAMERA)) {
                    Toast.makeText(context, R.string.need_permission, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, R.string.permission_in_settings, Toast.LENGTH_SHORT).show()
                }
                (activity as NoteAddActivity).onBackPressed()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun startCamera() {
        cameraView = activity?.findViewById(R.id.cameraView)
        cameraView?.setCaptureMode(CameraView.CaptureMode.IMAGE)
        cameraView?.bindToLifecycle(this as LifecycleOwner)
        takePictureButton = activity?.findViewById(R.id.takePictureButton)
        takePictureButton?.setOnClickListener {
            cameraView?.takePicture(
                generatePictureFile()!!,
                AsyncTask.SERIAL_EXECUTOR,
                this
            )
        }
    }

    override fun onImageSaved(file: File) {
        (activity as NoteAddActivity).showOverviewFragment("overviewFragment", file.path)
    }

    override fun onError(
        imageCaptureError: ImageCapture.ImageCaptureError,
        message: String,
        cause: Throwable?
    ) {
        (activity as NoteAddActivity).onBackPressed()
    }

    private fun generatePictureFile(): File? {
        return File(activity?.filesDir, UUID.randomUUID().toString())
    }


}