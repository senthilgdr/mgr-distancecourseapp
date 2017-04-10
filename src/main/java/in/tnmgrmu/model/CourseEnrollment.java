package in.tnmgrmu.model;

import lombok.Data;

@Data
public class CourseEnrollment {

	private Long courseId;
	private User user;
	private Course course;
	private boolean active;
	

}
