package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.AnnouncementsEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface AnnounService {
    Optional<AnnouncementsEntity> getFisrtAnnoun();
}
