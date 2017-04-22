package in.tnmgrmu.dao;

import java.sql.Date;
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
		courseEnroll.setId(rs.getLong("course_enrollment_id"));
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
	
	private CourseEnrollment convert1(ResultSet rs) throws SQLException {
		CourseEnrollment courseEnroll = new CourseEnrollment();
		courseEnroll.setId(rs.getLong("course_enrollment_id"));
		courseEnroll.setActive(rs.getBoolean("active"));
		courseEnroll.setEnrollDate(rs.getDate("enrollment_date").toLocalDate());
		
		Date completionDate = rs.getDate("completion_date");
		if(completionDate !=null) {
			courseEnroll.setCompletionDate(completionDate.toLocalDate());
		}
		
		Course course=new Course();
		course.setCourseId(rs.getLong("course_id"));
		course.setCourseName(rs.getString("course_name"));
		
		courseEnroll.setCourse(course);
				
		return courseEnroll;
	}

	public List<CourseEnrollment> list() {

		String sql = "select ce.id as course_enrollment_id,ce.course_id,c.course_name,ce.user_id,u.name,ce.active from course_enrollment ce,courses c,user_accounts u where ce.course_id=c.course_id and u.id=ce.user_id";

		List<CourseEnrollment> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public void courseEnroll(CourseEnrollment courseEnroll) {

		Long userId = courseEnroll.getUser().getId();
		Long courseId = courseEnroll.getCourse().getCourseId();
		
		String sql = "insert into course_enrollment ( user_id,course_id) values ( ?,? )";

		int rows = jdbcTemplate.update(sql, courseEnroll.getUser().getId(),courseEnroll.getCourse().getCourseId());

		System.out.println("No of rows inserted:" + rows);
		
		
		String sql2 = "INSERT INTO user_course_videos ( user_id, course_video_id) " + 
				" SELECT " + userId + ", id AS course_videos_id FROM course_activities WHERE course_id = ?";
		System.out.println("Assign courses :" + sql2 );
		
		int rows2 = jdbcTemplate.update(sql2 , courseId);
		System.out.println("No of course videos assigned:" + rows2);		
		
	}

	public void delete(Long enrollmentId) {
		
		String sql = "update course_enrollment set active=0 where id= ? and active=1";
		int rows = jdbcTemplate.update(sql, enrollmentId);
		System.out.println("No of rows deleted:" + rows);

	}
	public List<CourseEnrollment> findByUserId(Long userId) {

		String sql = "SELECT ce.id as course_enrollment_id,ce.course_id, c.course_name,ce.active,ce.enrollment_date,ce.completion_date FROM course_enrollment ce,courses c WHERE c.course_id = ce.course_id "+ 
				"AND ce.user_id=? AND ce.active=1";
		List<CourseEnrollment> courseEnroll = jdbcTemplate.query(sql, new Object[] { userId }, (rs, rowNo) -> {
			return convert1(rs);
		});
		return courseEnroll;

	}
	
	public List<CourseEnrollment> findByCourseId(Long courseId) {

		String sql = "select ce.course_enrollment_id,ce.user_id, u.name,ce.active from course_enrollment ce,user_accounts u"
				+ " where u.id = ce.user_id AND ce.course_id=? AND ce.active=1";
		List<CourseEnrollment> courseEnroll = jdbcTemplate.query(sql, new Object[] { courseId }, (rs, rowNo) -> {
			return convert(rs);
		});
		return courseEnroll;
	}
	
}
