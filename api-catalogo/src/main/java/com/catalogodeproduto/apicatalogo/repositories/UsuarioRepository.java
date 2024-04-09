package com.catalogodeproduto.apicatalogo.repositories;

import com.catalogodeproduto.apicatalogo.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    Optional<UsuarioEntity> findByEmail(String email);

}
