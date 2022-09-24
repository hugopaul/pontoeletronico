package br.com.imsodontologia.pontoeletronico;

import br.com.imsodontologia.pontoeletronico.model.Colaborador;
import br.com.imsodontologia.pontoeletronico.model.Perfil;
import br.com.imsodontologia.pontoeletronico.repository.ColaboradorRepository;
import br.com.imsodontologia.pontoeletronico.repository.PerfilRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;
import java.util.UUID;


@Slf4j
@OpenAPIDefinition
@SpringBootApplication
public class PontoeletronicoApplication {

	@Autowired
	private ColaboradorRepository colaboradorRepository;

	@Autowired
	private PerfilRepository perfilRepository;

	public static void main(String[] args) {
		System.setProperty("server.servlet.context-path", "/pontoeletronico/api");
		SpringApplication.run(PontoeletronicoApplication.class, args);
	}

	private BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	};

	@Bean
	InitializingBean sendDatabase() {
		return () -> {
			perfilRepository.save(new Perfil(UUID.fromString("c30bdcaa-e445-11ec-8fea-0242ac000001"), "USER"));
			perfilRepository.save(new Perfil(UUID.fromString("c30bdcaa-e445-11ec-8fea-0242ac000002"), "GERENTE"));
			perfilRepository.save(new Perfil(UUID.fromString("c30bdcaa-e445-11ec-8fea-0242ac000015"), "PROPRIETARIO"));
			colaboradorRepository.save(new Colaborador(UUID.fromString("c30bdcaa-e445-11ec-8fea-0242ac000014"), "admin"
												, "admin", bCryptPasswordEncoder().encode("admin"), "61982615008",
												Set.of( new Perfil(UUID.fromString("c30bdcaa-e445-11ec-8fea-0242ac000002"), "GERENTE"),
														new Perfil(UUID.fromString("c30bdcaa-e445-11ec-8fea-0242ac000001"), "USER"),
														new Perfil(UUID.fromString("c30bdcaa-e445-11ec-8fea-0242ac000015"), "PROPRIETARIO")), false));
		};
	}

}
