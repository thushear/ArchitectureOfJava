package com.github.thushear.conf;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by kongming on 2017/9/4.
 */
public class DropwizardHealthCheck extends HealthCheck {


    private final String template;

    public DropwizardHealthCheck(String template) {
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        String value = String.format(template,"TEST");
        if (!value.contains("TEST")){
            return Result.unhealthy("template error");
        }
        return Result.healthy();
    }
}
