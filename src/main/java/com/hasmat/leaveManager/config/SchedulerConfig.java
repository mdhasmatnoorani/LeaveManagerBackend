
package com.hasmat.leaveManager.config;

import com.hasmat.leaveManager.job.LeaveBalanceUpdateJob;
import org.quartz.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@Configuration
@EnableTransactionManagement
public class SchedulerConfig {

    @Bean
    @ConditionalOnProperty(value = "employee.leave-balance-update-job.cron-enabled", havingValue = "true")
    public JobDetail leaveBalanceUpdateJobDetail() {
        return JobBuilder.newJob(LeaveBalanceUpdateJob.class)
                .withIdentity("leaveBalanceUpdateJob")
                .storeDurably()  // Necessary for non-volatile jobs
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "employee.leave-balance-update-job.cron-enabled", havingValue = "true")
    public Trigger leaveBalanceUpdateTrigger(JobDetail leaveBalanceUpdateJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(leaveBalanceUpdateJobDetail)
                .withIdentity("leaveBalanceUpdateTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(900000).repeatForever())
                .build();
    }
}
