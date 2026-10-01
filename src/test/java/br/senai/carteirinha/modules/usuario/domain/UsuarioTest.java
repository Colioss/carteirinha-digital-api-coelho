package br.senai.carteirinha.modules.usuario.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UsuarioTest {

    private static final UUID ID =
        UUID.fromString(
            "00000000-0000-0000-0000-000000000001"
        );

    @Test
    void alunoDevePossuirCursoETurma() {

        assertThrows(
            IllegalArgumentException.class,
            () ->
                new Usuario(
                    ID,
                    "aluno",
                    "hash",
                    "Aluno",
                    "2026000001",
                    PerfilAcesso.ALUNO,
                    "",
                    "",
                    true
                )
        );
    }

    @Test
    void professorPodeNaoPossuirCursoETurmaNaSessao() {

        Usuario professor =
            assertDoesNotThrow(
                () ->
                    new Usuario(
                        ID,
                        "professor",
                        "hash",
                        "Professor",
                        "PROF20260001",
                        PerfilAcesso.PROFESSOR,
                        "",
                        "",
                        true
                    )
            );

        assertEquals(
            PerfilAcesso.PROFESSOR,
            professor.perfil()
        );

        assertEquals(
            "",
            professor.curso()
        );

        assertEquals(
            "",
            professor.turma()
        );
    }
}