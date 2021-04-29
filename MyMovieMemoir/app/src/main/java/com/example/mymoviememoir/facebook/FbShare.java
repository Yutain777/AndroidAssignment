package com.example.mymoviememoir.facebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymoviememoir.R;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

public class FbShare extends FragmentActivity {
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fb_share);
        shareDialog = new ShareDialog(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //  String movieAndYear = bundle.getString("name");
        final String movie = bundle.getString("message");
        TextView textView = findViewById(R.id.textMsg);
        textView.setText(movie);
        Button button = findViewById(R.id.btnSh);
        ImageView imageView = findViewById(R.id.imageView5);
        final Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Hello Facebook")
                            .setContentDescription(
                                    "The 'Hello Facebook' sample  showcases simple Facebook integration")
                            .setContentUrl(Uri.parse("http://developers.facebook.com/"))
                            .setQuote(movie)

                            .build();

                    shareDialog.show(linkContent);


                }

            }
        });


    }
}
