package br.com.livraria.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.livraria.dto.RoleDTO;
import br.com.livraria.model.Role;
import br.com.livraria.service.impl.RoleServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
@Api(description = "Endpoint para criar, atualizar, deletar, excluir e buscar o Perfil.", tags = {"Perfil API"})
@Slf4j
public class RoleController {

	@Autowired
	private RoleServiceImpl roleService;

	@Autowired
	private ModelMapper modelMapper;

	@ApiOperation(value = "${api.role.listar}")
	@GetMapping("/listar")
	public Page<RoleDTO> listarRoles(RoleDTO dto, @RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
		Role filter = modelMapper.map(dto, Role.class);
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Role> result = roleService.listarRoles(filter, pageRequest);
		List<RoleDTO> list = result.getContent().stream().map(entity -> modelMapper.map(entity, RoleDTO.class))
				.collect(Collectors.toList());
		return new PageImpl<RoleDTO>(list, pageRequest, result.getTotalElements());
	}

	@ApiOperation("${api.role.salvar}")
	@PostMapping(value = "/salvar")
	@ResponseStatus(HttpStatus.CREATED)
	public RoleDTO salvar(@Valid @RequestBody RoleDTO dto) {
		log.info("Criando um perfil: {}", dto.getDescricao());
		Role role = this.modelMapper.map(dto, Role.class);
		role = roleService.salvar(role);
		return this.modelMapper.map(role, RoleDTO.class);
	}

	@ApiOperation("${api.role.editar}")
	@PutMapping("/{id}")
	public RoleDTO editar(@PathVariable(value = "id") Long id, @Valid @RequestBody RoleDTO dto) {
		log.info("Editar um perfil por id: {}", id);
		return roleService.buscarPorId(id).map(role -> {
			role.setDescricao(dto.getDescricao());
			role = roleService.editar(role);
			return modelMapper.map(role, RoleDTO.class);
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	@ApiOperation("${api.role.excluir}")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable(value = "id") Long id) {
		log.info("Excluir um perfil por id: {}", id);
		Role role = roleService.buscarPorId(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		roleService.excluir(role);
	}

	@ApiOperation("${api.role.obterId}")
	@GetMapping("{id}")
	public RoleDTO buscarPorId(@PathVariable(value = "id") Long id) {
		log.info("Obter detalhes de um perfil pelo id: {}", id);
		return roleService.buscarPorId(id).map(role -> modelMapper.map(role, RoleDTO.class))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

}
