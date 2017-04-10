package in.tnmgrmu.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CourseCategory {

	private Long ccId;
	private Category category;
	private Course course;
	private boolean active;
    private LocalDate createdDate;
	private LocalDate modifiedDate;
	

}
