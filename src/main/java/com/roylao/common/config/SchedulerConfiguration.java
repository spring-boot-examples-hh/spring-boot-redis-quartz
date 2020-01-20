package com.roylao.common.config;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class SchedulerConfiguration {

    @Value("${org.quartz.jobStore.class}")
    private String jobStoreClass;

    @Value("${org.quartz.jobStore.host}")
    private String jobStoreHost;

    @Value("${org.quartz.jobStore.misfireThreshold}")
    private String jobStoreMisfireThreshold;

    @Value("${org.quartz.jobStore.password}")
    private String jobStorePassword;

    @Value("${org.quartz.jobStore.port}")
    private String jobStorePort;

    @Value("${org.quartz.jobStore.redisCluster}")
    private String jobStoreRedisCluster;

    @Value("${org.quartz.jobStore.redisSentinel}")
    private String jobStoreRedisSentinel;

    @Value("${org.quartz.jobStore.masterGroupName}")
    private String jobStoreMasterGroupName;

    @Value("${org.quartz.jobStore.database}")
    private String jobStoreDataBase;

    @Value("${org.quartz.jobStore.keyPrefix}")
    private String jobStoreKeyPrefix;

    @Value("${org.quartz.jobStore.lockTimeout}")
    private String jobStoreLockTimeout;

    @Value("${org.quartz.jobStore.ssl}")
    private String jobStoreSsl;

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }

    /**
     *
     * @param jobFactory
     * @return
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        //设置自行启动
        factory.setAutoStartup(true);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    @Bean
    public Properties quartzProperties() {
        Properties prop = new Properties();
        prop.put("org.quartz.jobStore.class", jobStoreClass);
        prop.put("org.quartz.jobStore.host", jobStoreHost);
        prop.put("org.quartz.jobStore.misfireThreshold", jobStoreMisfireThreshold);
        prop.put("org.quartz.jobStore.password", jobStorePassword);
        prop.put("org.quartz.jobStore.port", jobStorePort);
        prop.put("org.quartz.jobStore.redisCluster", jobStoreRedisCluster);
        prop.put("org.quartz.jobStore.redisSentinel", jobStoreRedisSentinel);
        prop.put("org.quartz.jobStore.masterGroupName", jobStoreMasterGroupName);
        prop.put("org.quartz.jobStore.database", jobStoreDataBase);
        prop.put("org.quartz.jobStore.keyPrefix", jobStoreKeyPrefix);
        prop.put("org.quartz.jobStore.lockTimeout", jobStoreLockTimeout);
        prop.put("org.quartz.jobStore.ssl", jobStoreSsl);
        return prop;
    }
    /**
     * 配置JobFactory,为quartz作业添加自动连接支持
     */
    public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements
            ApplicationContextAware {
        private transient AutowireCapableBeanFactory beanFactory;

        @Override
        public void setApplicationContext(final ApplicationContext context) {
            beanFactory = context.getAutowireCapableBeanFactory();
        }
        @Override
        protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
            final Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job);
            return job;
        }
    }
}
