package com.ccamposhe.workhub.repositories;

import com.ccamposhe.workhub.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

    boolean existsByInviteCode(String inviteCode);

    Optional<Workspace> findByInviteCode(String inviteCode);
}
