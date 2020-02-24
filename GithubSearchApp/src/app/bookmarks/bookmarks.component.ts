import { Component, OnInit } from '@angular/core';
import {Repository,GithubRepoService} from '../github-repo.service'
import {Observable} from 'rxjs/Observable'
@Component({
  selector: 'bookmarks',
  templateUrl: './bookmarks.component.html',
  styleUrls: ['./bookmarks.component.css']
})
export class BookmarksComponent implements OnInit {

  constructor(private githubRepoService:GithubRepoService) { }

  bookmarks:Repository[];
  
  ngOnInit(): void {
    this.githubRepoService.currBookmarkList.subscribe(bookmarks=>{this.bookmarks=bookmarks});
  }

  setBookmark(bookmark:Repository,isBookmarked:string){   
      this.githubRepoService.removeBookmark(bookmark).subscribe(repositories=> this.bookmarks = repositories);
  }
}
