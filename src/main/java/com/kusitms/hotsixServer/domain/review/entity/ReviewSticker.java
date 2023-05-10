package com.kusitms.hotsixServer.domain.review.entity;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Entity
@Table(name = "review_sticker")
public class ReviewSticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_sticker_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="sticker_id")
    private Sticker sticker;

    @Column(name = "is_positive", nullable = false)
    private char isPositive;

    public ReviewSticker(Review review, Sticker sticker, char isPositive) {
        this.review = review;
        this.sticker = sticker;
        this.isPositive = isPositive;
    }
}

