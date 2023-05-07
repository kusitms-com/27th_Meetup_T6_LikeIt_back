package com.kusitms.hotsixServer.domain.place.entity;

import com.kusitms.hotsixServer.domain.user.entity.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name = "places")
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "content")
    private String content;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "place_img", columnDefinition = "TEXT", nullable = false)
    private String placeImg;

    @Column(name = "star_rating")
    private float starRating;

    @Column(name = "region")
    private float region;

    @Column(name = "bookmark_count")
    @ColumnDefault("0")
    private int bookmarkCount;

}
