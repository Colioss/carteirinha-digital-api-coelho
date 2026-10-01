package br.senai.carteirinha.modules.usuario.presentation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JwtDecoder jwtDecoder;

    @Test
    void loginAlunoDeveRetornarPerfilAlunoNoJsonENoJwt()
        throws Exception {

        JsonNode response =
            login(
                "aluno",
                "123"
            );

        assertEquals(
            "ALUNO",
            response
                .get("perfil")
                .asText()
        );

        Jwt jwt =
            jwtDecoder.decode(
                response
                    .get("token")
                    .asText()
            );

        assertEquals(
            "ALUNO",
            jwt.getClaimAsString(
                "perfil"
            )
        );
    }

    @Test
    void loginProfessorDeveRetornarPerfilProfessor()
        throws Exception {

        String response =
            mockMvc
                .perform(
                    post("/auth/login")
                        .contentType(
                            MediaType.APPLICATION_JSON
                        )
                        .content(
                            """
                            {
                              "login": "professor",
                              "senha": "123"
                            }
                            """
                        )
                )
                .andExpect(
                    status().isOk()
                )
                .andExpect(
                    jsonPath("$.perfil")
                        .value("PROFESSOR")
                )
                .andExpect(
                    jsonPath("$.curso")
                        .value("")
                )
                .andExpect(
                    jsonPath("$.turma")
                        .value("")
                )
                .andReturn()
                .getResponse()
                .getContentAsString(
                    StandardCharsets.UTF_8
                );

        JsonNode json =
            objectMapper.readTree(
                response
            );

        Jwt jwt =
            jwtDecoder.decode(
                json
                    .get("token")
                    .asText()
            );

        assertEquals(
            "PROFESSOR",
            jwt.getClaimAsString(
                "perfil"
            )
        );
    }

    private JsonNode login(
        String login,
        String senha
    ) throws Exception {

        String response =
            mockMvc
                .perform(
                    post("/auth/login")
                        .contentType(
                            MediaType.APPLICATION_JSON
                        )
                        .content(
                            """
                            {
                              "login": "%s",
                              "senha": "%s"
                            }
                            """.formatted(
                                login,
                                senha
                            )
                        )
                )
                .andExpect(
                    status().isOk()
                )
                .andReturn()
                .getResponse()
                .getContentAsString(
                    StandardCharsets.UTF_8
                );

        return objectMapper.readTree(
            response
        );
    }
}