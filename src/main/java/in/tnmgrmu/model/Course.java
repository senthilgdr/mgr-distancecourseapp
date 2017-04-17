package in.tnmgrmu.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Course {

	private Long courseId;
	private String courseName;
	private String description;
	private String category;
	private boolean active;
	private Long createdBy;
	private LocalDate createdDate;
	private Long modifiedBy;
	private LocalDate modifiedDate;
	

}
