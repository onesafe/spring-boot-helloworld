package com._4paradigm.ee;

import com._4paradigm.ee.config.Constants;
import com._4paradigm.ee.metrics.Prometheus;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.common.TextFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;


@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() throws Exception {
        Summary.Timer requestTimer = Prometheus.PAS_QUERY_LATENCY.labels(
                Prometheus.getNamespace(),
                Prometheus.getPod(),
                Prometheus.getPasId(),
                Prometheus.getDeployment()
        ).startTimer();

        Thread.sleep(100);

        Prometheus.PAS_QUERY_COUNT.labels(
                Prometheus.getNamespace(),
                Prometheus.getPod(),
                Prometheus.getPasId(),
                Prometheus.getDeployment(),
                String.valueOf("200")).inc();


        requestTimer.observeDuration();
        return "hello yiping_2 | " + Constants.name;
    }

    //用fastjson出现中文乱码的话，记得加produces = "application/json; charset=utf-8"
    @RequestMapping(value = "/getDemo", produces = "application/json; charset=utf-8")
    public Demo getDemo(){
        Demo demo = new Demo();
        demo.setId(1000);
        demo.setName("张三和李四and王五");
        demo.setCreateTime(new Date());
        demo.setRemarks("这是一个备注信息");
        return demo;
    }

    @RequestMapping("/api/metrics")
    public String metrics() throws Exception {
        CollectorRegistry registry =  CollectorRegistry.defaultRegistry;
        Writer writer = new StringWriter();
        TextFormat.write004(writer, registry.metricFamilySamples());
        return writer.toString();
    }
}
