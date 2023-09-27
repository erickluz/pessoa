package org.erick.pessoa.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.erick.pessoa.domain.Endereco;
import org.erick.pessoa.domain.Pessoa;
import org.erick.pessoa.domain.PessoaEndereco;
import org.erick.pessoa.dto.EnderecoDTO;
import org.erick.pessoa.dto.PessoaDTO;
import org.erick.pessoa.exceptions.PessoaException;
import org.erick.pessoa.repository.EnderecoRepository;
import org.erick.pessoa.repository.PessoaEnderecoRepository;
import org.erick.pessoa.repository.PessoaRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PessoaEnderecoRepository pessoaEnderecoRepository;

	public Pessoa criarPessoa(PessoaDTO pessoaDTO) {
		return pessoaRepository.save(criarPessoaDeDTO(pessoaDTO));
	}

	private Pessoa criarPessoaDeDTO(PessoaDTO pessoaDTO) {
		return new Pessoa(null, pessoaDTO.getNome(), obterData(pessoaDTO), pessoaDTO.getEnderecoPrincipal());
	}

	private LocalDateTime obterData(PessoaDTO pessoaDTO) {
		LocalDateTime dataNascimento;
		try {
			dataNascimento = LocalDateTime.parse(pessoaDTO.getDataNascimento() + " 00:00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
		} catch (Exception e) {
			throw new PessoaException("Data Invalida. Formato esperado dd/MM/yyyy");
		}
		return dataNascimento;
	}

	public Pessoa editarPessoa(PessoaDTO pessoaDTO) {
		return pessoaRepository.save(editarPessoaDeDTO(pessoaDTO));
	}
	
	private Pessoa editarPessoaDeDTO(PessoaDTO pessoaDTO) {
		return new Pessoa(Long.valueOf(pessoaDTO.getId()), pessoaDTO.getNome(), obterData(pessoaDTO), pessoaDTO.getEnderecoPrincipal());
	}

	public Pessoa consultarPessoaPorId(Long id) {
		validarParamentroId(id);
		return pessoaRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id, "Pessoa não encontrada"));
	}

	private void validarParamentroId(Long id) {
		if (isParametroIdInvalido(id)) {
			throw new PessoaException("Consulta Inválida. O campo ID é obrigatório");
		}
	}

	private boolean isParametroIdInvalido(Long id) {
		return id == null;
	}

	public List<Pessoa> listarPessoas() {
		return pessoaRepository.findAll();
	}

	public Endereco criarEndereco(EnderecoDTO enderecoDTO) {
		validarEnderecoDTO(enderecoDTO);
		Pessoa pessoa = obterPessoa(enderecoDTO);
		Endereco endereco = parseEndereco(enderecoDTO);
		endereco = enderecoRepository.save(endereco);
		pessoaEnderecoRepository.save(new PessoaEndereco(pessoa, endereco));
		return endereco;
	}

	private Pessoa obterPessoa(EnderecoDTO enderecoDTO) {
		return pessoaRepository.findById(enderecoDTO.getIdPessoa()).orElseThrow(() -> new ObjectNotFoundException(enderecoDTO.getIdPessoa(), "Pessoa não encontrada"));
	}

	private Endereco parseEndereco(EnderecoDTO enderecoDTO) {
		return new Endereco(enderecoDTO.getLogradouro(), enderecoDTO.getCep(), enderecoDTO.getNumero(), enderecoDTO.getCidade());
	}

	private void validarEnderecoDTO(EnderecoDTO enderecoDTO) {
		if (isPessoaEnderecoInvalido(enderecoDTO)) {
			throw new PessoaException("Endeço Inválido. O campo Pessoa é obrigatório");
		}
	}

	private boolean isPessoaEnderecoInvalido(EnderecoDTO enderecoDTO) {
		return enderecoDTO.getIdPessoa() == null;
	}

	public List<Endereco> listarEnderecoPorIdPessoa(Long id) {
		validarParametro(id);
		return pessoaEnderecoRepository.listarEnderecosPorIdPessoa(id);
	}

	private void validarParametro(Long id) {
		if (isParametroIdPessoaInvalido(id)) {
			throw new PessoaException("Consulta Inválida. O campo Pessoa é obrigatório");
		}
	}
	
	private boolean isParametroIdPessoaInvalido(Long id) {
		return id == null;
	}

	public Pessoa configurarEnderecoPrincipalPessoa(Long idEndereco, Long idPessoa) {
		Endereco endereco = obterEndereco(idEndereco);
		Pessoa pessoa = obterPessoa(idPessoa);
		pessoa.setEnderecoPrincipal(endereco);
		return pessoaRepository.save(pessoa);
	}

	private Pessoa obterPessoa(Long idPessoa) {
		return pessoaRepository.findById(idPessoa).orElseThrow(() -> new ObjectNotFoundException(idPessoa, "Pessoa não encontrada"));
	}

	private Endereco obterEndereco(Long idEndereco) {
		return enderecoRepository.findById(idEndereco).orElseThrow(() -> new ObjectNotFoundException(idEndereco, "Pessoa não encontrada"));
	}

}
