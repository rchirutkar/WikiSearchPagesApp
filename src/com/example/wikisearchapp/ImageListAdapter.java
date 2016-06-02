package com.example.wikisearchapp;

import java.util.ArrayList;
import com.example.ImageManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageListAdapter extends BaseAdapter {

	private Context mContext;

	private LayoutInflater mLayoutInflater;

	private ArrayList<WikiPageFields> list;
	private boolean[] animateEachRow; 

	public ImageManager imageManager;

	public ImageListAdapter(Context context) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = new ArrayList<WikiPageFields>();
		animateEachRow = new boolean[getCount()];
		imageManager = new ImageManager(mContext.getApplicationContext(), 60000);
	}

	static class ViewHolder {
		protected TextView text;
		protected ImageView image;

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		if (convertView == null) {
			view = mLayoutInflater.inflate(R.layout.list_items, null);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.listTitle);
			holder.text.setTextColor(Color.BLACK);
			holder.image = (ImageView) view.findViewById(R.id.listImage);
			view.setTag(holder);
			
			if (!animateEachRow[position]) {
	            animateEachRow[position] = true;
	            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.fade);
	            view.startAnimation(animation);
	        }
		} else {
			holder = (ViewHolder) view.getTag();
		}
		final WikiPageFields page = list.get(position);
		if (page != null) {
			
			holder.text.setText(page.getTitle());
			holder.image.setTag(page.getUrlToPageThumbnail());
			holder.image.setId(position);
			Thumbnail image = new Thumbnail();
			image.setImg(holder.image);
		}
		imageManager.displayImage(page.getUrlToPageThumbnail(), holder.image,
				R.drawable.placeholder);
		return view;
	}

	public void upDateEntries(ArrayList<WikiPageFields> entries) {
		list = entries;
		animateEachRow = new boolean[list.size()];
		notifyDataSetChanged();
	}
}
