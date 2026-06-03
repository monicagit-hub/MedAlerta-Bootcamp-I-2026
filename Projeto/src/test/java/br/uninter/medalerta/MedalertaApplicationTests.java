package br.uninter.medalerta;

import br.uninter.medalerta.security.JwtUtil;
import br.uninter.medalerta.security.UsuarioDetalhesService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MedalertaApplicationTests {

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UsuarioDetalhesService usuarioDetalhesService;

    @Test
    void contextLoads() {
    }

}
