package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.AnnouncementEntity;
import com.nwpu.bsss.repository.AnnounRepository;
import com.nwpu.bsss.service.AnnounService;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

public class AnnounServiceImpl implements AnnounService {
    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private AnnounRepository announRepository;

    @Override
    public Optional<AnnouncementEntity> getFisrtAnnoun() {
        return announRepository.getTopByEndTime();
    }
}
