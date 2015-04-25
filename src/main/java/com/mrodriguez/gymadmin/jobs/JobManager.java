package com.mrodriguez.gymadmin.jobs;

import static org.quartz.JobBuilder.newJob;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 *
 * @author mauricio
 */
public class JobManager {

	public static void startJobs() throws SchedulerException {

		SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
		Scheduler sched = schedFact.getScheduler();

		// define the job and tie it to our Job1 class
		JobDetail job1 = newJob(Job1.class).withIdentity("Job1", "group1")
				.build();

		// Trigger the job to run now, and then every 40 seconds
		Trigger triggerJob1 = newTrigger()
				.withIdentity("job1Trigger", "group1")
				.startNow()
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(5)
								.repeatForever()).build();

		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job1, triggerJob1);

		// define the job and tie it to our Job1 class
		JobDetail job2 = newJob(Job2.class).withIdentity("Job2", "group1")
				.build();

		// Trigger the job to run now, and then every 40 seconds
		Trigger triggerJob2 = newTrigger()
				.withIdentity("job2Trigger", "group1")
				.startNow()
				.withSchedule(
						simpleSchedule().withIntervalInSeconds(7)
								.repeatForever()).build();

		// Tell quartz to schedule the job using our trigger
		sched.scheduleJob(job2, triggerJob2);

		sched.start();
	}

}
