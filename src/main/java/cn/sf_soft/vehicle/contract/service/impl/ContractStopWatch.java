package cn.sf_soft.vehicle.contract.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;

public class ContractStopWatch {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ContractStopWatch.class);

    private static ThreadLocal<List<StopWatch>> threadLocal = new ThreadLocal<>();


    public static StopWatch startWatch(Class clazz, String... messages) {
        StopWatch watch = new StopWatch();
        logger.debug(String.format("%s %s", clazz.getSimpleName(), StringUtils.join(messages, " ")));
        watch.start(String.format("%s %s", clazz.getSimpleName(), StringUtils.join(messages, " ")));
        if (threadLocal.get() == null) {
            threadLocal.set(new ArrayList<StopWatch>());
        }
        threadLocal.get().add(watch);
        return watch;
    }

    public static void stop(StopWatch watch) {
        if (watch == null || !watch.isRunning()) {
            return;
        }

        watch.stop();
//        logger.debug(String.format("耗时:%s ms , %s", watch.getTotalTimeMillis(),watch.getLastTaskName()));
    }

    public static String log() {
        if (threadLocal.get() == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (StopWatch watch : threadLocal.get()) {
            if (watch == null || watch.isRunning()) {
                continue;
            }

            builder.append(String.format("\r\n%s 耗时:%sms,",watch.getLastTaskName(), watch.getTotalTimeMillis()));
        }

        logger.debug(builder.toString());
        //清除
        threadLocal = new ThreadLocal<>();

        return builder.toString();

    }

}
