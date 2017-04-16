package in.tnmgrmu.model;

import lombok.Data;

@Data
public class CourseVideo {

	private Long cvId;
	private Video video;
	private Course course;
}
