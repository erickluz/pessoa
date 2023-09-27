package org.erick.pessoa.repository;

import java.util.List;

import org.erick.pessoa.domain.Endereco;
import org.erick.pessoa.domain.PessoaEndereco;
import org.erick.pessoa.domain.PessoaEnderecoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaEnderecoRepository extends JpaRepository<PessoaEndereco, PessoaEnderecoId> {

	@Query("	SELECT e FROM PessoaEndereco pe "
		 + "	INNER JOIN pe.endereco e "
		 + "	INNER JOIN pe.pessoa p "
		 + "	WHERE p.id = :idPessoa ")
	List<Endereco> listarEnderecosPorIdPessoa(Long idPessoa);

}
