package com.ccamposhe.workhub.controllers;

import com.ccamposhe.workhub.domain.User;
import com.ccamposhe.workhub.domain.Workspace;
import com.ccamposhe.workhub.domain.WorkspaceMember;
import com.ccamposhe.workhub.dtos.WorkspaceRequestDTO;
import com.ccamposhe.workhub.services.WorkspaceMemberService;
import com.ccamposhe.workhub.services.WorkspaceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService service;
    private final WorkspaceMemberService memberService;

    @PostMapping
    public ResponseEntity<Workspace> create(@RequestBody @Valid WorkspaceRequestDTO dto, @AuthenticationPrincipal User loggedUser){
        Workspace savedWorkspace = service.createWorkspace(dto, loggedUser.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedWorkspace);
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> findMyWorkspaces(@AuthenticationPrincipal User loggedUser) {
        List<Workspace> workspaces = service.findMyWorkspaces(loggedUser.getId());

        return ResponseEntity.ok(workspaces);
    }

    @GetMapping("/{workspaceId}/members")
    public ResponseEntity<List<WorkspaceMember>> listMembers(
            @PathVariable("workspaceId") UUID workspaceId,
            @AuthenticationPrincipal User loggedUser
    ) {
        List<WorkspaceMember> members = memberService.listMemberByWorkspace(workspaceId, loggedUser.getId());

        return ResponseEntity.ok(members);
    }

}
