package com.sp.whatnow;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.Manifest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import androidx.appcompat.app.AlertDialog;



public class DocumentUploadFragment extends Fragment {

    private static final int REQUEST_PERMISSION_CODE = 200;
    private static final int PICK_IMAGE_REQUEST = 40;

    private String selectedOption = "";

    private static final String[] REQUIRED_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};


    public DocumentUploadFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_document_upload, container, false);

        // Check and request external storage permission

        checkPermission();

        return view;
    }
    private void checkPermission() {
        if (hasPermissions()) {
            // Permission already granted, proceed with image selection

            showOptionSelectionDialog();
        } else {
            // Request permission
            ActivityCompat.requestPermissions(requireActivity(), REQUIRED_PERMISSIONS, REQUEST_PERMISSION_CODE);
        }
    }
    private boolean hasPermissions() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with image selection+
                Log.d("yey", "onRequestPermissionsResult: ");
                selectImage();
            } else {
                // Permission denied
                // Handle accordingly, show a message, request again, etc.
                Log.d("nah", "onRequestPermissionsResult: ");
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            String selectedImagePath = getPathFromUri(selectedImageUri);

            // Now you have the selected image path, proceed to copy it to the appropriate directory

            File destinationDirectory = new File(Environment.getExternalStorageDirectory(), "download/whatnow/" + selectedOption);

            if (!destinationDirectory.exists()) {
                destinationDirectory.mkdirs();
            }

            File destinationImagePath = new File(destinationDirectory, "image.jpg");

            try {
                copyFile(new File(selectedImagePath), destinationImagePath);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception, show an error message, etc.
            }
        }
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = requireActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);
        cursor.close();
        return imagePath;
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        FileInputStream sourceStream = new FileInputStream(sourceFile);
        FileOutputStream destStream = new FileOutputStream(destFile);
        FileChannel sourceChannel = sourceStream.getChannel();
        FileChannel destChannel = destStream.getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        sourceStream.close();
        destStream.close();
        DocumentsFragment image = new DocumentsFragment();
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeFragmentContainer, image)
                .addToBackStack(null) // Add to the back stack for back navigation
                .commit();
    }
    private void showOptionSelectionDialog() {
        // Create a list of options to display in the dialog
        String[] options = {"school", "courses", "national", "international", "others"};

        // Create an ArrayAdapter to display the options in the dialog
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, options);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Select an option")
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Update the selectedOption variable based on the selected option
                        selectedOption = options[which];
                        // Call the image selection method after the option is selected
                        selectImage();
                    }
                })
                .setCancelable(false) // To prevent dismissing the dialog by tapping outside
                .show();
    }



}