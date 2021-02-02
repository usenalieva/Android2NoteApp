package com.makhabatusen.noteapp.ui.profile;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.makhabatusen.noteapp.App;
import com.makhabatusen.noteapp.R;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    ImageView profilePic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePic = view.findViewById(R.id.iv_profile_pic);

        Glide
                .with(this)
                .load(App.getPrefs().avatarUrl())
                .centerCrop()
                .into(profilePic);

        profilePic.setOnClickListener(v -> {
            getContent.launch("image/*");
        });
    }

    ActivityResultLauncher<String> getContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            result -> {
                Glide.with(requireActivity())
                        .load(result)
                        .centerCrop()
                        //.circleCrop()
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(40)))
                        .into(profilePic);
                uploadToFB(result);
            }
    );

    private void uploadToFB(Uri uri) {
        final StorageReference reference =
                FirebaseStorage
                        .getInstance()
                        .getReference()
                        .child("images/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/avatar");

        reference
                .putFile(uri)
                .continueWithTask(task ->
                        reference.getDownloadUrl()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveAvatarUrl(task.getResult().toString());
                    } else {
                        Toast.makeText(requireContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
        );
    }

    private void saveAvatarUrl(String url) {
        App.getPrefs().avatarUrl(url);
    }

}