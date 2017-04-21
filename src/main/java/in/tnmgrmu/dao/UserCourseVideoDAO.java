package in.tnmgrmu.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.tnmgrmu.model.Course;
import in.tnmgrmu.model.CourseVideo;
import in.tnmgrmu.model.UserCourseVideo;
import in.tnmgrmu.model.Video;


@Repository
public class UserCourseVideoDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;


	public List<UserCourseVideo> list(Long courseId,Long userId) {
		
		String sql="SELECT c.course_id, c.course_name,c.description,v.title,v.url,uc.id,uc.status FROM "
				+ "courses c,videos v, course_videos cv,user_course_videos uc WHERE c.course_id = cv.course_id "
				+ "AND v.video_id  = cv.video_id AND uc.course_video_id=cv.id AND cv.course_id=? AND uc.user_id=?";
		
		System.out.println(sql);
		List<UserCourseVideo> list = jdbcTemplate.query(sql, new Object[] {courseId,userId}, (rs, rowNum) -> {
			CourseVideo  courseVideo = new  CourseVideo(); 
						 	
			Course course=new Course();
			course.setCourseId(rs.getLong("course_id"));
			course.setCourseName(rs.getString("course_name"));
			course.setDescription(rs.getString("description"));
			
			courseVideo.setCourse(course);
			
			Video video=new Video();
			/*video.setId(rs.getLong("video_id"));*/
			video.setTitle(rs.getString("title"));
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
	public Long pendingVideos(Long courseId,Long userId) throws Exception {
		 
        Long pendingVideosCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_course_videos uc,course_videos cv WHERE "
        		+ " uc.course_video_id=cv.id AND  uc.status='PENDING' AND cv.course_id= ? AND uc.user_id=?",
        		new Object[] {courseId,userId},
                Long.class);
         
        return pendingVideosCount;
    }
	
	public Long completeVideos(Long courseId,Long userId) throws Exception {
		 
        Long completeVideosCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user_course_videos uc,course_videos cv WHERE "
        		+ " uc.course_video_id=cv.id AND  uc.status='COMPLETED' AND cv.course_id= ? AND uc.user_id=?",
        		new Object[] {courseId,userId},
                Long.class);
         
        return completeVideosCount;
    }
	public void updateStatus(Long statusId) {

		String sql = "update  user_course_videos set status='COMPLETED',completion_date=now() where  id =? ";

		Integer rows = jdbcTemplate.update(sql,statusId);

		System.out.println("No of rows modified:" + rows);

	}
}
