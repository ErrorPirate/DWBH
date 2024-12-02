package com.dwbh.backend.controller.counselor_hire;

import com.dwbh.backend.dto.counselor_hire.*;
import com.dwbh.backend.service.counselor_hire.CounselorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/counselor-hire")
@Tag(name = "게시판", description = "게시판 API")
@Slf4j
public class CounselorController {

    private final CounselorService counselorService;

    @Operation(summary = "전체 게시글 조회", description = "전체 게시글 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<CounselorListResponse> readCounselorList(@ModelAttribute ReadCounselorListRequest request, Pageable pageable) {
        CounselorListResponse response = counselorService.readCounselorList(request, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 상세조회", description = "ID로 특정 게시글을 조회합니다.")
    @GetMapping("/{hireSeq}")
    public ResponseEntity<CounselorDetailResponse> readPostDetail(@PathVariable Long hireSeq) {

        CounselorDetailResponse detail = counselorService.readPostDetail(hireSeq);
        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }

    @Operation(summary = "해당 유저가 작성한 게시글 조회", description = "전체 게시글 목록을 조회합니다.")
    @GetMapping("/user/{userSeq}")
    public ResponseEntity<CounselorUserListResponse> readUserCounselorList(@PathVariable Long userSeq, Pageable pageable) {
        CounselorUserListResponse response = counselorService.readUserCounselorList(userSeq, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 등록", description = "게시글을 등록합니다.")
    @PostMapping
    public ResponseEntity<Void> createCounselor(@RequestBody CreateCounselorRequest request) {
        log.info("request : {}", request);
        counselorService.createPost(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 등록 페이지 진입 시 데이터 조회", description = "게시글 등록 페이지 진입 시 데이터 조회합니다.")
    @GetMapping("/create")
    public ResponseEntity<CreateCounselorViewResponse> createCounselorRequest() {
        return ResponseEntity.ok(counselorService.createCounselorRequest());
    }

    @Operation(summary = "게시글 수정", description = "ID로 특정 게시글을 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCounselor(@PathVariable Long id, @RequestBody CounselorUpdateRequest updatePostReqDTO) {
        counselorService.updatePost(id, updatePostReqDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 삭제", description = "ID로 특정 게시글을 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCounselor(@PathVariable Long id) {
        counselorService.deletePost(id);
        return ResponseEntity.ok().build();
    }



}
