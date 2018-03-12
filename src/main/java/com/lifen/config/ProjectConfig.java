package com.lifen.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by 廖师兄
 * 2017-07-30 11:43
 */
@Data
@ConfigurationProperties(prefix = "server")
@Component
public class ProjectConfig {


}
