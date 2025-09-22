package com.bookstore.service;

import com.bookstore.dto.request.LoginRequestDTO;
import com.bookstore.dto.request.UserRegisterRequestDTO;
import com.bookstore.dto.response.LoginResponseDTO;
import com.bookstore.dto.response.UserRegisterResponseDTO;
import com.bookstore.exception.BusinessRuleException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Author;
import com.bookstore.model.Reader;
import com.bookstore.model.User;
import com.bookstore.model.enums.RoleType;
import com.bookstore.repository.AuthorRepository;
import com.bookstore.repository.ReaderRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorRepository authorRepository;
    private final ReaderRepository readerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserRegisterResponseDTO register(UserRegisterRequestDTO dto) {
        // Regla: no permitir emails duplicados
        if (userRepository.existsByEmail(dto.email())) {
            throw new BusinessRuleException("El email ya está registrado");
        }

        // Buscar rol por enum
        var role = roleRepository.findByName(dto.roleType())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));

        // Crear usuario base
        var user = User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .role(role)
                .build();

        var savedUser = userRepository.save(user);

        Long profileId = null;
        String profileType = null;

        // Crear perfil según el rol
        if (role.getName() == RoleType.AUTHOR) {
            var author = Author.builder()
                    .name(dto.name())
                    .bio(dto.bio())
                    .website(dto.website())
                    .user(savedUser)
                    .build();
            var savedAuthor = authorRepository.save(author);
            profileId = savedAuthor.getId();
            profileType = RoleType.AUTHOR.name();

        } else if (role.getName() == RoleType.READER) {
            var reader = Reader.builder()
                    .name(dto.name())
                    .preferences(dto.preferences())
                    .user(savedUser)
                    .build();
            var savedReader = readerRepository.save(reader);
            profileId = savedReader.getId();
            profileType = RoleType.READER.name();

        } else if (role.getName() == RoleType.ADMIN) {
            // Un admin no requiere perfil adicional
            profileType = RoleType.ADMIN.name();
        }

        // Respuesta
        return UserRegisterResponseDTO.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .roleName(role.getName().name()) // ✅ correcto
                .profileId(profileId)
                .profileType(profileType)
                .name(dto.name())
                .build();

    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO dto) {
        var user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Validar credenciales
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new BusinessRuleException("Credenciales inválidas");
        }

        // TODO: aquí generar JWT real; por ahora token simulado
        String fakeToken = "demo-token";

        return LoginResponseDTO.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .token(fakeToken)
                .build();

    }
}
