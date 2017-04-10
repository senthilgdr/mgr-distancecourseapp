package in.tnmgrmu.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Video {

	private Long id;

	private String title;
	
	private String url;
	
	private boolean active;

	private LocalDate createdDate;

	private LocalDate modifiedDate;
}
