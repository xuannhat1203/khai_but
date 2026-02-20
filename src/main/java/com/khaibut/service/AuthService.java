package com.khaibut.service;

import com.khaibut.dto.LoginRequest;
import com.khaibut.dto.RegisterRequest;
import com.khaibut.entity.User;
import com.khaibut.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    /**
     * Đăng ký người dùng mới
     */
    public String register(RegisterRequest registerRequest) {
        // Kiểm tra username đã tồn tại chưa
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username đã tồn tại");
        }

        // Kiểm tra email đã tồn tại chưa
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // Tạo user mới
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRole(registerRequest.getRole() != null ? registerRequest.getRole() : "USER");
        user.setEnabled(true);

        userRepository.save(user);

        return "Đăng ký thành công";
    }

    /**
     * Đăng nhập
     */
    public String login(LoginRequest loginRequest) {
        // Tìm user theo username
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Username hoặc password không chính xác"));

        // Kiểm tra password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Username hoặc password không chính xác");
        }

        // Kiểm tra user có được enable không
        if (!user.getEnabled()) {
            throw new RuntimeException("Tài khoản này đã bị vô hiệu hóa");
        }

        // Tạo JWT token
        return jwtService.generateToken(user.getUsername());
    }
}

