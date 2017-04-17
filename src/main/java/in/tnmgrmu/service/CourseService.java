package in.tnmgrmu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.tnmgrmu.dao.CourseDAO;
import in.tnmgrmu.model.Course;

@Service
public class CourseService {
	@Autowired
	private CourseDAO courseDAO;

	public Course findById(Long id) {
		return courseDAO.findById(id);
	}

	public List<Course> list() {
		return courseDAO.list();

	}
	public List<Course> list(String category) {
		return courseDAO.list(category);

	}

	public void save(Course course) {

		courseDAO.save(course);
	}
	public  List<String> findAllCategory(){
		return courseDAO.findAllCategory();
	}
	public void delete(Long courseId) {

		courseDAO.delete(courseId);
	}

	public void update(Course course) {

		courseDAO.update(course);
	}
	
	public void courseDisable(Course course) {

		courseDAO.courseDisable(course);
	}
	public void courseEnable(Course course) {

		courseDAO.courseEnable(course);
	}
	
}
