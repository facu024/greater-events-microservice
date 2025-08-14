package ar.edu.unnoba.pdyc2025.user.repository;

import ar.edu.unnoba.pdyc2025.user.model.ArtistRef;
import ar.edu.unnoba.pdyc2025.user.model.EventRef;
import ar.edu.unnoba.pdyc2025.user.model.UserNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {
    List<UserNotification> findByUserIdOrderByTimestampDesc(Long userId);
}
