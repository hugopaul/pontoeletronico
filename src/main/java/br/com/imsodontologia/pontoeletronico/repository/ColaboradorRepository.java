package br.com.imsodontologia.pontoeletronico.repository;

import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, UUID> {

    @Query(value = "select * from pontoeletronico.tb_colaborador where username like :username", nativeQuery = true)
    Optional<Colaborador> findByUsername(@Param("username") String username);
}
