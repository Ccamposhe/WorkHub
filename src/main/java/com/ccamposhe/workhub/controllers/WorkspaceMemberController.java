package com.ccamposhe.workhub.controllers;

import com.ccamposhe.workhub.domain.User;
import com.ccamposhe.workhub.domain.WorkspaceMember;
import com.ccamposhe.workhub.dtos.JoinWorkspaceRequestDTO;
import com.ccamposhe.workhub.services.WorkspaceMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class WorkspaceMemberController {

    private final WorkspaceMemberService service;

    @PostMapping("/join")
    public ResponseEntity<WorkspaceMember> join(
            @RequestBody @Valid JoinWorkspaceRequestDTO dto,
            @AuthenticationPrincipal User loggedUser) {
        WorkspaceMember newMember = service.joinWorkspace(loggedUser.getId(), dto.inviteCode());
        return ResponseEntity.status(HttpStatus.CREATED).body(newMember);
    }

    @PutMapping("/{memberId}/approve")
    public ResponseEntity<WorkspaceMember> approve(
            @PathVariable("memberId") UUID memberId,
            @AuthenticationPrincipal User loggedUser) {
        WorkspaceMember approvedMember = service.approveMember(loggedUser.getId(), memberId);
        return ResponseEntity.ok(approvedMember);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable("memberId") UUID memberId,
            @AuthenticationPrincipal User loggedUser) {
        service.removeMember(memberId, loggedUser.getId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-workspaces")
    public ResponseEntity<List<WorkspaceMember>> listMyWorkspaces(@AuthenticationPrincipal User loggedUser) {
        List<WorkspaceMember> myWorkspaces = service.listMyWorkspaces(loggedUser.getId());
        return ResponseEntity.ok(myWorkspaces);
    }
}