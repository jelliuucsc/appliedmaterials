package com.appliedmaterial.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryResults {
	@JsonProperty("items")
	private List<GithubAccounts> githubAccounts;
	
	public GithubRepositoryResults() {
		
	}

	public List<GithubAccounts> getGithubAccount() {
		return githubAccounts;
	}

	public void setGithubAccount(List<GithubAccounts> githubAccounts) {
		this.githubAccounts = githubAccounts;
	}
	
}
