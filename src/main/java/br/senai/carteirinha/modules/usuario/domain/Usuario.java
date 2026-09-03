package br.senai.carteirinha.modules.usuario.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * Entidade de domínio do módulo de usuário.
 *
 * Não conhece Spring, JPA, HTTP, JWT ou BCrypt. As credenciais e a resposta
 * do login são DTOs do caso de uso, não novas entidades do domínio.
 */
public final class Usuario {
    private final UUID id;
    private final String login;
    private final String senhaHash;
    private final String nome;
    private final String curso;
    private final String turma;
    private final boolean ativo;

    public Usuario(UUID id, String login, String senhaHash, String nome,
                   String curso, String turma, boolean ativo) {
        this.id = Objects.requireNonNull(id);
        this.login = exigirTexto(login, "login");
        this.senhaHash = exigirTexto(senhaHash, "senhaHash");
        this.nome = exigirTexto(nome, "nome");
        this.curso = exigirTexto(curso, "curso");
        this.turma = exigirTexto(turma, "turma");
        this.ativo = ativo;
    }

    public UUID id() { return id; }
    public String login() { return login; }
    public String senhaHash() { return senhaHash; }
    public String nome() { return nome; }
    public String curso() { return curso; }
    public String turma() { return turma; }
    public boolean estaAtivo() { return ativo; }

    private static String exigirTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " não pode estar vazio");
        }
        return valor;
    }
}
