package com.nwpu.bsss.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Response body for GET("/articleList")
 */
public class ArticleListResponse {

	private List<ArticleBrief> articles;

	public ArticleListResponse() {
		this.articles = new ArrayList<>();
	}

	public ArticleListResponse(List<ArticleBrief> articles) {
		this.articles = articles;
	}

	public List<ArticleBrief> getArticles() {
		return this.articles;
	}

	public void setArticles(List<ArticleBrief> articles) {
		this.articles = articles;
	}

	public static class ArticleBrief {
		private long articleId;
		private String title;

		public long getArticleId() {
			return this.articleId;
		}

		public void setArticleId(long articleId) {
			this.articleId = articleId;
		}

		public String getTitle() {
			return this.title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	}
	
}
