package com.example.hellospring.repository;

import com.example.hellospring.domain.entities.Error;
import com.example.hellospring.domain.projections.ErrorProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ErrorRepository extends JpaRepository<Error, Long> {

    List<Error> findAll();

    Optional<Error> findById(Long id);

    List<Error> findByDeviceTypeAndStartTimeBetween(String deviceType, Long startTimeBegin, Long startTimeEnd);

    @Query(value = "select err from Error err")
    List<Error> findAllByNonNativeQuery();

    @Query(
            
            nativeQuery = true,
            value = "select (toFloat64(count(*)))/(toFloat64(:minutesNumber)) as avgVal, errorType as errorType from error " +
                    "where deviceType = :deviceType and " +
                    "startTime between :startTimeBegin and :startTimeEnd " +
                    "group by errorType")
    List<ErrorProjection> findByNativeQuery(@Param("minutesNumber") Long minutesNumber,
                                            @Param("startTimeBegin") Long startTimeBegin,
                                            @Param("startTimeEnd") Long startTimeEnd,
                                            @Param("deviceType") String deviceType);

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM tutorial.error ORDER BY deviceId OFFSET 1 ROW FETCH FIRST 2 ROWS WITH TIES")
    List<Error> findWithTies();
}
