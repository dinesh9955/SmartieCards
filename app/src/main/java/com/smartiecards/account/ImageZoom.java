package com.smartiecards.account;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smartiecards.BaseAppCompactActivity;
import com.smartiecards.R;

import uk.co.senab.photoview.PhotoView;


/**
 * Created by AnaadIT on 12/14/2017.
 */

public class ImageZoom extends BaseAppCompactActivity{


    ImageLoader imageLoader;
    DisplayImageOptions options;

    ImageView imageViewDelete;



    @Override
    protected int getLayoutResource() {
        return R.layout.image_zoom;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  setContentView(R.layout.image_zoom);

        imageViewDelete = (ImageView) findViewById(R.id.imageViewDelete);

        try{
            imageLoader = ImageLoader.getInstance();

            imageLoader.init(ImageLoaderConfiguration.createDefault(ImageZoom.this));
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.malecostume_128)
                    .showImageForEmptyUri(R.drawable.malecostume_128)
                    .showImageOnFail(R.drawable.malecostume_128)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
//			.displayer(new RoundedBitmapDisplayer(20))
                  //  .displayer(new CircleBitmapDisplayer(Color.parseColor("#19457d"), 2))
                    .build();
        }catch(Exception e){
           // Log.d(TAG, "myError11: "+e.getMessage());
        }


        Bundle bundle = getIntent().getExtras();

        String imageLink = bundle.getString("imageLink");



        final PhotoView photoView = findViewById(R.id.photo_view);

//        Picasso.with(this)
//                .load("http://pbs.twimg.com/media/Bist9mvIYAAeAyQ.jpg")
//                .into(photoView);
        imageLoader.displayImage(imageLink , photoView, options);



        imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
