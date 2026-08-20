package com.ccamposhe.workhub.services;

import com.ccamposhe.workhub.domain.User;
import com.ccamposhe.workhub.domain.Workspace;
import com.ccamposhe.workhub.domain.WorkspaceMember;
import com.ccamposhe.workhub.domain.enums.MemberRole;
import com.ccamposhe.workhub.domain.enums.MemberStatus;
import com.ccamposhe.workhub.dtos.WorkspaceRequestDTO;
import com.ccamposhe.workhub.repositories.UserRepository;
import com.ccamposhe.workhub.repositories.WorkspaceMemberRepository;
import com.ccamposhe.workhub.repositories.WorkspaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository repository;
    private final UserRepository userRepository;
    private final WorkspaceMemberRepository memberRepository;

    @Transactional
    public Workspace createWorkspace(WorkspaceRequestDTO dto){

        if (repository.existsByInviteCode(dto.inviteCode())){ //receber o valor boolean, criado no repository, verificando se o valor atual ja existe
            throw new RuntimeException("Este codigo ja esta em uso");
        }

        User creator = userRepository.findById(dto.userId())
                .orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

        Workspace workspace = new Workspace();
        workspace.setName(dto.name());
        workspace.setInviteCode(dto.inviteCode());
        Workspace savedWorkspace = repository.save(workspace);

        WorkspaceMember adminMember = new WorkspaceMember();
        adminMember.setWorkspace(savedWorkspace);
        adminMember.setUser(creator);
        adminMember.setStatus(MemberStatus.APPROVED);
        adminMember.setRole(MemberRole.ADMIN);

        memberRepository.save(adminMember);

        return savedWorkspace;

    }
    public List<Workspace> findAllWorkspaces(){
        return repository.findAll();
    }

}
