package com.example.testviewer;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

public class RSSAdaputer_outer extends ArrayAdapter<ImageContainer>{

	private LayoutInflater layoutInflater_;
    View holder;
    Context context;

	 public RSSAdaputer_outer(Context context, int id, List<ImageContainer> rss) {
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
	 static class ViewHolder{
		 ImageView icon;
		 TextView text;
	 }

}
