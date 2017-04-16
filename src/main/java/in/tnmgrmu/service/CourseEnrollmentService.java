package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.CourseEnrollmentDAO;
import in.tnmgrmu.model.CourseEnrollment;

@Service
public class CourseEnrollmentService {
	@Autowired
	private CourseEnrollmentDAO courseEnrollmentDAO;

	public CourseEnrollment findById(Long id) {
		return courseEnrollmentDAO.findById(id);
	}

	public List<CourseEnrollment> list() {
		return courseEnrollmentDAO.list();

	}

	public void courseEnroll(CourseEnrollment course) {

		courseEnrollmentDAO.courseEnroll(course);
	}

	public void delete(Long enrollmentId) {

		courseEnrollmentDAO.delete(enrollmentId);
	}
	public List<CourseEnrollment> findByUserId(Long userId) {

		return courseEnrollmentDAO.findByUserId(userId);
	}

	public List<CourseEnrollment> findByCourseId(Long courseId) {

		return courseEnrollmentDAO.findByCourseId(courseId);
	}


}
