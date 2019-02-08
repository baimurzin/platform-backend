package com.pwrstd.platform.backend.repository;

import com.pwrstd.platform.backend.model.Point;
import com.pwrstd.platform.backend.model.Question;
import com.pwrstd.platform.backend.model.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findAllByQuestion(Question step);
}
