package br.com.imsodontologia.pontoeletronico.repository;

import br.com.imsodontologia.pontoeletronico.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PerfilRepository extends JpaRepository<Perfil, UUID> {
}
