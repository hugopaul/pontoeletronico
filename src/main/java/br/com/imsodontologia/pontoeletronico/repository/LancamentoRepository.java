package br.com.imsodontologia.pontoeletronico.repository;

import br.com.imsodontologia.pontoeletronico.model.Lancamento;
import br.com.imsodontologia.pontoeletronico.model.MeusLancamentosConcatenados;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, UUID> {

    @Query(value = "SELECT * FROM pontoeletronico.tb_lancamento WHERE cd_colaborador = :cdColaborador and dh_marcacao between (NOW() - interval '24 hours') and now() ", nativeQuery = true)
    List<Lancamento> findAllByCdColaborador(@Param("cdColaborador") UUID cdColaborador);

    @Query(value = "select dia, lancamentos from (select to_char(a.dh_marcacao, 'dd/MM/yyyy')  as dia , string_agg(to_char(a.dh_marcacao, 'HH24:mm:SS'), ' - ') as lancamentos " +
            "from (select dh_marcacao, cd_colaborador from pontoeletronico.tb_lancamento order by dh_marcacao asc) as a\n" +
            "where cd_colaborador = :cdColaborador\n" +
            "group by dia) as b",nativeQuery = true)
    List<Object> getAllLancamentosConcatenadosByColaborador(@Param("cdColaborador") UUID cdColaborador);

    @Query(value = "select nm_colaborador, dia, lancamentos \n" +
            "from (select nm_colaborador, to_char(a.dh_marcacao, 'dd/MM/yyyy')  as dia , \n" +
            "  string_agg(to_char(a.dh_marcacao, 'HH24:mm:SS'), ' - ') as lancamentos\n" +
            "         from (select a.dh_marcacao, a.cd_colaborador,  b.nm_colaborador\n" +
            "  from pontoeletronico.tb_lancamento as a\n" +
            "   inner join pontoeletronico.tb_colaborador as b on a.cd_colaborador =  b.cd_colaborador\n" +
            "  order by dh_marcacao asc) as a\n" +
            "            group by dia, nm_colaborador) as b",nativeQuery = true)
    List<Object> getAllLancamentosConcatenados();
}
