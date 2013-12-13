package com.example.testviewer;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ImageViewActivity extends FragmentActivity{

	ImageView imageView;
	ArrayList<String> imagelist;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_viewer);
	    imageView = (ImageView) findViewById(R.id.imageView1);


	    Intent intent = getIntent();
	    imagelist = intent.getStringArrayListExtra("urllist");
	    String imageUrl = imagelist.get(0);


	    ImageLoader loader = ImageLoader.getInstance();
	    // loadImageを使う場合
	    loader.loadImage(imageUrl, new SimpleImageLoadingListener() {
	        @Override
	        public void onLoadingComplete(String imageUri,View view, Bitmap loadedImage) {
	            imageView.setImageBitmap(loadedImage);
	        }
	    });

	    // displayImageを使う場合
	    loader.displayImage(imageUrl, imageView);

	}

}
