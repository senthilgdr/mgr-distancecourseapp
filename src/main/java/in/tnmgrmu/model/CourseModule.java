package in.tnmgrmu.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CourseModule {

	private Long courseModuleId;
	private String moduleName;
	private Course course;
	private boolean active;
	private Long createdBy;
	private LocalDate createdDate;
	private Long modifiedBy;
	private LocalDate modifiedDate;
	

}
