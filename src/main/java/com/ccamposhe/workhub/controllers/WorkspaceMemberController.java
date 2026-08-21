package com.ccamposhe.workhub.controllers;

import com.ccamposhe.workhub.domain.WorkspaceMember;
import com.ccamposhe.workhub.dtos.JoinWorkspaceRequestDTO;
import com.ccamposhe.workhub.services.WorkspaceMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class WorkspaceMemberController {

    private final WorkspaceMemberService service;

    @PostMapping("/join")
    public ResponseEntity<WorkspaceMember> join(@RequestBody @Valid JoinWorkspaceRequestDTO dto){

        WorkspaceMember newMember = service.joinWorkspace(dto.userId(), dto.inviteCode());

        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }

    @PutMapping("/{memberId}/approve")
    public ResponseEntity<WorkspaceMember> approve(@PathVariable UUID memberId, @RequestHeader("user-id") UUID adminId){
        WorkspaceMember approvedMember = service.approveMember(adminId, memberId);

        return ResponseEntity.ok(approvedMember);
    }
}
