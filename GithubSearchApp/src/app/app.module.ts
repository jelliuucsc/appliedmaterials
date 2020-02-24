import { BrowserModule, } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import {FormsModule, NgForm} from '@angular/forms';
import {CommonModule} from '@angular/common'
import { HttpClientModule,  } from '@angular/common/http';
import './rxjs-extensions';

//service
import {GithubRepoService} from './github-repo.service'

//components
import { AppComponent } from './app.component';
import {BookmarksComponent} from './bookmarks/bookmarks.component'
import {SearchResultsComponent} from './search-results/search-results.component'

@NgModule({
  declarations: [
    AppComponent,
    BookmarksComponent,
    SearchResultsComponent


  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CommonModule
  ],
  providers: [GithubRepoService],
  bootstrap: [AppComponent]
})
export class AppModule { }
