package br.com.imsodontologia.pontoeletronico.repository;

import br.com.imsodontologia.pontoeletronico.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, UUID> {

    @Query(value = "SELECT * FROM pontoeletronico.tb_lancamento WHERE cd_colaborador = :cdColaborador ", nativeQuery = true)
    List<Lancamento> findAllByCdColaborador(@Param("cdColaborador") UUID cdColaborador);
}
