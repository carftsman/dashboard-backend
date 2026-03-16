package com.dhatvibs.dashboard.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDTO {

	
        private Long id;
        
	    private String dashboardName;

	    private String description;

	    private String previewImage;

}
