package in.tnmgrmu.model;

import lombok.Data;

@Data
public class BatchStudent {
	
	private Long batchStudentId;
	private Batch batch;
	private User user;
	private boolean active;	

}
