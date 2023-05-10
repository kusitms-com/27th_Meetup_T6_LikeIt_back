package com.kusitms.hotsixServer.domain.review.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Entity
@Table(name = "stickers")
public class Sticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sticker_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    //스티커 긍/부정 변수 ->  Y/N
    @Column(name = "is_positive", nullable = false)
    private char isPositive;
}
