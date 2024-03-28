package com.catalogodeproduto.apicatalogo.repositories;

import com.catalogodeproduto.apicatalogo.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findByEmail(String email);

}
