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

import java.util.List;
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

    public WorkspaceMember approveMember(UUID adminId, UUID memberId){
        WorkspaceMember targetMember = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("Solicitacao nao encontrada"));

        WorkspaceMember adminRequest = memberRepository.findByUser_IdAndWorkspace_Id(adminId, targetMember.getWorkspace().getId())
                        .orElseThrow(()-> new RuntimeException("Voce nao faz parte desta empresa"));

        if (adminRequest.getRole() != MemberRole.ADMIN){
            throw new RuntimeException("Acesso negado: Apenas administrador pode aprovar membros");
        }

        targetMember.setStatus(MemberStatus.APPROVED);

        return memberRepository.save(targetMember);
    }

    public List<WorkspaceMember> listMemberByWorkspace(UUID workspaceId){
        workspaceRepository.findById(workspaceId)
                .orElseThrow(()-> new RuntimeException("Empresa não encontrada"));

        return memberRepository.findByWorkspace_Id(workspaceId);
    }

    public void removeMember(UUID memberId, UUID requesterId){
        WorkspaceMember targetMember = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("Vinculo não encontrado"));

        boolean isSelf = targetMember.getUser().getId().equals(requesterId);

        boolean isAdmin = memberRepository.findByUser_IdAndWorkspace_Id(requesterId, targetMember.getWorkspace().getId())
                .map(member -> member.getRole() == MemberRole.ADMIN)
                .orElse(false);

        if (!isSelf && !isAdmin){
            throw new RuntimeException("Acesso negado, voce não tem permissão para remover membros");
        }

        memberRepository.delete(targetMember);

    }
    public List<WorkspaceMember> listMyWorkspaces(UUID userId){
        userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("Usuario não encontrado"));

        return memberRepository.findByUser_Id(userId);
    }

}
