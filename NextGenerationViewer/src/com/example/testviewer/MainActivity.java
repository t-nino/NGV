package com.example.testviewer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class MainActivity extends FragmentActivity implements LoaderCallbacks<ArrayList<ImageContainer>>{

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
	public Loader<ArrayList<ImageContainer>> onCreateLoader(int arg0, Bundle arg1) {
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
	public void onLoadFinished(Loader<ArrayList<ImageContainer>> arg0,ArrayList<ImageContainer> arg1) {

		// TODO 自動生成されたメソッド・スタブ
		if(arg1!=null){
			rssAdapter = new RSSAdaputer(mainActivity,0,arg1);
			listView.setAdapter(rssAdapter);
		}
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<ImageContainer>> arg0) {
		// TODO 自動生成されたメソッド・スタブ

	}

	//処理を呼び出し先で処理させるためにインナークラスにする

	public class RSSAdaputer extends ArrayAdapter<ImageContainer>{

		private LayoutInflater layoutInflater_;
	    View holder;
	    Context context;

		 public RSSAdaputer(Context context, int id, List<ImageContainer> rss) {
			 super(context, id, rss);
			 layoutInflater_ = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 this.context = context;
		 }

		 @Override
			 public View getView(int position, View convertView, ViewGroup parent) {

			 	final ViewHolder holder;
			 	// 特定の行(position)のデータを得る

				 final ImageContainer rss = (ImageContainer)getItem(position);

				 // convertViewは使い回しされている可能性があるのでnullの時だけ新しく作る
				 if (convertView == null) {
					 convertView = layoutInflater_.inflate(R.layout.rss, null);
					 holder = new ViewHolder();
					 holder.text = (TextView)convertView.findViewById(R.id.text);
					 holder.icon = (ImageView)convertView.findViewById(R.id.image);

					 convertView.setTag(holder);

				 }else{
					 holder = (ViewHolder)convertView.getTag();
				 }

				 holder.text.setText(rss.title);

				 if(rss.isLoaded()){
					 holder.icon.setImageBitmap(rss.getBitmap());
				 }else{
					 //ここで、読み込み中のBMPを入れておかないと、使い回しがでてしまうので、対応
					 //とりあえず初期化?
					 holder.icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
					 holder.icon = (ImageView)convertView.findViewById(R.id.image);

					 rss.getLoader().loadImage(rss.getImage(),new SimpleImageLoadingListener(){
				        @Override
				        public void onLoadingComplete(String imageUri,View view, Bitmap loadedImage) {
				            rss.setBitmap(loadedImage);
				            //holder.icon.setImageBitmap(loadedImage);
				            rss.setLoaded(true);

				            //呼び出し元クラスの状態を変更
				            rssAdapter.notifyDataSetChanged();
				            listView.invalidateViews();
				        }
					 });
				 }

				 return convertView;
			 }

		 public String getURL(int position){
			 //エラー防止のため、数チェック。これでいいか後でテスト
			 if(position < getCount()){
				 ImageContainer rss = getItem(position);
				 return rss.getLink();
			 }else{
				 return null;
			 }
		 }
	}
		 static class ViewHolder{
			 ImageView icon;
			 TextView text;
		 }

}
