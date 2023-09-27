package org.erick.pessoa.resource;

import java.net.URI;
import java.util.List;

import org.erick.pessoa.domain.Endereco;
import org.erick.pessoa.domain.Pessoa;
import org.erick.pessoa.dto.EnderecoDTO;
import org.erick.pessoa.dto.PessoaDTO;
import org.erick.pessoa.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequestMapping("/pessoa")
@RestController
public class PessoaResource {
	
	@Autowired
	private PessoaService pessoaService;
	
	@PostMapping
	public ResponseEntity<?> criarPessoa(@RequestBody PessoaDTO pessoaDTO) {
		Pessoa pessoa = pessoaService.criarPessoa(pessoaDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping
	public ResponseEntity<Void> editarPessoa(@RequestBody PessoaDTO pessoa) {
		pessoaService.editarPessoa(pessoa);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> consultarPessoaPorId(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.consultarPessoaPorId(id);
		return ResponseEntity.ok(pessoa);
	}
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> listarPessoas() {
		return ResponseEntity.ok(pessoaService.listarPessoas());
	}
	
	@PostMapping("/endereco")
	public ResponseEntity<Void> criarEnderecoParaPessoa(@RequestBody EnderecoDTO enderecoDTO) {
		pessoaService.criarEndereco(enderecoDTO);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/endereco/{id}")
	public ResponseEntity<List<Endereco>> listarEnderecosDeUmaPessoa(@PathVariable Long id) {
		return ResponseEntity.ok(pessoaService.listarEnderecoPorIdPessoa(id));
	}
	
	@PatchMapping("/endereco")
	public ResponseEntity<Void> configurarEnderecoPrincipalPessoa(@RequestParam Long idPessoa, @RequestParam Long idEndereco) {
		pessoaService.configurarEnderecoPrincipalPessoa(idEndereco, idPessoa);
		return ResponseEntity.noContent().build();
	}
}
