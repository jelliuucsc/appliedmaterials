import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//components
import { BookmarksComponent } from './bookmarks/bookmarks.component';
import { SearchResultsComponent } from './search-results/search-results.component';


//routes
const routes: Routes = [
    {path: 'bookmarks', component:BookmarksComponent},
    {path: 'search', component:SearchResultsComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

export const routableComponents = [
  BookmarksComponent,
  SearchResultsComponent
];