package com.posting.myproject;

import android.content.Intent;
import android.os.Bundle;

import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import com.bumptech.glide.Glide;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by delaroy on 3/22/17.
 */
public class DetailActivity extends AppCompatActivity {
    TextView Link,Link12, Username,Link2,Link3,Link1;
    Toolbar mActionBarToolbar;
    CircleImageView imageView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        imageView =  findViewById(R.id.user_image_header);
        Username = (TextView) findViewById(R.id.username);

        Link = (TextView) findViewById(R.id.link);
        Link1 = (TextView) findViewById(R.id.link1);
        Link2 = (TextView) findViewById(R.id.link2);
        Link3 = (TextView) findViewById(R.id.link3);

        Link12 = (TextView) findViewById(R.id.link12);


        String username = getIntent().getExtras().getString("login");
        String avatarUrl = getIntent().getExtras().getString("avatar_url");
        String link = getIntent().getExtras().getString("html_url");
        String link1 = getIntent().getExtras().getString("forks_url");
        String link2 = getIntent().getExtras().getString("keys_url");
        String link3 = getIntent().getExtras().getString("hooks_url");

        Link12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForksActivity.class);
                intent.putExtra("forks_url", link1);
                startActivity(intent);

            }
        });
        Link.setText(link);
        Linkify.addLinks(Link, Linkify.WEB_URLS);

        Link1.setText(link1);
        Linkify.addLinks(Link1, Linkify.WEB_URLS);

        Link2.setText(link2);
        Linkify.addLinks(Link2, Linkify.WEB_URLS);

        Link3.setText(link3);
        Linkify.addLinks(Link3, Linkify.WEB_URLS);

        Username.setText("Username  :"+"  "+username);
        Glide.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.load)
                .into(imageView);

//        getSupportActionBar().setTitle("Details Activity");
    }

    private Intent createShareForcastIntent(){
        String username = getIntent().getExtras().getString("login");
        String link = getIntent().getExtras().getString("link");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
            .setType("text/plain")
                .setText("Check out this awesome developer @" + username + ", " + link)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForcastIntent());
        return true;
    }
}
