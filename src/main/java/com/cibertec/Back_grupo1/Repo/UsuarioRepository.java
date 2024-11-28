package com.cibertec.Back_grupo1.Repo;

import com.cibertec.Back_grupo1.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByDni(String dni);
    Optional<Usuario> findByCorreo(String correo);
}