package br.com.lucas.encurtadorurls.link;

import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_curto", nullable = false, unique = true, length = 20)
    private String codigoCurto;

    @Column(name = "url_original", nullable = false, length = 2048)
    private String urlOriginal;

    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm = Instant.now();

    @Column(name = "expira_em")
    private Instant expiraEm;

    @Column(name= "cliques", nullable = false)
    private Long cliques = 0L;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

}
