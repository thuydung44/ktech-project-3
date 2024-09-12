package com.example.ktech_project_3;

import com.example.ktech_project_3.dto.UserDto;
import com.example.ktech_project_3.entity.UserEntity;
import com.example.ktech_project_3.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository repository) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        UserEntity user1 = new UserEntity();
        user1.setUsername("user1");
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setAuthorities("ROLE_DEFAULT");
        this.repository.save(user1);
        UserEntity admin = new UserEntity();
        admin.setUsername("thuyDung");
        admin.setPassword(passwordEncoder.encode("passwordAdmin"));
        admin.setAuthorities("ROLE_ADMIN");
        this.repository.save(admin);
    }

    public void createUser(UserDto dto) {
        if (userExists(dto.getUsername()) || !dto.getPassword().equals(dto.getPasswordCheck() ))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        UserEntity newUser = new UserEntity();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setAuthorities("ROLE_DEFAULT");
        UserDto.fromEntity(repository.save(newUser));
    }

    public UserDto updateUser(Long id, UserDto dto) {
        Optional<UserEntity> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) {throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
        UserEntity target = optionalUser.get();
        target.setNickname(dto.getNickname());
        target.setName(dto.getName());
        target.setAge(dto.getAge());
        target.setEmail(dto.getEmail());
        target.setPhone(dto.getPhone());
        if (dto.getNickname() == null ||
        dto.getName() == null ||
        dto.getAge() == null ||
        dto.getEmail() == null ||
        dto.getPhone() == null) {
             target.setAuthorities("ROLE_DEFAULT");
        } else {
        target.setAuthorities("ROLE_USER");}
        return UserDto.fromEntity(repository.save(target));


    } public UserDto updateProfileImg(Long id, MultipartFile image) {
        Optional<UserEntity> optionalUser = repository.findById(id);
        if (optionalUser.isEmpty()) { throw new ResponseStatusException(HttpStatus.NOT_FOUND); }
        String profileDir = "media/" + id + "/";
        try {
            Files.createDirectories(Path.of(profileDir));
        } catch (IOException e) { throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);}

        String originalFilename = image.getOriginalFilename();
        String[] filenameSplit = originalFilename.split("\\.");
        String extension = filenameSplit[filenameSplit.length - 1];
        String uploadPath = profileDir + "profile" + extension;
        try {
            image.transferTo(Path.of(uploadPath));
        } catch (IOException e) { throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);}
        String reqPath = "static" + id + "/profile" + extension;
        UserEntity target = optionalUser.get();
        target.setProfileImgUrl(reqPath);
        target.setAuthorities("ROLE_DEFAULT");
        return UserDto.fromEntity(repository.save(target));
    }
// 신청
    public UserEntity applyForBusiness(String name) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<UserEntity> userOpt = repository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getBusinessStatus() == UserEntity.BusinessStatus.NONE) {
            UserEntity user = userOpt.get();
            user.setBusinessStatus(UserEntity.BusinessStatus.PENDING);
            return repository.save(user);
        }
        return null;


    }
    // 관리자: 전환 목록 조회
    public List<UserEntity> getBusinessApplyList() {
        return repository.findByBusinessStatus(UserEntity.BusinessStatus.PENDING);
    }

    // 관리자: 사업자 신청수락
    public UserEntity acceptBusinessApply(Long userId) {
        Optional<UserEntity> userOpt = repository.findById(userId);
        if (userOpt.isPresent() && userOpt.get().getBusinessStatus() == UserEntity.BusinessStatus.PENDING) {
            UserEntity user = userOpt.get();
            user.setBusinessStatus(UserEntity.BusinessStatus.APPROVED);
            user.setAuthorities("ROLE_BUSINESS");
            return repository.save(user);
        }
        return null;
    }

    // 관리자: 사업자 신청 거절
    public UserEntity rejectBusinessApply(Long userId) {
        Optional<UserEntity> userOpt = repository.findById(userId);
        if (userOpt.isPresent() && userOpt.get().getBusinessStatus() == UserEntity.BusinessStatus.PENDING) {
            UserEntity user = userOpt.get();
            user.setBusinessStatus(UserEntity.BusinessStatus.REJECTED);
            return repository.save(user);
        }
        return null;
    }



    public UserDto readByUsername(String username) {
        Optional<UserEntity> optionalUser = repository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException(username);
        return UserDto.fromEntity(optionalUser.get());
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = repository.findByUsername(username);
        if (optionalUser.isEmpty()) throw new UsernameNotFoundException(username);
//        String authorities = optionalUser.get().getAuthorities(); //USER;
        //String[] "a,b,c"=> List<Authority> authorities = Arrays.asList(authorities.split(",")).stream()
        List<String> authorities = new ArrayList<>();
        authorities.add(optionalUser.get().getAuthorities());

        UserDetails userDetails = (UserDetails) new User(optionalUser.get().getUsername(),
                optionalUser.get().getPassword(),
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        return userDetails;

    }

    public boolean userExists(String username) {
        return repository.existsByUsername(username);
    }
}
