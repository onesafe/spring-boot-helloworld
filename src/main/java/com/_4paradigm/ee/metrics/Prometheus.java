package com._4paradigm.ee.metrics;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Summary;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wangyiping on 25/09/2018.
 */
@Slf4j
public class Prometheus {
    private static List<String> pasHealthLabels = Arrays.asList("pas_namespace", "pas_pod", "pas_id", "pas_deployment");
    private static List<String> pasQueryLatency = Arrays.asList("pas_namespace", "pas_pod", "pas_id", "pas_deployment");
    private static List<String> pasQueryCountLabels = Arrays.asList("pas_namespace", "pas_pod", "pas_id", "pas_deployment", "http_code");


    public static final Counter PAS_QUERY_COUNT = Counter.build()
            .name("pas_query_count")
            .help("pas query count")
            .labelNames(pasQueryCountLabels.toArray(new String[0]))
            .register();

    public static final Gauge PAS_HEALTH = Gauge.build()
            .name("pas_health")
            .help("pas health")
            .labelNames(pasHealthLabels.toArray(new String[0]))
            .register();

    public static final Summary PAS_QUERY_LATENCY = Summary.build()
            .quantile(0.5, 0.05)
            .quantile(0.9, 0.01)
            .quantile(0.99, 0.001)
            .quantile(0.999, 0.0001)
            .name("pas_query_latency")
            .help("pas query latency")
            .labelNames(pasQueryLatency.toArray(new String[0]))
            .register();

    public static String getNamespace() throws Exception {
        String namespace = System.getenv("POD_META_NAMESPACE");
        if (namespace == null) {
            return "$POD_META_NAMESPACE";
        }
        return namespace;
    }

    public static String getPod() throws Exception {
        String hostname = InetAddress.getLocalHost().getHostName();
        if (hostname == null) {
            return "$MY_HOSTNAME";
        }
        return hostname;
    }

    public static String getPasId() {
        String pasID = System.getenv("PROPHET_PAS_ID");
        if (pasID == null) {
            return "$PROPHET_PAS_ID";
        }
        return pasID;
    }

    public static String getDeployment() {
        String pasDeployment = System.getenv("PROPHET_PAS_DEPLOYMENT_NAME");
        if (pasDeployment == null) {
            return "$PROPHET_PAS_DEPLOYMENT_NAME";
        }
        return pasDeployment;
    }
}
