package com.dwbh.backend.service;

import com.dwbh.backend.dto.UserDetailResponse;
import com.dwbh.backend.dto.user.ModifyUserPasswordRequest;
import com.dwbh.backend.dto.user.ModifyUserRequest;
import com.dwbh.backend.dto.user.UserModifyResponse;
import com.dwbh.backend.entity.User;
import com.dwbh.backend.exception.CustomException;
import com.dwbh.backend.exception.ErrorCodeType;
import com.dwbh.backend.repository.user.UserRepository;
import com.dwbh.backend.dto.CreateUserRequest;
import com.dwbh.backend.repository.user.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomUserRepository customUserRepository;
    private final UserRepository userRepository;

    // 회원 등록
    @Transactional
    public void createUser(CreateUserRequest newUser, String tokenEmail) {
        if (emailCheck(newUser.getUserEmail()) || nicknameCheck(newUser.getUserNickname())) {
            throw new CustomException(ErrorCodeType.USER_EXIST_VALUES);
        } else if (!tokenEmail.equals(newUser.getUserEmail())) {
            throw new CustomException(ErrorCodeType.USER_EMAIL_TOKEN_ERROR);
        }

        User user = modelMapper.map(newUser, User.class);
        user.encryptPassword(passwordEncoder.encode(newUser.getUserPassword()));
        userRepository.save(user);
    }

    // user_seq 등록 여부 확인
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User loginUser = userRepository.findByUserEmail(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        if ( loginUser.getUserStatus().equals("delete") ) {
            throw new CustomException(ErrorCodeType.USER_NOT_FOUND);
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(loginUser.getUserEmail(), loginUser.getUserPassword(), grantedAuthorities);
    }

    // 이메일 중복 확인
    public Boolean emailCheck(String userEmail) {
        return userRepository.existsByUserEmail(userEmail);
    }

    // 닉네임 중복 확인
    public Boolean nicknameCheck(String userNickname) {
        return userRepository.existsByUserNickname(userNickname);
    }

    // user_email 로 user_seq 검색
    public Long getUserSeq(String Email) {
        User user = userRepository.findByUserEmail(Email).orElseThrow();
        return user.getUserSeq();
    }

    // 회원 상세 조회
    public UserDetailResponse getUserDetail(Long userSeq) {

        return customUserRepository.findUserDetailResponse(userSeq);
    }

    // 회원 정보 수정 조회
    public UserModifyResponse getUserModify(Long userSeq) {

        return modelMapper.map(userRepository.findByUserSeq(userSeq), UserModifyResponse.class);
    }

    // 회원 정보 수정
    public void modifyUser(Long userSeq, MultipartFile userProfile, ModifyUserRequest modifyUserRequest) {

        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        // 패스워드 변경 유무
        if ( modifyUserRequest.getUserPassword() != null ) {
            modifyUserRequest.setUserPassword(passwordEncoder.encode(modifyUserRequest.getUserPassword()));
            user.modifyUser(modifyUserRequest, true);
        } else { user.modifyUser(modifyUserRequest, false); }

        userRepository.save(user);
    }

    // 회원 탈퇴
    public void deleteUser(Long userSeq) {
        userRepository.deleteById(userSeq);
    }

    // 회원 비밀번호 변경
    @Transactional
    public void updateUserPassword(ModifyUserPasswordRequest modifyUserPasswordRequest) {
        Long userSeq = getUserSeq(modifyUserPasswordRequest.getUserEmail());

        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.USER_NOT_FOUND));

        modifyUserPasswordRequest.setUserPassword(passwordEncoder.encode(modifyUserPasswordRequest.getUserPassword()));
        user.modifyUserPassword(modifyUserPasswordRequest.getUserPassword());

        userRepository.save(user);
    }
}