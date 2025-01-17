package repository;

import entity.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
// Método para encontrar um usuário por email
Optional<Usuario> findByEmail(String email);
   
}