package com.francis.Activity_Tracker.repositories;

import com.francis.Activity_Tracker.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {
    Optional<Activity> findActivityByName( String name);
    Optional<Activity> deleteByIdAndName(Long id, String name);

    Boolean existsByName(String activityName);
}
