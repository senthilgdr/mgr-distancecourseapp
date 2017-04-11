package in.tnmgrmu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Category;
import in.tnmgrmu.model.Course;
import in.tnmgrmu.model. CourseCategory;


@Repository
public class CourseCategoryDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public  CourseCategory findById(Long id) {

		String sql = "SELECT  cc.cc_id,cc.course_id,c.course_name,cc.category_id,ca.category_name,cc.active,cc.created_at,cc.modified_at FROM course_categories cc,categories ca,courses c where cc.course_id=c.course_id and cc.category_id=ca.category_id and cc.cc_id= ?";
		 CourseCategory  CourseCategory = jdbcTemplate.queryForObject(sql, new Object[] { id }, (rs, rowNo) -> {
			return convert(rs);
		});
		return  CourseCategory;

	}

	private  CourseCategory convert(ResultSet rs) throws SQLException {
		 CourseCategory  courseCategory = new  CourseCategory(); 
		 courseCategory.setCcId(rs.getLong("cc_id"));
		 	
		Course course=new Course();
		course.setCourseId(rs.getLong("course_id"));
		course.setCourseName(rs.getString("course_name"));
		courseCategory.setCourse(course);
		 
		Category category=new Category();
		category.setCategoryId(rs.getLong("category_id"));
		category.setCategoryName(rs.getString("category_name"));
		courseCategory.setCategory(category);
		
		 courseCategory.setActive(rs.getBoolean("active"));		 
		 courseCategory.setCreatedDate(rs.getDate("created_at").toLocalDate());		 
		 courseCategory.setModifiedDate(rs.getDate("modified_at").toLocalDate());
		return  courseCategory;
	}

	public List< CourseCategory> list() {

		String sql = "select cc.cc_id,cc.course_id,c.course_name,cc.category_id,ca.category_name,cc.active,cc.created_at,cc.modified_at FROM course_categories cc,categories ca,courses c WHERE cc.course_id=c.course_id AND cc.category_id=ca.category_id";

		List< CourseCategory> list = jdbcTemplate.query(sql, new Object[] {}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	public void save( CourseCategory  courseCategory) {

		String sql = "insert into  course_categories ( course_id,category_id) values ( ?,? )";

		int rows = jdbcTemplate.update(sql,  courseCategory.getCourse().getCourseId(), courseCategory.getCategory().getCategoryId());

		System.out.println("No of rows inserted:" + rows);
	}

	public void update( CourseCategory  courseCategory) {

		String sql = "update course_categories set course_id=?,category_id=? where cc_id =? ";

		Integer rows = jdbcTemplate.update(sql,  courseCategory.getCourse().getCourseId(), courseCategory.getCategory().getCategoryId(),courseCategory.getCcId());

		System.out.println("No of rows modified:" + rows);

	}
	public void delete(Long courseId) {

		String sql = "delete from course_categories where cc_id = ?";
		int rows = jdbcTemplate.update(sql, courseId);
		System.out.println("No of rows deleted:" + rows);

	}

}
