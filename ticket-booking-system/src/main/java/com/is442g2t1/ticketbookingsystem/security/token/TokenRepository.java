package com.is442g2t1.ticketbookingsystem.security.token;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer>{

    // Token and UserEntity should refer to the entity classes
    @Query(value = """
      select t from Token t inner join UserEntity u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUserId(Integer id);

    Optional<Token> findByToken(String token);
    
}
