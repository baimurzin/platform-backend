package com.pwrstd.platform.backend.service;

import com.pwrstd.platform.backend.model.Course;
import com.pwrstd.platform.backend.model.Step;

public interface CourseService {

    Step getCurrentStepForCourse(Course course);
    Step goNextStepForCourse(Course course);
    Step initializeCourse(Course course);

}
