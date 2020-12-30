import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/Services/login.service';
import {Router} from '@angular/router';

///Navbar at the top of the site. logs the user in if the session just started and there is a saved username/password.
// Allows user to access their cart, orders, services and log in/out.

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.css']
})
export class NavBarComponent implements OnInit {

  constructor(public loginService : LoginService, public router : Router) { }

  ngOnInit(): void {
    if (this.loginService.user) {

    } else {

      let username = localStorage.getItem('username');
      let pass = localStorage.getItem('password');
      if (username && pass) {
        this.loginService.sendLogin(username, pass, this.handleLogin.bind(this));
    }

        
      
    }
  }

  handleLogin(b : boolean) {
    if (b) {
      this.router.navigate(['selectStore']);
    } else {

    }
  }

  logOut(){
    this.loginService.logOut();
    this.router.navigate(['home']);
  }

}
