package com.example.testviewer;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class ImageViewActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<String>>,OnGestureListener{

	private ImageView imageView;
	private String url;

	private ViewFlipper flipper;
	private ImageAdaputer imageAdaputer;
	ImageLoader loader;

	private int page = 0; // ページ数
	//private int displayWidth; // 画面サイズ：X
	//private int displayHeight; // 画面サイズ：Y
	Point outSize;
	private int pageCount = 0; // 画面数
	private boolean scrollFlg = false; // スクロールチェック
	private static final int SCROLL_NONE = 0; // スライド距離が一定量を超えない
	private static final int SCROLL_LEFT = 2; //
	private static final int SCROLL_RIGHT = 1; //
	private int slideLimitFlg = SCROLL_NONE; // スライドの状態判定

	@Override
	public void onCreate(final Bundle savedInstanceState){
	    super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_viewer);
	    flipper = (ViewFlipper) findViewById(R.id.flipper);

	    Intent intent = getIntent();
	    url = intent.getStringExtra("url");

		loader = ImageLoader.getInstance();

		Bundle bundle = new Bundle(1);
		//initLoaderだと、最初の一回しか起動しない？
		getSupportLoaderManager().restartLoader(0,null,this);

	}

	@Override
	public Loader<ArrayList<String>> onCreateLoader(int arg0, Bundle arg1) {
		// TODO 自動生成されたメソッド・スタブ

		AsyncHttpRequestLoader asyncHttpRequestLoader = new AsyncHttpRequestLoader(this);
		//がぞうまとめ
		asyncHttpRequestLoader.setURL(url);
		return asyncHttpRequestLoader;
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<String>> arg0,
			ArrayList<String> arg1) {
		// TODO 自動生成されたメソッド・スタブ

		//とりあえず、全画像をフリッパーの中に入れる。あまりよくない。

		Iterator<String> ite = arg1.iterator();



		while(ite.hasNext()){
			final ImageView imageView = new ImageView(this);
			 // loadImageを使う場合
			String imgurl = ite.next();
			 loader.loadImage(imgurl, new SimpleImageLoadingListener() {
			        @Override
			        public void onLoadingComplete(String imageUri,View view, Bitmap loadedImage) {
			            imageView.setImageBitmap(loadedImage);
			        }
			    });
			 flipper.addView(imageView);
		}

	}

	@Override
	public void onLoaderReset(Loader<ArrayList<String>> arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

System.out.println("onFling");
		// TODO 自動生成されたメソッド・スタブ
        float dx = Math.abs(velocityX);
        float dy = Math.abs(velocityY);
        if (dx > dy && dx > 150) {
            if (e1.getX() < e2.getX()) {
                //flipper.setInAnimation(mInFromLeft);
                //flipper.setOutAnimation(mOutToRight);
            	System.out.println("prev");
                flipper.showPrevious();

                //setDate(mFromDate);
            } else {
                //flipper.setInAnimation(mInFromRight);
                //flipper.setOutAnimation(mOutToLeft);
                flipper.showNext();
                System.out.println("next");
                //setDate(mToDate);
            }
            return true;
        }
        return false;

	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
