package in.tnmgrmu.model;

import lombok.Data;

@Data
public class CourseVideo {

	private Long cvId;
	private String activityType;
	private Video video;
	private Course course;
}
