package com.cclouds.customlistview;

import java.util.ArrayList;

import com.example.ccloudscustomlistview.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ListView listView = (ListView) findViewById(R.id.listView1);
		ArrayList<String> imageLinksList = new ArrayList<String>();
		imageLinksList.add("http://i.imgur.com/7spzG.png");
		imageLinksList.add("http://i.stack.imgur.com/ILTQq.png");
		imageLinksList.add("http://4.bp.blogspot.com/-IJXe-eEJTmA/U-G6WnOSe_I/AAAAAAAAAtQ/v_V9BcQu9Ok/s33-c/336914de2a.png");
		imageLinksList.add("http://www.fordesigner.com/imguploads/Image/cjbc/zcool/png20080526/1211810400.png");
		CustomListAdapter customListAdapter = new CustomListAdapter(imageLinksList);
		listView.setAdapter(customListAdapter);
		listView.setRecyclerListener(customListAdapter);
		
	}

	

}
