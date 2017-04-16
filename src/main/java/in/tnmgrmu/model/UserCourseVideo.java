package in.tnmgrmu.model;

import lombok.Data;

@Data
public class UserCourseVideo {
	
	private Long ucvId;
	private User user;
	private CourseVideo courseVideo;
	private String status;
	

}
