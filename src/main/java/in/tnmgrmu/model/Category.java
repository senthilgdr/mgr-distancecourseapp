package in.tnmgrmu.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Category {

	private Long categoryId;
	private String categoryName;
	private boolean active;
	private LocalDate createdDate;
	private LocalDate modifiedDate;
	

}
