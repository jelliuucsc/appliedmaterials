import { Component, OnInit, Input } from '@angular/core';
import {Repository,GithubRepoService} from '../github-repo.service'
import {Observable} from 'rxjs/Observable'

@Component({
  selector: 'search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {

  constructor(private githubRepoService:GithubRepoService) { }
  repositories:Repository[];
  bookmarks:Repository[];
  pageNumber:number;
  query:string;
 
  ngOnInit(): void {
    this.githubRepoService.currRepoList.subscribe(repositories=>{this.repositories = repositories});
    this.githubRepoService.currBookmarkList.subscribe(repositories=>{this.bookmarks = repositories});
    this.githubRepoService.currQueryString.subscribe(query=>{this.query = query});
    this.githubRepoService.currPageNumber.subscribe(pageNumber=>{this.pageNumber = pageNumber});
  }
  getPage(page:string){
    console.log(this.pageNumber)
    if(page == "next"){
      this.githubRepoService.getRepositories(this.query, this.pageNumber+1).subscribe(account=> {this.repositories= account});
    }
    else{
      this.githubRepoService.getRepositories(this.query, this.pageNumber-1).subscribe(account=> {this.repositories= account});
    }
  }
    
  setBookmark(bookmark:Repository,isBookmarked:string){
    if(isBookmarked == "add"){
      this.githubRepoService.addBookmark(bookmark).subscribe(repositories=> this.bookmarks = repositories);
      for(let repository of this.repositories){
        if(repository.html_url === bookmark.html_url){
          repository.bookmarked = true;
        }
      }
      
    }
    else{
      this.githubRepoService.removeBookmark(bookmark).subscribe(repositories=> this.bookmarks = repositories);
      for(let repository of this.repositories){
        if(repository.html_url === bookmark.html_url){
          repository.bookmarked = false;
        }
      }
    }
  }
}
