package com.cclouds.customlistview;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.RecyclerListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.cclouds.controller.AppController;
import com.example.ccloudscustomlistview.R;

public class CustomListAdapter extends BaseAdapter implements RecyclerListener {
	
	private static LayoutInflater mInflater;
	ArrayList<String> mLinksList;
	
	ViewHolderItem mViewHolderItem;

	public CustomListAdapter( ArrayList<String> linksList ) {

		this.mLinksList = linksList;
		mInflater = (LayoutInflater) AppController.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public int getCount() {
		return mLinksList.size();
	}

	@Override
	public String getItem(int position) {
		return mLinksList.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {
		if( convertView == null ) {
			convertView = mInflater.inflate(R.layout.list_item, parent, false);
			mViewHolderItem = new ViewHolderItem();
			mViewHolderItem.mTextViewItem = (TextView) convertView.findViewById(R.id.linkTextView);
			mViewHolderItem.mImageViewItem = (ImageView) convertView.findViewById(R.id.imageView1);
			convertView.setTag(mViewHolderItem);
		}
		else {
			mViewHolderItem = (ViewHolderItem) convertView.getTag();
		}
			
		String imageUrl = getItem(position);
		mViewHolderItem.mTextViewItem.setText(imageUrl);
		getImageView(imageUrl, convertView);

		return convertView;

	}


	private void getImageView(String imageUrl, View view) {

		ImageLoader imageLoader = AppController.getInstance().getImageLoader();
		
		imageLoader.get(imageUrl, new ImageListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("ERROR", "Image Load Error: " + error.getMessage());
			}

			@Override
			public void onResponse(ImageContainer response, boolean arg1) {
				if (response.getBitmap() != null) {
					// load image into imageview
					mViewHolderItem.mImageViewItem.setImageBitmap(response.getBitmap());
					
				}
			}
		});

		// Loading image with placeholder and error image
		imageLoader.get(imageUrl, ImageLoader.getImageListener(
				mViewHolderItem.mImageViewItem, R.drawable.loading, R.drawable.error));

		/***
		 * Used when cache is looked up for responses/ images - else do a network call
		 * Refer - Volley - life cycle of request
		 * http://developer.android.com/training/volley/simple.html
		 */
		
		/*Cache cache = AppController.getInstance().getRequestQueue().getCache();
		Entry entry = cache.get(imageUrl);
		if(entry != null){
			try {
				@SuppressWarnings("unused")
				String data = new String(entry.data, "UTF-8");
				// handle data, like converting it to xml, json, bitmap etc.,
			} catch (UnsupportedEncodingException e) {		
				e.printStackTrace();
			}
		}else{
			// cached response doesn't exists. Make a network call here
		}*/

	}
	
	/**
	 * Why is it used?
	 * http://www.javacodegeeks.com/2013/09/android-viewholder-pattern-example.html
	 * */
	static class ViewHolderItem {
		TextView mTextViewItem;
		ImageView mImageViewItem;
	}

	@Override
	public void onMovedToScrapHeap(View view) {
		
		ViewHolderItem item = (ViewHolderItem) view.getTag();
		item.mImageViewItem.getDrawingCache().recycle();
		
	}
	
}
