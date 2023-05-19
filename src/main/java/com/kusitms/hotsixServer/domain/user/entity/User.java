package com.kusitms.hotsixServer.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kusitms.hotsixServer.domain.user.dto.GoogleUser;
import com.kusitms.hotsixServer.global.common.BaseTimeEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "user_email", unique = true, nullable = false)
    private String userEmail;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "user_img", columnDefinition = "TEXT", nullable = false)
    private String userImg;

    @JsonIgnore
    @Column(name="password")
    private String password;

    @Builder
    public User(GoogleUser googleUser, PasswordEncoder passwordEncoder){
        this.userName = googleUser.getName();
        this.userEmail = googleUser.getEmail();
        this.userImg = googleUser.getPicture();
        this.password = passwordEncoder.encode("google");
    }

    public void updateNickName(String nickname){
        this.nickname = nickname;
    }

    public void updatePhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }

    public void updateBirth(String birthDate){
        this.birthDate = birthDate;
    }

    public void updateImg(String img){
        this.userImg = img;
    }

}
