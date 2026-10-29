package br.com.lucas.encurtadorurls;

import br.com.lucas.encurtadorurls.link.Link;
import br.com.lucas.encurtadorurls.link.LinkRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.stereotype.Service;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class LinkRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgrs = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void deveGerarIdEValoresIniciaisAoSalvar() {
        Link link = new Link();
        link.setCodigoCurto("abc123");
        link.setUrlOriginal("https://exemplo.com/pagina-muito-longa");

        Link salvo = linkRepository.save(link);
        assertThat(salvo.getId()).isNotNull();

        // força o INSERT e descarta o cache, para a leitura abaixo vir do banco de verdade
        entityManager.flush();
        entityManager.clear();

        Link doBanco = linkRepository.findById(salvo.getId()).orElseThrow();
        assertThat(doBanco.getCriadoEm()).isNotNull();
        assertThat(doBanco.getCliques()).isEqualTo(0L);
        assertThat(doBanco.getAtivo()).isTrue();
        assertThat(doBanco.getExpiraEm()).isNull();
    }

    @Test
    void deveEncontrarLinkPorCodigoCurtoExistente() {
        Link link = new Link();
        link.setCodigoCurto("xyz789");
        link.setUrlOriginal("https://exemplo.com/outra-pagina");
        linkRepository.save(link);

        Optional<Link> resultado = linkRepository.findByCodigoCurto("xyz789");

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getUrlOriginal()).isEqualTo("https://exemplo.com/outra-pagina");
    }

    @Test
    void deveDevolverVazioParaCodigoCurtoInexistente() {
        Optional<Link> resultado = linkRepository.findByCodigoCurto("nao-existe");

        assertThat(resultado).isEmpty();
    }


}
