package com.sp.whatnow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DoucmentsImageShowFragment extends Fragment {
    private String clickedId = "";
    private RecyclerView image_show_recyclerView;
    private List<ImageModel> imageList;
    private ImageAdapter imageAdapter;


    public DoucmentsImageShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doucments_image_show, container, false);

        image_show_recyclerView = view.findViewById(R.id.image_recycleview);

        Bundle bundle = getArguments();
        if (bundle != null) {
            clickedId = bundle.getString("clickedId");
            // Use the clickedId as needed
        }
        image_show_recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        File destinationDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "whatnow/"+ clickedId);
        File[] files = destinationDirectory.listFiles();
        Log.d("baka","geh" );


        if (files != null) {
            Log.d("baka","not geh" );
            for (File file : files) {
                if (file.isFile() && file.getPath().endsWith(".jpg")) {
                    String imagePath = file.getAbsolutePath();
                    Log.d("ImagePath", imagePath);
                    imageList.add(new ImageModel(imagePath));
                } else {
                    Log.d("NotAdded", "File not added: " + file.getAbsolutePath());
                }
            }
        }else{
            Log.d("baka","more geh" );
        }

        // Set up RecyclerView Adapter
        imageAdapter = new ImageAdapter(imageList);
        image_show_recyclerView.setAdapter(imageAdapter);

        // Set the click listener on the adapter

        return view;
    }
    public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private List<ImageModel> imageList;
        private Context context;





        public ImageAdapter(List<ImageModel> imageList) {
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.document_row, parent, false);
            return new ImageViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
            ImageModel imageModel = imageList.get(position);

            // Load image using Glide with placeholder and error listener
            Glide.with(context)
                    .load(imageModel.getImagePath())
                    .placeholder(R.drawable.googlelogo) // Add your placeholder image
                    .error(R.drawable.googlelogo) // Add your error image
                    .into(holder.imageView);
            holder.itemView.setOnClickListener(v -> {
                // Directly handle the click event here
                shareImage(imageModel.getImagePath());
            });

        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public ImageViewHolder(@NonNull View itemView) {

                super(itemView);
                imageView = itemView.findViewById(R.id.header_image);
            }
        }
    }
    public void onItemClick(String imagePath) {
        // Handle click event, e.g., share the image
        shareImage(imagePath);
    }

    private void shareImage(String imagePath) {
        // Create an Intent to share the image
        File imageFile = new File(imagePath);

        // Use FileProvider to get a content URI
        Uri contentUri = FileProvider.getUriForFile(
                requireContext(),
                requireContext().getApplicationContext().getPackageName() + ".provider",
                imageFile
        );

        // Create an Intent to share the image
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);

        // Grant read permission to the receiving app
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Start the chooser
        startActivity(Intent.createChooser(shareIntent, "Share Image"));
    }
}