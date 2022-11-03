package com.jayesh.his.co.binding;

import lombok.Data;

@Data
public class CoResponse {

	private Long totalTriggers;
	private Long failedTrigger;
	private Long successTriggers;
}
