package in.tnmgrmu.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CourseEnrollment {

	private Long id;
	private User user;
	private Course course;
	private boolean active;
	private LocalDate enrollDate;
	private LocalDate completionDate;
	

}
