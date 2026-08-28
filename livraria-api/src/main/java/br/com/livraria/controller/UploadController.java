package br.com.livraria.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("uploads")
@Api(description = "Endpoint para upload de arquivos.", tags = {"Upload API"})
@Slf4j
public class UploadController {

	@Value("${app.upload.dir}")
	private String uploadDir;

	@ApiOperation("${api.upload.imagem}")
	@PostMapping("/imagem")
	@ResponseStatus(HttpStatus.CREATED)
	public String uploadImagem(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		if (file.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Arquivo não pode ser vazio");
		}

		String extensao = "";
		String nomeOriginal = file.getOriginalFilename();
		if (nomeOriginal != null && nomeOriginal.contains(".")) {
			extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf('.'));
		}
		String nomeArquivo = UUID.randomUUID().toString() + extensao;

		try {
			Path destino = Paths.get(uploadDir);
			Files.createDirectories(destino);
			Files.copy(file.getInputStream(), destino.resolve(nomeArquivo), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			log.error("Erro ao salvar arquivo de imagem", e);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao salvar o arquivo");
		}

		String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		return baseUrl + "/uploads/" + nomeArquivo;
	}

}
