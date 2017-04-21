package in.tnmgrmu.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.CourseVideo;
import in.tnmgrmu.model.UserCourseVideo;
import in.tnmgrmu.model.Video;


@Repository
public class CourseVideoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	

	private  CourseVideo convert(ResultSet rs) throws SQLException {
		CourseVideo  courseVideo = new  CourseVideo(); 
		courseVideo.setCvId(rs.getLong("course_video_id"));
		courseVideo.setActivityType(rs.getString("activityType"));
		 	
		Course course=new Course();
		course.setCourseId(rs.getLong("course_id"));
		course.setCourseName(rs.getString("course_name"));
		course.setDescription(rs.getString("description"));
		
		courseVideo.setCourse(course);
		
		Video video=new Video();
		video.setId(rs.getLong("video_id"));
		video.setUrl(rs.getString("url"));
		 
		courseVideo.setVideo(video);
		
		return  courseVideo;
	}
	
	public List<CourseVideo> list(Long courseId) {
		
		String sql="SELECT v.title FROM "
				+ "videos v, course_activities cv WHERE"
				+ " v.video_id  = cv.video_id  AND cv.course_id=? ";
		
		System.out.println(sql);
		
		List<CourseVideo> list = jdbcTemplate.query(sql, new Object[] {courseId}, (rs, rowNum) -> {
			
			CourseVideo  courseVideo = new  CourseVideo(); 
									
			Video video=new Video();
			video.setTitle(rs.getString("title"));
			courseVideo.setVideo(video);
			
			return  courseVideo;
		});
		return list;
	}
	public Long findTotalVideos(Long courseId) throws Exception {
		 
        Long videosCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM course_activities WHERE COURSE_ID=?",new Object[] {courseId},
                Long.class);
         
        return videosCount;
    }
	
	
	public CourseVideo findById(Long id) {

		String sql = "SELECT c.course_id AS course_id,c.course_name AS course_name, cv.activity_type ,"
				+ "c.description,v.video_id AS video_id,v.url , cv.id AS course_video_id "
				+ "FROM course_activities cv,courses c,videos v WHERE cv.course_id=c.course_id AND cv.video_id=v.video_id"
				+ " AND cv.id=?";

		 CourseVideo list = jdbcTemplate.queryForObject(sql, new Object[] {id}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	
}
