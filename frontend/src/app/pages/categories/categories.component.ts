import {Component, inject, OnInit} from '@angular/core';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {RequestsService} from '../../services/requests.service';
import {Router} from '@angular/router';

export type Category = {
  id: number;
  name: string;
  url: string;
}

const env = window.__env;

@Component({
  selector: 'app-categories',
  imports: [
    MatCard,
    MatCardTitle,
  ],
  templateUrl: './categories.component.html',
  styleUrl: './categories.component.scss'
})


export class CategoriesComponent implements OnInit {

  protected readonly env = env;

  private requestsService = inject(RequestsService);
  private router = inject(Router);

  categories: Category[] = [];

  ngOnInit() {
    this.requestsService.getCategories().subscribe(categories => {
      this.categories = categories;
    })
  }

  navigateToCategory(categoryUrl: string) {
    this.router.navigate([`/categories/${categoryUrl}`]);
  }


}
