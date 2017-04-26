package in.tnmgrmu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Course;


@Repository
public class CourseDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Course findById(Long id) {

		String sql = "select  course_id,course_name,description,active,created_by,created_at,modified_by,modified_at,category from courses where course_id= ?";
		Course course = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNo) -> {
			return convert(rs);
		});
		return course;

	}

	private Course convert(ResultSet rs) throws SQLException {
		Course course = new Course();
		course.setCourseId(rs.getLong("course_id"));
		course.setCourseName(rs.getString("course_name"));
		course.setDescription(rs.getString("description"));	
		course.setCategory(rs.getString("category"));	
		course.setActive(rs.getBoolean("active"));
		course.setCreatedBy(rs.getLong("created_by"));
		course.setCreatedDate(rs.getDate("created_at").toLocalDate());
		course.setModifiedBy(rs.getLong("modified_by"));		
		course.setModifiedDate(rs.getDate("modified_at").toLocalDate());
		return course;
	}

	public List<Course> list() {

		String sql = "select  course_id,course_name,description,active,created_by,created_at,modified_by,modified_at,category from courses";

		List<Course> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}
	public List<String> findAllCategory() {

		String sql = "SELECT DISTINCT category FROM courses";

		List<String> list = jdbcTemplate.queryForList(sql, new Object[] {}, String.class);
		return list;
	}

	public List<Course> list(String categroy) {

		String sql = "select  course_id,course_name,description,active,created_by,created_at,modified_by,modified_at,category from courses where category=?";

		List<Course> list = jdbcTemplate.query(sql, new Object[] {categroy}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}
	public void save(Course course) {

		String sql = "insert into courses ( course_name,description,created_by,modified_by,category) values ( ?,?,?,?,?)";

		int rows = jdbcTemplate.update(sql, course.getCourseName(),course.getDescription(),course.getCreatedBy(),course.getCreatedBy(),course.getCategory());

		System.out.println("No of rows inserted:" + rows);
	}

	public void update(Course course) {

		String sql = "update courses set course_name=?, description= ?,modified_by=?, category=? where course_id =? ";

		Integer rows = jdbcTemplate.update(sql, course.getCourseName(),course.getDescription(),course.getModifiedBy(),course.getCategory(),course.getCourseId());

		System.out.println("No of rows modified:" + rows);

	}
	/*public void courseDisable(Course course) {

		String sql = "update courses set active=0 where course_id= ? and active=1";
		
		Integer rows = jdbcTemplate.update(sql, course.getCourseId());

		System.out.println("No of rows modified:" + rows);

	}
	public void courseEnable(Course course) {

		String sql = "update courses set active=1 where course_id= ? and active=0";
		
		Integer rows = jdbcTemplate.update(sql,course.getCourseId());

		System.out.println("No of rows modified:" + rows);

	}
*/
	public void delete(Long courseId) {

		String sql = "delete from courses where id = ?";
		int rows = jdbcTemplate.update(sql, courseId);
		System.out.println("No of rows deleted:" + rows);

	}

}
