package br.com.livraria.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.livraria.model.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{
	

}
