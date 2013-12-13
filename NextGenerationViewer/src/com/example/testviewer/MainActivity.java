package com.example.testviewer;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<RSSContainer>> {

	//AsyncHttpRequest task = new AsyncHttpRequest(this);

	ArrayList<String> imageList;
	MainActivity mainActivity;
	ListView listView;
	RSSAdaputer rssAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageList = new ArrayList<String>();


		listView = (ListView)findViewById(R.id.listView1);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO 自動生成されたメソッド・スタブ
				String url = null;
				if(rssAdapter!=null){
					url = rssAdapter.getURL(arg2);
				}
				if(url!=null){
					Intent nextIntent = new Intent(MainActivity.this,ImageViewActivity.class);
					nextIntent.putExtra("url",url);
					startActivity(nextIntent);
				}
			}
		});

		mainActivity = this;

		Bundle bundle = new Bundle(1);
		//initLoaderだと、最初の一回しか起動しない？
		getSupportLoaderManager().restartLoader(0,null,mainActivity);



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/*
	 * ここでは、Loader呼び出し時の初期化と、Loader作って返す処理をする。
	 * 初期化は、たとえばダイアログの作成など
	 */
	@Override
	public Loader<ArrayList<RSSContainer>> onCreateLoader(int arg0, Bundle arg1) {
		// TODO 自動生成されたメソッド・スタブ

		//ここで、新しく作ったLoaderに、forceloadさせて、開始させるサンプルもあった。
		/*
		AsyncHttpRequestLoader asyncHttpRequestLoader = new AsyncHttpRequestLoader(this);
		asyncHttpRequestLoader.setURL("http://matome.naver.jp/odai/2138676999458499101");
		return asyncHttpRequestLoader;
		*/

		AsyncRSSLoader asyncRSSLoader = new AsyncRSSLoader(this);
		//がぞうまとめ
		asyncRSSLoader.setURL("http://matome.naver.jp/feed/topic/1Luxr");
		return asyncRSSLoader;
	}


	@Override
	public void onLoadFinished(Loader<ArrayList<RSSContainer>> arg0,ArrayList<RSSContainer> arg1) {

		// TODO 自動生成されたメソッド・スタブ
		if(arg1!=null){

			rssAdapter = new RSSAdaputer(mainActivity,0,arg1);
			listView.setAdapter(rssAdapter);

		}
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<RSSContainer>> arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
