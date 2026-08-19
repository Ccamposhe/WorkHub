package com.ccamposhe.workhub.services;

import com.ccamposhe.workhub.domain.Workspace;
import com.ccamposhe.workhub.repositories.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository repository;

    public Workspace createWorkspace(Workspace workspace){
        if (repository.existsByInviteCode(workspace.getInviteCode())){ //receber o valor boolean, criado no repository, verificando se o valor atual ja existe
            throw new RuntimeException("Este codigo ja esta em uso");
        }
        return repository.save(workspace);
    }
    public List<Workspace> findAllWorkspaces(){
        return repository.findAll();
    }

}
