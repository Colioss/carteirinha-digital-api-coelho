package br.senai.carteirinha.modules.usuario.infrastructure.persistence;

import br.senai.carteirinha.modules.usuario.domain.PerfilAcesso;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Configuration
public class UsuarioDataInitializer {

    @Bean
    CommandLineRunner carregarUsuariosDeTeste(
        SpringDataUsuarioRepository repository,
        PasswordEncoder passwordEncoder
    ) {

        return args -> {

            garantirUsuario(
                repository,
                passwordEncoder,
                UUID.fromString(
                    "00000000-0000-0000-0000-000000000001"
                ),
                "aluno",
                "123",
                "Rafael Costa",
                "2026000001",
                PerfilAcesso.ALUNO,
                "Desenvolvimento de Sistemas",
                "2DEVEST-A"
            );

            garantirUsuario(
                repository,
                passwordEncoder,
                UUID.fromString(
                    "00000000-0000-0000-0000-000000000002"
                ),
                "maria",
                "456",
                "Maria Oliveira",
                "2026000002",
                PerfilAcesso.ALUNO,
                "Desenvolvimento de Sistemas",
                "2DEVEST-B"
            );

            garantirUsuario(
                repository,
                passwordEncoder,
                UUID.fromString(
                    "00000000-0000-0000-0000-000000000003"
                ),
                "professor",
                "123",
                "Professor Modelo",
                "PROF20260001",
                PerfilAcesso.PROFESSOR,
                "",
                ""
            );
        };
    }

    private void garantirUsuario(
        SpringDataUsuarioRepository repository,
        PasswordEncoder passwordEncoder,
        UUID id,
        String login,
        String senha,
        String nome,
        String matricula,
        PerfilAcesso perfil,
        String curso,
        String turma
    ) {

        var existente =
            repository
                .findByLoginIgnoreCase(
                    login
                );

        if (existente.isPresent()) {

            UsuarioJpaEntity usuario =
                existente.get();

            if (
                usuario.getPerfil() == null
            ) {
                usuario.definirPerfil(
                    perfil
                );

                repository.save(
                    usuario
                );
            }

            return;
        }

        repository.save(
            new UsuarioJpaEntity(
                id,
                login,
                passwordEncoder.encode(
                    senha
                ),
                nome,
                matricula,
                perfil,
                curso,
                turma,
                true
            )
        );
    }
}