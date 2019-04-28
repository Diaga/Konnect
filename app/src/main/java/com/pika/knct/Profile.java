package com.pika.knct;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Profile extends Fragment {

    private TextView nameTextView;
    private TextView educationTextView;
    private TextView locationTextView;
    private ImageView profileImageView;
    private View fragmentProfileView;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentProfileView = inflater.inflate(R.layout.fragment_profile, null);

        nameTextView = fragmentProfileView.findViewById(R.id.appCompatTextView3);
        educationTextView = fragmentProfileView.findViewById(R.id.appCompatTextView4);
        locationTextView = fragmentProfileView.findViewById(R.id.appCompatTextView5);
        profileImageView = fragmentProfileView.findViewById(R.id.avatar);

        if (user.getPhotoURI() != null) {
            Picasso.with(getContext()).load(user.getPhotoURI()).into(profileImageView);
        }
        nameTextView.setText(user.getDisplayName());
        educationTextView.setText(user.getInstitute());
        locationTextView.setText(user.getCity());

        return fragmentProfileView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect with database
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        user = userViewModel.getSignedInUser();


    }
}
