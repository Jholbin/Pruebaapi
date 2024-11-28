package com.cibertec.Back_grupo1.Service;

import com.cibertec.Back_grupo1.Model.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> buscarPorUsername(String username);
    Optional<Usuario> buscarPorDni(String dni);
    Optional<Usuario> buscarPorCorreo(String correo);
    boolean validateUserCredentials(String username, String password);
    void registrarUsuario(Usuario usuario);  // Método
}
