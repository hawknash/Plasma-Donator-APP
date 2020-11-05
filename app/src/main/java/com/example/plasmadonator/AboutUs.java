package com.example.plasmadonator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        setTitle("About Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        FancyAboutPage fancyAboutPage=findViewById(R.id.fancyaboutpage);
        //fancyAboutPage.setCoverTintColor(Color.BLUE);  //Optional
        fancyAboutPage.setCover(R.drawable.back); //Pass your cover image
        fancyAboutPage.setName("Plasma Donator");
        fancyAboutPage.setDescription("Plasma Donator app is mainly based on making plasma therapy possible for patients.");
        fancyAboutPage.setAppIcon(R.drawable.plasmalogo); //Pass your app icon image
        fancyAboutPage.setAppName("Plasma Donator App");
        fancyAboutPage.setVersionNameAsAppSubTitle("1.0.0");
        fancyAboutPage.setAppDescription("Due to the current Worldwide Pandemic caused by the spread of the coronavirus, one proven way to help stabilize patients is the Plasma Therapy.\n\nThe Project aims at building an application to coordinate Plasma Donation.\n\nThe basic building aim is to provide Plasma Donation Service. Plasma Donator is a Mobile Application that is designed to store, process, retrieve and analyze information concerned with the administrative and inventory management within a Plasma Bank.");
        fancyAboutPage.addEmailLink("plasmadonator123@gmail.com");     //Add your email id
        fancyAboutPage.addFacebookLink("https://www.facebook.com");  //Add your facebook address url
        fancyAboutPage.addTwitterLink("https://twitter.com");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com");
        fancyAboutPage.addGitHubLink("https://github.com");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
