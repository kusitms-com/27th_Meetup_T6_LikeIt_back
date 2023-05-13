package com.kusitms.hotsixServer.domain.place.controller;

import com.kusitms.hotsixServer.domain.place.service.BookmarkService;
import com.kusitms.hotsixServer.global.dto.ResponseDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/place")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @ApiOperation("장소 북마크 설정 코드")
    @GetMapping("/bookmark/{placeId}")
        public ResponseEntity<ResponseDto> setBookmark(@PathVariable("placeId") Long placeId) {
        String message = bookmarkService.setBookmark(placeId);
        return ResponseEntity.ok(ResponseDto.create(message));
    }
}
