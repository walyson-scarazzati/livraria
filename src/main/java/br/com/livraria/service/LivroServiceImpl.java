package br.com.livraria.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.livraria.model.Livro;
import br.com.livraria.repository.LivroRepository;

@Service
public class LivroServiceImpl implements LivroService {
	
	@Autowired 
	private LivroRepository livrariaDao;
	
	@Override
	public Livro salvar(Livro livraria) {
		return livrariaDao.save(livraria);
	}

	@Override
	public void editar(Livro livraria) {
		livrariaDao.saveAndFlush(livraria); 
	}

	@Override
	public void excluir(Long id) {
		livrariaDao.deleteById(id); 
	}

	@Override
	public Livro findById(Long id) {
		return livrariaDao.getOne(id);
	}

	@Override
	public List<Livro> findAll() {
		return livrariaDao.findAll();
	}

	@Override
	public List<Livro> findByTitulo(String nome) {
		return livrariaDao.findByTitulo(nome);
	}

}
