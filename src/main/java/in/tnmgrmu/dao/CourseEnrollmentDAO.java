package in.tnmgrmu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.CourseEnrollment;
import in.tnmgrmu.model.User;


@Repository
public class CourseEnrollmentDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CourseEnrollment findById(Long id) {

		String sql = "select ce.course_enrollment_id,ce.course_id,c.course_name,ce.user_id,u.name,ce.active from course_enrollment ce,courses c,user_accounts u where ce.course_id=c.course_id and u.id=ce.user_id and ce.course_enrollment_id= ? AND ce.active=1";
		CourseEnrollment courseEnroll = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNo) -> {
			return convert(rs);
		});
		return courseEnroll;

	}

	private CourseEnrollment convert(ResultSet rs) throws SQLException {
		CourseEnrollment courseEnroll = new CourseEnrollment();
		courseEnroll.setCourseId(rs.getLong("course_enrollment_id"));
		courseEnroll.setActive(rs.getBoolean("active"));
		
		Course course=new Course();
		course.setCourseId(rs.getLong("course_id"));
		course.setCourseName(rs.getString("course_name"));
		
		User user=new User();
		user.setId(rs.getLong("user_id"));
		user.setName("name");
		
		courseEnroll.setUser(user);
		courseEnroll.setCourse(course);
				
		return courseEnroll;
	}

	public List<CourseEnrollment> list() {

		String sql = "select ce.course_enrollment_id,ce.course_id,c.course_name,ce.user_id,u.name,ce.active from course_enrollment ce,courses c,user_accounts u where ce.course_id=c.course_id and u.id=ce.user_id";

		List<CourseEnrollment> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public void courseEnroll(CourseEnrollment courseEnroll) {

		String sql = "insert into course_enrollment ( user_id,course_id) values ( ?,? )";

		int rows = jdbcTemplate.update(sql, courseEnroll.getUser().getId(),courseEnroll.getCourse().getCourseId());

		System.out.println("No of rows inserted:" + rows);
	}

	public void delete(Long enrollmentId) {

		String sql = "delete from course_enrollment where course_enrollment_id = ?";
		int rows = jdbcTemplate.update(sql, enrollmentId);
		System.out.println("No of rows deleted:" + rows);

	}
	
}
