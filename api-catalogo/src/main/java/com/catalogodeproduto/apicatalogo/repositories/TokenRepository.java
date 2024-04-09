package com.catalogodeproduto.apicatalogo.repositories;

import com.catalogodeproduto.apicatalogo.entities.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Integer> {

      @Query(value = """
      select t from TokenEntity t join fetch UsuarioEntity u\s
      on t.user.id = u.id\s
      where u.id = :userid and (t.expired = false or t.revoked = false)\s
      """)
      List<TokenEntity> findAllValidTokenByUser(Integer userid);

      Optional<TokenEntity> findByToken(String token);
}
