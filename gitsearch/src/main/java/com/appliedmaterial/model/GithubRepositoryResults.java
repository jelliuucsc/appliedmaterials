package com.appliedmaterial.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubRepositoryResults {
	@JsonProperty("items")
	private List<GithubAccount> githubAccounts;
	
	public GithubRepositoryResults() {
		
	}

	public List<GithubAccount> getGithubAccount() {
		return githubAccounts;
	}

	public void setGithubAccount(List<GithubAccount> githubAccounts) {
		this.githubAccounts = githubAccounts;
	}
	
}
