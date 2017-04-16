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

	public List<UserCourseVideo> list(Long courseId,Long userId) {

		/*String sql = "SELECT c.course_id AS course_id,c.course_name AS course_name,"
				+ "c.description,v.video_id AS video_id,v.url , cv.id AS course_video_id "
				+ "FROM course_videos cv,courses c,videos v WHERE cv.course_id=c.course_id AND cv.video_id=v.video_id"
				+ " AND uc.course_video_id=cv.id AND cv.course_id=? AND uc.user_id=?";
*/
		String sql="SELECT c.course_id, c.course_name,c.description,v.url,uc.id,uc.status FROM "
				+ "courses c,videos v, course_videos cv,user_course_videos uc WHERE c.course_id = cv.course_id "
				+ "AND v.video_id  = cv.video_id AND uc.course_video_id=cv.id AND cv.course_id=? AND uc.user_id=?";
		
		System.out.println(sql);
		List<UserCourseVideo> list = jdbcTemplate.query(sql, new Object[] {courseId,userId}, (rs, rowNum) -> {
			CourseVideo  courseVideo = new  CourseVideo(); 
			//courseVideo.setCvId(rs.getLong("course_video_id"));
			 	
			Course course=new Course();
			course.setCourseId(rs.getLong("course_id"));
			course.setCourseName(rs.getString("course_name"));
			course.setDescription(rs.getString("description"));
			
			courseVideo.setCourse(course);
			
			Video video=new Video();
			/*video.setId(rs.getLong("video_id"));*/
			video.setUrl(rs.getString("url"));
			courseVideo.setVideo(video);
			
			
			UserCourseVideo cv=new UserCourseVideo();
			cv.setUcvId(rs.getLong("id"));
			cv.setStatus(rs.getString("status"));			
			cv.setCourseVideo(courseVideo);
			
			
			return  cv;
		});
		return list;
	}
	
	public void updateStatus(Long statusId) {

		String sql = "update  user_course_videos set status='COMPLETED',completion_date=now() where  id =? ";

		Integer rows = jdbcTemplate.update(sql,statusId);

		System.out.println("No of rows modified:" + rows);

	}
	
	public CourseVideo findById(Long id) {

		String sql = "SELECT c.course_id AS course_id,c.course_name AS course_name,"
				+ "c.description,v.video_id AS video_id,v.url , cv.id AS course_video_id "
				+ "FROM course_videos cv,courses c,videos v WHERE cv.course_id=c.course_id AND cv.video_id=v.video_id"
				+ " AND cv.id=?";

		 CourseVideo list = jdbcTemplate.queryForObject(sql, new Object[] {id}, (rs, rowNum) -> {
			return convert(rs);
		});
		return list;
	}

	
}
