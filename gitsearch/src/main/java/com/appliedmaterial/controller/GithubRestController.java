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

import com.appliedmaterial.model.GithubAccounts;
import com.appliedmaterial.model.GithubRepositoryResults;

@RestController
public class GithubRestController {
	@Autowired
	RestTemplate restTemplate;
	
	final static Logger logger = LogManager.getLogger(GithubRestController.class);
	
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/search/{query}")
	public GithubRepositoryResults getSearchResult(HttpServletRequest request, @PathVariable("query") String query) {
		HttpHeaders headers = new HttpHeaders();
		HttpSession session = request.getSession();
//		System.out.println(session.getId());
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<GithubRepositoryResults> entity = new HttpEntity<GithubRepositoryResults>(headers);
		GithubRepositoryResults gh = restTemplate.exchange("https://api.github.com/search/repositories?q=" + query + "+language:javascript&per_page=10", HttpMethod.GET, entity, GithubRepositoryResults.class).getBody();
		Set<GithubAccounts> urls = (Set<GithubAccounts>)session.getAttribute("BOOKMARKS");
		if (urls == null) {
			urls = new HashSet<GithubAccounts>();
			session.setAttribute("BOOKMARKS", urls);
			session.setAttribute("searchResults", gh.getGithubAccount());
			return gh;
		}
		else {
			for(int i = 0 ; i < gh.getGithubAccount().size(); i++) {
				if(urls.contains(gh.getGithubAccount().get(i))) {
					gh.getGithubAccount().get(i).setBookmarked(true);
				}
			}
			session.setAttribute("searchResults", gh.getGithubAccount());
			return gh;
		}
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping ("/addBookmark/{isBookmarked}")
	public Set<GithubAccounts>addUrl(HttpServletRequest request, @RequestBody GithubAccounts account, @PathVariable("isBookmarked") String isBookmarked) {
		HttpSession session = request.getSession();
//		System.out.println(session.getId());
//		System.out.println(account.getRepoName());
		Set<GithubAccounts> urls = (Set<GithubAccounts>)session.getAttribute("BOOKMARKS");
		if(isBookmarked.equals("add")){
			logger.info("Added Bookmark Url:" + account.getRepoUrl());
			urls.add(account);	
		} else {
			urls.remove(account);
			logger.info("Removed Bookmark Url:" + account.getRepoUrl());
//			session.setAttribute("BOOKMARKS", urls);
		}
		System.out.println(urls);
		return (Set<GithubAccounts>) session.getAttribute("BOOKMARKS");
	}
	
	
//	@CrossOrigin(origins = "http://localhost:4200")
//	@GetMapping ("/ok")
//	public Set<String> getOk(){
//		Set<String> a = new HashSet<>();
//		a.add("a");
//		a.add("b");
//		return a;
//	}
//	@CrossOrigin(origins = "http://localhost:4200")
//	@GetMapping ("/getBookmarks")
//	public void getBookmarks(HttpServletRequest request) {
//		System.out.println("In getBookmarks");
//		HttpSession session = request.getSession();
//		System.out.println(session.getId());
//		List<String> urls = (List<String>) session.getAttribute("BOOKMARKS");
//		for(String s:urls) {
//			System.out.println(s);
//		}
//	}
}