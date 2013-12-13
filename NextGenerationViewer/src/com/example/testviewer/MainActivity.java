package com.example.testviewer;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<RSSContainer>> {

	//AsyncHttpRequest task = new AsyncHttpRequest(this);

	ArrayList<String> imageList;
	TextView text;
	MainActivity mainActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imageList = new ArrayList<String>();
		Button button_start = (Button) findViewById(R.id.button1);
		Button button_show = (Button) findViewById(R.id.button2);

		text = (TextView)findViewById(R.id.text_main);
		mainActivity = this;



		button_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ

				Bundle bundle = new Bundle(1);
				//initLoaderだと、最初の一回しか起動しない？
				getSupportLoaderManager().restartLoader(0,null,mainActivity);

				//task.execute();
			}
		});

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
		StringBuffer sb = new StringBuffer();
		if(arg1!=null){

			Iterator<RSSContainer> ite = arg1.iterator();
			while(ite.hasNext()){
				RSSContainer rss = ite.next();
				sb.append(rss.getTitle());
				sb.append("\n");
			}
			text.setText(sb.toString());
/*
			Intent nextIntent = new Intent(MainActivity.this,ImageViewActivity.class);
			nextIntent.putExtra("urllist",arg1);
			startActivity(nextIntent);
*/
		}
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<RSSContainer>> arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
