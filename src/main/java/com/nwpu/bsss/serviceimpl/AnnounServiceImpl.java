package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.AnnouncementsEntity;
import com.nwpu.bsss.repository.AnnounRepository;
import com.nwpu.bsss.service.AnnounService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class AnnounServiceImpl implements AnnounService {
    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private AnnounRepository announRepository;

    @Override
    public Optional<AnnouncementsEntity> getFisrtAnnoun() {
        return announRepository.findTopByOrderByStartTimeDesc();
    }
}
