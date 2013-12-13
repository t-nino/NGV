package com.example.testviewer;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;



public class AsyncHttpRequestLoader extends AsyncTaskLoader<ArrayList<String>> {

	private String urlStr = null;

	public AsyncHttpRequestLoader(Context context) {
		super(context);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public void setURL(String urlStr){
		this.urlStr = urlStr;
	}
	public void setURL(URL url){
		this.urlStr = url.getPath();
	}


	@Override
	public ArrayList<String> loadInBackground() {
		// TODO 自動生成されたメソッド・スタブ
		return getImageSrc();
	}


	@Override
	protected void onStartLoading() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStartLoading();
		forceLoad();
	}

	/**
	 * 指定されたURLから画像リンク（img src）を抜粋する
	 * @return
	 */

    private ArrayList<String> getImageSrc(){

    	if(urlStr==null){
    		return null;
    	}

    	Document document;

    	ArrayList<String> retArray = new ArrayList<String>();

    	try {
    	    document = Jsoup.connect(urlStr).get();


    	    Elements imgTags = document.getElementsByTag("img");
    	    Iterator<Element> ite = imgTags.iterator();
    	    while(ite.hasNext()){
    	    	Element imgTag = ite.next();

    	    	Attributes attrs = imgTag.attributes();

    	    	Iterator<Attribute> ite2 = attrs.iterator();
    	    	while(ite2.hasNext()){
    	    		Attribute att  = ite2.next();
    	    		String key = att.getKey();
    	    		if(key.equals("src")){
    	    				System.out.println(att.getValue());
    	    				retArray.add(att.getValue());
    	    		}
    	    	}
    	    }
    	    return retArray;

    	} catch (IOException e) {
    	    e.printStackTrace();
        	return null;
    	}
    }

}
