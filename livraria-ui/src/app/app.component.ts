import { Component, OnInit } from '@angular/core';
import { RoleService } from './perfil/role.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Livraria';

  constructor(private roleService: RoleService) { }

  ngOnInit() {
    this.roleService.carregarRoles();
  }
}
