package com.vahana;

import com.vahana.configurations.logging.CustomMDCTypes;
import com.vahana.configurations.logging.TargetTypes;
import com.vahana.utils.v1.MDCUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class VahanaApplication {
	public static void main(String[] args) {
		MDCUtils.configureMDCVar(CustomMDCTypes.TARGET, TargetTypes.SYSTEM);
		SpringApplication.run(VahanaApplication.class, args);
	}
}