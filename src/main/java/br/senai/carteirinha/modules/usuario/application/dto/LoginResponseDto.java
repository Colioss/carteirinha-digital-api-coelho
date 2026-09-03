package br.senai.carteirinha.modules.usuario.application.dto;

/**
 * Dados devolvidos ao aplicativo depois da autenticação.
 * Não possui refresh token nesta etapa do curso.
 */
public record LoginResponseDto(
    String id,
    String nome,
    String curso,
    String turma,
    String token
) {}
