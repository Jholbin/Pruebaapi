package com.cibertec.Back_grupo1.Controller;

import com.cibertec.Back_grupo1.Model.Usuario;
import com.cibertec.Back_grupo1.Service.UsuarioService;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/buscarUsuario/{username}")
    public ResponseEntity<?> buscarUsuarioPorUsername(@PathVariable String username) {
        Usuario usuario = usuarioService.buscarPorUsername(username).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
        usuario.setRol(null); // Evitar la recursión infinita en la respuesta JSON
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @GetMapping("/buscarDni/{dni}")
    public ResponseEntity<?> buscarUsuarioPorDni(@PathVariable String dni) {
        Usuario usuario = usuarioService.buscarPorDni(dni).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>("DNI no encontrado", HttpStatus.NOT_FOUND);
        }
        usuario.setRol(null); // Evitar la recursión infinita en la respuesta JSON
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    
    @GetMapping("/buscarCorreo/{correo}")
    public ResponseEntity<?> buscarUsuarioPorCorreo(@PathVariable String correo) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>("Correo no encontrado", HttpStatus.NOT_FOUND);
        }
        usuario.setRol(null); // Evitar la recursión infinita en la respuesta JSON
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean isValidUser = usuarioService.validateUserCredentials(username, password);
        if (isValidUser) {
            return new ResponseEntity<>("Login exitoso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }
    }
    
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody Usuario usuario) {
    	// Verificar si falta algún campo obligatorio
        if (usuario.getUsername() == null || usuario.getCorreo() == null || usuario.getDni() == null ||
                usuario.getNombre() == null || usuario.getApellido() == null || usuario.getPassword() == null) {
            return new ResponseEntity<>("Faltan campos obligatorios", HttpStatus.BAD_REQUEST);
        }

        // Establecer la fecha de registro si no se proporcionó
        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }

        // Verificar si el nombre de usuario ya existe
        Optional<Usuario> existingUser = usuarioService.buscarPorUsername(usuario.getUsername());
        if (existingUser.isPresent()) {
            return new ResponseEntity<>("El nombre de usuario ya está en uso", HttpStatus.BAD_REQUEST);
        }
        
        // Verificar si el DNI de usuario ya existe
        Optional<Usuario> existingDNI = usuarioService.buscarPorDni(usuario.getDni());
        if (existingDNI.isPresent()) {
        	return new ResponseEntity<>("El DNI del usuario ya esta en uso", HttpStatus.BAD_REQUEST);
        }
        
        // Verificar si el Corro de usuario ya existe
        Optional<Usuario> existingEmail = usuarioService.buscarPorCorreo(usuario.getCorreo());
        if (existingEmail.isPresent()) {
        	return new ResponseEntity<>("El Correo del usuario ya esta en uso", HttpStatus.BAD_REQUEST);
        }
        


        // Registrar usuario
        usuarioService.registrarUsuario(usuario);
        return new ResponseEntity<>("Usuario registrado con éxito", HttpStatus.CREATED);

         }
    }



