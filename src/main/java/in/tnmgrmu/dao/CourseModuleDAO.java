package in.tnmgrmu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.CourseModule;


@Repository
public class CourseModuleDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CourseModule findById(Long id) {

		String sql = "select  cm.course_module_id,cm.course_id, c.course_name,cm.module_name,cm.active,cm.created_by,cm.created_at,cm.modified_by,cm.modified_at from "
				+ "course_module cm,courses c where cm.course_id=c.course_id and cm.course_module_id= ?";
		CourseModule courseModule = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNo) -> {
			return convert(rs);
		});
		return courseModule;

	}

	private CourseModule convert(ResultSet rs) throws SQLException {
		CourseModule courseModule = new CourseModule();	
		
		Course course=new Course();
		course.setCourseId(rs.getLong("course_id"));
		course.setCourseName(rs.getString("course_name"));
		courseModule.setCourse(course);
		
		courseModule.setCourseModuleId(rs.getLong("course_module_id"));		
		courseModule.setModuleName(rs.getString("module_name"));
		courseModule.setActive(rs.getBoolean("active"));
		courseModule.setCreatedBy(rs.getLong("created_by"));
		courseModule.setCreatedDate(rs.getDate("created_at").toLocalDate());
		courseModule.setModifiedBy(rs.getLong("modified_by"));		
		courseModule.setModifiedDate(rs.getDate("modified_at").toLocalDate());
		return courseModule;
	}

	public List<CourseModule> list() {

		String sql = "select  cm.course_module_id,cm.course_id, c.course_name,cm.module_name,cm.active,cm.created_by,cm.created_at,cm.modified_by,cm.modified_at from "
				+ "course_module cm,courses c where cm.course_id=c.course_id";

		List<CourseModule> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public void delete(Long courseId) {

		String sql = "delete from course_module where course_module_id = ?";
		int rows = jdbcTemplate.update(sql, courseId);
		System.out.println("No of rows deleted:" + rows);

	}

}
