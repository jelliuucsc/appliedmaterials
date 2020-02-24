import { Component } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { NgForm } from '@angular/forms';
import {Repository,GithubRepoService} from './github-repo.service'
import {Observable} from 'rxjs/Observable'
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'GithubSearchApp';
  repositories:Repository[];
  query:string;
  constructor(private route:ActivatedRoute, private router:Router, private githubRepoService:GithubRepoService) { }

  ngOnInit(): void {
    // this.githubRepoService.currRepoList.subscribe(repositories=> this.repositories = repositories)

  }

  showBookmarks(){
    console.log("bookmark")
    this.router.navigate(['bookmarks'], {relativeTo:this.route});
    
  }

  searchGithub(){
    console.log("search")
    this.router.navigate(['search'], {relativeTo:this.route});
  }

  onFormSubmit(repoForm:NgForm){
    this.query = repoForm.controls['result'].value;
    this.githubRepoService.getRepositories(repoForm.controls['result'].value, 1).subscribe(account=> {this.repositories= account});
  }
}
