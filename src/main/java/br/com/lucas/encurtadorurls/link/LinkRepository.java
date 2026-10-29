package br.com.lucas.encurtadorurls.link;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByCodigoCurto(String codigoCurto);



}
