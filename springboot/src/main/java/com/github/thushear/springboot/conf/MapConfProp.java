package com.github.thushear.springboot.conf;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by kongming on 2018/3/7.
 */
@ConfigurationProperties(prefix = "map",ignoreUnknownFields=true)
@Data
@NoArgsConstructor
@Setter
@Getter
public class MapConfProp {


    private String key;


    private String value;






}
