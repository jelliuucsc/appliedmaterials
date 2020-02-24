package com.appliedmaterial.controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.appliedmaterial.model.GithubAccount;
import com.appliedmaterial.model.GithubRepositoryResults;

@RestController
public class GithubRestController {
	@Autowired
	RestTemplate restTemplate;

	final static Logger logger = LogManager.getLogger(GithubRestController.class);
	private static final String SESSION_BOOKMARKS = "SESSION_BOOKMARKS";
	private static final String SESSION_SEARCH_RESULT = "SESSION_SEARCH_RESULT";

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/search/{query}/{pageNumber}")
	public GithubRepositoryResults getSearchResult(HttpServletRequest request, @PathVariable("query") String query, @PathVariable("pageNumber") String pageNumber) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
		logger.debug(session.getId());
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<GithubRepositoryResults> entity = new HttpEntity<GithubRepositoryResults>(headers);
		GithubRepositoryResults gh = restTemplate
				.exchange(
						"https://api.github.com/search/repositories?q=" + query
								+ "+language:javascript&per_page=10&page=" + pageNumber,
						HttpMethod.GET, entity, GithubRepositoryResults.class)
				.getBody();

		@SuppressWarnings("unchecked")
		Set<GithubAccount> urls = (Set<GithubAccount>) session.getAttribute(SESSION_BOOKMARKS);

		if (urls == null) {
			urls = new HashSet<GithubAccount>();
			session.setAttribute(SESSION_BOOKMARKS, urls);
			session.setAttribute(SESSION_SEARCH_RESULT, gh.getGithubAccount());
			return gh;
		} else {
			for (int i = 0; i < gh.getGithubAccount().size(); i++) {
				if (urls.contains(gh.getGithubAccount().get(i))) {
					gh.getGithubAccount().get(i).setBookmarked(true);
				}
			}
			session.setAttribute(SESSION_SEARCH_RESULT, gh.getGithubAccount());
			return gh;
		}
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/addBookmark")
	public Set<GithubAccount> addUrl(HttpServletRequest request, @RequestBody GithubAccount account) {
		HttpSession session = request.getSession();

		logger.debug(session.getId());
		logger.debug(account.getRepoName());

		@SuppressWarnings("unchecked")
		Set<GithubAccount> urls = (Set<GithubAccount>) session.getAttribute(SESSION_BOOKMARKS);

		logger.info("Added Bookmark Url:" + account.getRepoUrl());
		urls.add(account);

		return urls;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/removeBookmark")
	public Set<GithubAccount> removeUrl(HttpServletRequest request, @RequestBody GithubAccount account) {
		HttpSession session = request.getSession();

		logger.debug(session.getId());
		logger.debug(account.getRepoName());

		@SuppressWarnings("unchecked")
		Set<GithubAccount> urls = (Set<GithubAccount>) session.getAttribute(SESSION_BOOKMARKS);

		urls.remove(account);
		logger.info("Removed Bookmark Url:" + account.getRepoUrl());

		return urls;
	}
}