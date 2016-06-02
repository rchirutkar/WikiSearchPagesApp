package com.example.wikisearchapp;

public class WikiPageFields {
	protected String urlToPageThumbnail;
	protected String title;
	
	public WikiPageFields()
	{
		urlToPageThumbnail = "";
		title = "";
	}
	
	public String getUrlToPageThumbnail() {
		return urlToPageThumbnail;
	}
	public void setUrlToPageThumbnail(String urlToPageThumbnail) {
		this.urlToPageThumbnail = urlToPageThumbnail;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
