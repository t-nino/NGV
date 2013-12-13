package com.example.testviewer;



import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.ImageLoader;

public class RSSContainer{

	public String title;
	public String link;
	public String thumnail;
	public Bitmap bitmap;
	public boolean loaded;
	//継承して作りたかったが、シングルトンであるため継承できない。仕方ないので、自分で持つ。
	public ImageLoader loader;

	public RSSContainer(ImageLoader loader) {
		// TODO 自動生成されたコンストラクター・スタブ
		title  = null;
		link  = null;
		thumnail = null;
		bitmap = null;
		loaded = false;
		this.loader = loader;
	}



	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getThumnail() {
		return thumnail;
	}
	public void setThumnail(String thumnail) {
		this.thumnail = thumnail;
	}
	public Bitmap getBitmap(){
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap){
		this.bitmap = bitmap;
	}
	public boolean isLoaded(){
		return loaded;
	}
	public void setLoaded(boolean loaded){
		this.loaded = loaded;
	}
	public ImageLoader getLoader(){
		return loader;
	}
	public void setLoader(ImageLoader loader){
		this.loader = loader;
	}

}
