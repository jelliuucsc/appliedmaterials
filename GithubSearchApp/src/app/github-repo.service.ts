import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { map } from 'rxjs/operators'; 
import{BehaviorSubject,Observable } from 'rxjs'

export class Repository {
  owner:Array<string>;
  name:string;
  html_url:string;
  language:string;
  bookmarked:boolean;
}

@Injectable({
  providedIn: 'root'
})
export class GithubRepoService {

  constructor(private http:HttpClient) { 
  }
  
  repositories:Repository[];
  bookmarks:Repository[];
  query:string;

  private repoList = new BehaviorSubject<Repository[]>(this.repositories);
  currRepoList = this.repoList.asObservable();
  
  private bookmarkList = new BehaviorSubject<Repository[]>(this.bookmarks);
  currBookmarkList = this.bookmarkList.asObservable();

  private queryString = new BehaviorSubject<string>(" ");
  currQueryString = this.queryString.asObservable();

  private pageNumber = new BehaviorSubject<number>(undefined);
  currPageNumber = this.pageNumber.asObservable();

 
  setQuery(query:string){
    this.queryString.next(query);
  }

 
  getRepositories(query:string, pageNumber:number){
    this.pageNumber.next(pageNumber);
    this.queryString.next(query);
    return this.http.get("http://localhost:8080/search/"+query + "/" + pageNumber,{withCredentials:true})
          .pipe(map((data: any) => {this.repoList.next(<Repository[]>data.githubAccount);return <Repository[]>data.githubAccount}));
  }

  addBookmark(repository:Repository){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        'Access-Control-Allow-Credentials': 'true'
      })
    };
    return this.http.post("http://localhost:8080/addBookmark", repository,{withCredentials:true})
          .pipe(map((data: any) => {this.bookmarkList.next(<Repository[]>data); return <Repository[]>data}));

  }
  removeBookmark(repository:Repository){
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Access-Control-Allow-Origin': 'http://localhost:4200',
        'Access-Control-Allow-Credentials': 'true'
      })
    };
    return this.http.post("http://localhost:8080/removeBookmark", repository,{withCredentials:true})
          .pipe(map((data: any) => {this.bookmarkList.next(<Repository[]>data); return <Repository[]>data}));
  }
}
