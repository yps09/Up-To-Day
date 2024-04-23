package com.android.up_to_day;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DeveloperActivity extends AppCompatActivity {

    ImageView developer_img;
    CardView linkedin,email,github;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        linkedin = findViewById(R.id.linkedbtn);
        email = findViewById(R.id.emailbtn);
        github = findViewById(R.id.gitbtn);
        developer_img = findViewById(R.id.developer_img);
        String imgUrl = "https://media.licdn.com/dms/image/D4D03AQH33wtUqkLAfA/profile-displayphoto-shrink_800_800/0/1695890977795?e=1707955200&v=beta&t=swzzV5aFT6iR_4tVxe9tfHDWJgssGXDYU3rDRzjX-qI";
        Picasso.get().load(imgUrl).error(R.drawable.hide_img).into(developer_img);

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkedInProfile();
            }

            @SuppressLint("QueryPermissionsNeeded")
            private void openLinkedInProfile() {
                String linkedinUrl = "https://www.linkedin.com/in/yogendra-pratap-singh-29123b21a/";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl));

                // Check if there's an app capable of handling the intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                composeEmail("yogi586400@gmail.com");
            }

            @SuppressLint("QueryPermissionsNeeded")
            private void composeEmail(String addresses) { Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, addresses);

                // Set the intent to show the chooser only for email apps
                Intent chooserIntent = Intent.createChooser(intent, "Select Email App");
                if (chooserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooserIntent);
                }
            }
        });

        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithubProfile();
            }

            @SuppressLint("QueryPermissionsNeeded")
            private void openGithubProfile() {
                String githubUrl = "https://github.com/yps09";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));

                PackageManager packageManager = getPackageManager();
                if (packageManager != null && intent.resolveActivity(packageManager) != null) {
                    startActivity(intent);
                } else {
                    // Log an error or display a message for debugging
                    Log.e("GitHubProfile", "Unable to open GitHub profile");
                    // You may want to display a message to the user here
                }
            }

        });
    }
}