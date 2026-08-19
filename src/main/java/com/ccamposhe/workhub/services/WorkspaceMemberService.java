package com.ccamposhe.workhub.services;

import com.ccamposhe.workhub.domain.User;
import com.ccamposhe.workhub.domain.Workspace;
import com.ccamposhe.workhub.domain.WorkspaceMember;
import com.ccamposhe.workhub.domain.enums.MemberRole;
import com.ccamposhe.workhub.domain.enums.MemberStatus;
import com.ccamposhe.workhub.repositories.UserRepository;
import com.ccamposhe.workhub.repositories.WorkspaceMemberRepository;
import com.ccamposhe.workhub.repositories.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceMemberService {

    private final WorkspaceMemberRepository memberRepository;
    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;

    public WorkspaceMember joinWorkspace(UUID userId, String inviteCode){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Usuario nao encontrado"));

        Workspace workspace = workspaceRepository.findByInviteCode(inviteCode)
                .orElseThrow(()-> new RuntimeException("Codigo de convite invalido"));

        if (memberRepository.existsByUser_IdAndWorkspace_Id(user.getId(), workspace.getId())){
            throw new RuntimeException("Voce ja enviou uma solicitacao para esta empresa");
        }

        WorkspaceMember newMember = new WorkspaceMember();
        newMember.setUser(user);
        newMember.setWorkspace(workspace);
        newMember.setStatus(MemberStatus.PENDING);
        newMember.setRole(MemberRole.MEMBER);

        return memberRepository.save(newMember);
    }

    public WorkspaceMember approveMember(UUID memberId){
        WorkspaceMember member = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("Solicitacao nao encontrada"));

        member.setStatus(MemberStatus.APPROVED);

        return memberRepository.save(member);
    }
}
