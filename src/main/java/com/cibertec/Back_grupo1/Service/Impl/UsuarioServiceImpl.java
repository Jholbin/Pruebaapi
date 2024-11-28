package com.cibertec.Back_grupo1.Service.Impl;

import com.cibertec.Back_grupo1.Model.Usuario;
import com.cibertec.Back_grupo1.Repo.UsuarioRepository;
import com.cibertec.Back_grupo1.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }
    
    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
    
    @Override
    public boolean validateUserCredentials(String username, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            return usuario.getPassword().equals(password);
        }
        return false;
    }

	@Override
	public void registrarUsuario(Usuario usuario) {
		 usuarioRepository.save(usuario);
		
	}
}
