package br.senai.carteirinha.modules.usuario.infrastructure.persistence;

import br.senai.carteirinha.modules.usuario.application.port.out.UsuarioRepository;
import br.senai.carteirinha.modules.usuario.domain.PerfilAcesso;
import br.senai.carteirinha.modules.usuario.domain.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioPersistenceAdapter
    implements UsuarioRepository {

    private final SpringDataUsuarioRepository repository;

    public UsuarioPersistenceAdapter(
        SpringDataUsuarioRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public Optional<Usuario> buscarPorLogin(
        String login
    ) {

        return repository
            .findByLoginIgnoreCase(login)
            .map(this::toDomain);
    }

    private Usuario toDomain(
        UsuarioJpaEntity entity
    ) {

        PerfilAcesso perfil =
            entity.getPerfil() == null
                ? PerfilAcesso.ALUNO
                : entity.getPerfil();

        return new Usuario(
            entity.getId(),
            entity.getLogin(),
            entity.getSenhaHash(),
            entity.getNome(),
            entity.getMatricula(),
            perfil,
            entity.getCurso(),
            entity.getTurma(),
            entity.isAtivo()
        );
    }
}