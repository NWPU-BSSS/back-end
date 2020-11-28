package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.AnnouncementEntity;

import java.util.Optional;

public interface AnnounService {
    Optional<AnnouncementEntity> getFisrtAnnoun();
}
