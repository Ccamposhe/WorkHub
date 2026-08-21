package com.ccamposhe.workhub.repositories;

import com.ccamposhe.workhub.domain.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, UUID> {

    boolean existsByUser_IdAndWorkspace_Id(UUID userId, UUID workspaceId);

    Optional<WorkspaceMember> findByUser_IdAndWorkspace_Id(UUID userId, UUID workspaceId);
}
