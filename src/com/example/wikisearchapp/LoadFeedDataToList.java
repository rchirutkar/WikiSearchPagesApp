package com.example.wikisearchapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class LoadFeedDataToList extends AsyncTask<String, Integer, Void> {

	ArrayList<WikiPageFields> tokenList;

	private final ImageListAdapter mAdapter;
	ProgressBar mLoadingProgress;
	MainActivity mActivity;

	public LoadFeedDataToList(Context loadingProgress, ImageListAdapter adapter) {
		mAdapter = adapter;
		mActivity = (MainActivity)loadingProgress;
		mLoadingProgress = mActivity.getLoadingProgressBar();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mLoadingProgress.setVisibility(View.VISIBLE);       
	    
	}

	@Override
	protected Void doInBackground(String... searchText) {
		// TODO Auto-generated method stub
		
		String url = "https://en.wikipedia.org/w/api.php?action=query&format=json&prop=pageimages&" +
				"list=&generator=prefixsearch&callback=&piprop=thumbnail&pithumbsize=100&pilimit=50&" +
				"gpslimit=50&gpssearch="+ searchText[0];

		tokenList = new ArrayList<WikiPageFields>();

		try {
			
			String resultJSONString = getJsonFromURL(url).replaceFirst("\\/\\*\\*\\/\\(", "").replace("\\)", "");
			System.out.println("JSON : " + resultJSONString);
			JSONObject jObject = new JSONObject(resultJSONString);

			JSONObject pages = jObject.getJSONObject("query").getJSONObject(
					"pages");

			parse(pages.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		//return tokenList;

	}

	@Override
	protected void onPostExecute(Void result) {

		mAdapter.upDateEntries(tokenList);
		mLoadingProgress.setVisibility(View.GONE);

	}

	public void parse(String json) throws JsonProcessingException, IOException {

		JsonFactory factory = new JsonFactory();

		ObjectMapper mapper = new ObjectMapper(factory);
		JsonNode rootNode = mapper.readTree(json);
		
		int count = 0;
		Iterator<Map.Entry<String, JsonNode>> pagesIterator = rootNode
				.getFields();
		while (pagesIterator.hasNext()) {
 
			Map.Entry<String, JsonNode> pages = pagesIterator.next();
			Iterator<Map.Entry<String, JsonNode>> pageFieldsIterator = pages
					.getValue().getFields();
			WikiPageFields page = null;
			while (pageFieldsIterator.hasNext()) {
				count +=20;
				publishProgress(count,count);
				
				Map.Entry<String, JsonNode> field = pageFieldsIterator.next();
				
				if (field.getKey().equals("pageid")) {
					//page = new WikiPageFields();
				}
				if (field.getKey().equals("title")) {
					page = new WikiPageFields();
					page.setTitle(field.getValue().asText());
					
				}
				
				if (field.getKey().equals("thumbnail")) {
					
					page.setUrlToPageThumbnail(field.getValue().getFields()
							.next().getValue().asText());
				}
			}
			tokenList.add(page);
		}

	}

	public String getJsonFromURL(String urls) {
		InputStream stream = null;
		try {

			URL url = new URL(urls);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setReadTimeout(3000);
			// conn.setConnectTimeout(3000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.connect();
			conn.getContentLength();
			int count =0;
			stream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line = "";
			StringBuilder buffer = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
				count +=60;
				publishProgress(0,count);
			}
			
			
			return buffer.toString();

		} catch (FileNotFoundException e) {

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				if (stream != null)
					stream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		
//	    mLoadingProgress.setProgress(values[0]);
//	    mLoadingProgress.setSecondaryProgress(values[0]+values[1]+15);
	}

}
