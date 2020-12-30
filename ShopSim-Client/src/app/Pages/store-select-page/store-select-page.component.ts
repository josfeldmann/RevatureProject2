import { Component, OnInit } from '@angular/core';
import { Store } from 'src/app/Models/Store';
import { LoginService } from 'src/app/Services/login.service';
import { StoreService } from 'src/app/Services/store.service';


///Displays all of the stores in a grid and allows the user to select one to proceed
@Component({
  selector: 'app-store-select-page',
  templateUrl: './store-select-page.component.html',
  styleUrls: ['./store-select-page.component.css']
})
export class StoreSelectPageComponent implements OnInit {

  public stores? : Store[] = undefined

  constructor(public loginService : LoginService, public storeService : StoreService) { }

  ngOnInit(): void {
      this.storeService.getStores(this.setStores.bind(this), this.loginService.user?.zipcode);
  }

  setStores(s : Store[]) {
      this.stores = s;
  }
}
