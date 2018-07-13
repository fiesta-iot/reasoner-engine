package eu.fiestaiot.reasoner.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties specific to JBooter.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "fiesta", ignoreUnknownFields = false)
public class FiestaProperties {

    private final Async async = new Async();

    private final Http http = new Http();

    private final Cache cache = new Cache();

    private final Mail mail = new Mail();

    private final Security security = new Security();

    private final Swagger swagger = new Swagger();

    private final Metrics metrics = new Metrics();

    private final CorsConfiguration cors = new CorsConfiguration();

    private final Ribbon ribbon = new Ribbon();
    private final Enpoints enpoints = new Enpoints();

    public Enpoints getEnpoints() {
        return enpoints;
    }

    public Async getAsync() {
        return async;
    }

    public Http getHttp() {
        return http;
    }

    public Cache getCache() {
        return cache;
    }

    public Mail getMail() {
        return mail;
    }

    public Security getSecurity() {
        return security;
    }

    public Swagger getSwagger() {
        return swagger;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public CorsConfiguration getCors() {
        return cors;
    }

    public Ribbon getRibbon() {
        return ribbon;
    }
    public static class Enpoints {

        private String iotRegisterResourceUrl;
        private String iotRegisterTestbedUrl;
        private String iotRegisterTextUrl;
        private String ontologyValidatorResourceUrl;
        private String ontologyValidatrorObservationUrl;
        private String authenticateUrl;
        private String getUserInfoUrl;
        private String getUserIdUrl;
        private String ontologyM3LiteUrl;
        private String executeQueryBaseURL;
        private String platformBaseURL;


        public String getPlatformBaseURL() {
            return platformBaseURL;
        }

        public void setPlatformBaseURL(String platformBaseURL) {
            this.platformBaseURL = platformBaseURL;
        }

        public String getExecuteQueryBaseURL() {
            return executeQueryBaseURL;
        }

        public void setExecuteQueryBaseURL(String executeQueryBaseURL) {
            this.executeQueryBaseURL = executeQueryBaseURL;
        }

        public String getOntologyM3LiteUrl() {
            return ontologyM3LiteUrl;
        }

        public void setOntologyM3LiteUrl(String ontologyM3LiteUrl) {
            this.ontologyM3LiteUrl = ontologyM3LiteUrl;
        }

        public String getIotRegisterResourceUrl() {
            return iotRegisterResourceUrl;
        }

        public void setIotRegisterResourceUrl(String iotRegisterResourceUrl) {
            this.iotRegisterResourceUrl = iotRegisterResourceUrl;
        }

        public String getIotRegisterTestbedUrl() {
            return iotRegisterTestbedUrl;
        }

        public void setIotRegisterTestbedUrl(String iotRegisterTestbedUrl) {
            this.iotRegisterTestbedUrl = iotRegisterTestbedUrl;
        }

        public String getIotRegisterTextUrl() {
            return iotRegisterTextUrl;
        }

        public void setIotRegisterTextUrl(String iotRegisterTextUrl) {
            this.iotRegisterTextUrl = iotRegisterTextUrl;
        }

        public String getOntologyValidatorResourceUrl() {
            return ontologyValidatorResourceUrl;
        }

        public void setOntologyValidatorResourceUrl(String ontologyValidatorResourceUrl) {
            this.ontologyValidatorResourceUrl = ontologyValidatorResourceUrl;
        }

        public String getOntologyValidatrorObservationUrl() {
            return ontologyValidatrorObservationUrl;
        }

        public void setOntologyValidatrorObservationUrl(String ontologyValidatrorObservationUrl) {
            this.ontologyValidatrorObservationUrl = ontologyValidatrorObservationUrl;
        }

        public String getAuthenticateUrl() {
            return authenticateUrl;
        }

        public void setAuthenticateUrl(String authenticateUrl) {
            this.authenticateUrl = authenticateUrl;
        }

        public String getGetUserInfoUrl() {
            return getUserInfoUrl;
        }

        public void setGetUserInfoUrl(String getUserInfoUrl) {
            this.getUserInfoUrl = getUserInfoUrl;
        }

        public String getGetUserIdUrl() {
            return getUserIdUrl;
        }

        public void setGetUserIdUrl(String getUserIdUrl) {
            this.getUserIdUrl = getUserIdUrl;
        }

    }

    public static class Async {

        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }

    public static class Http {

        private final Cache cache = new Cache();

        public Cache getCache() {
            return cache;
        }

        public static class Cache {

            private int timeToLiveInDays = 1461;

            public int getTimeToLiveInDays() {
                return timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }

    public static class Cache {
    }

    public static class Mail {

        private String from = "FiestaReasoning@localhost";

        private String baseUrl = "";

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    public static class Security {

        private final Authentication authentication = new Authentication();

        public Authentication getAuthentication() {
            return authentication;
        }
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            public Jwt getJwt() {
                return jwt;
            }

            public static class Jwt {

                private String secret;

                private long tokenValidityInSeconds = 1800;

                private long tokenValidityInSecondsForRememberMe = 2592000;

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public long getTokenValidityInSeconds() {
                    return tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }
    }

    public static class Swagger {

        private String title = "Fiesta Reasoning API";

        private String description = "Fiesta Reasoning API documentation";

        private String version = "0.0.1";

        private String termsOfServiceUrl;

        private String contactName;

        private String contactUrl;

        private String contactEmail;

        private String license;

        private String licenseUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfServiceUrl() {
            return termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactUrl() {
            return contactUrl;
        }

        public void setContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
        }

        public String getContactEmail() {
            return contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getLicense() {
            return license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }
    }

    public static class Metrics {

        private final Jmx jmx = new Jmx();

        private final Graphite graphite = new Graphite();

        private final Prometheus prometheus = new Prometheus();

        private final Logs logs = new Logs();

        public Jmx getJmx() {
            return jmx;
        }

        public Graphite getGraphite() {
            return graphite;
        }

        public Prometheus getPrometheus() {
            return prometheus;
        }

        public Logs getLogs() {
            return logs;
        }

        public static class Jmx {

            private boolean enabled = true;

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }

        public static class Graphite {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 2003;

            private String prefix = "FiestaReasoning";

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public String getPrefix() {
                return prefix;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }
        }

        public static class Prometheus {

            private boolean enabled = false;

            private String endpoint = "/prometheusMetrics";

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getEndpoint() {
                return endpoint;
            }

            public void setEndpoint(String endpoint) {
                this.endpoint = endpoint;
            }
        }

        public static class Logs {

            private boolean enabled = false;

            private long reportFrequency = 60;

            public long getReportFrequency() {
                return reportFrequency;
            }

            public void setReportFrequency(int reportFrequency) {
                this.reportFrequency = reportFrequency;
            }

            public boolean isEnabled() {
                return enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }
    }

    private final Logging logging = new Logging();

    public Logging getLogging() { return logging; }

    public static class Logging {

        private final Logstash logstash = new Logstash();

        public Logstash getLogstash() { return logstash; }

        public static class Logstash {

            private boolean enabled = false;

            private String host = "localhost";

            private int port = 5000;

            private int queueSize = 512;

            public boolean isEnabled() { return enabled; }

            public void setEnabled(boolean enabled) { this.enabled = enabled; }

            public String getHost() { return host; }

            public void setHost(String host) { this.host = host; }

            public int getPort() { return port; }

            public void setPort(int port) { this.port = port; }

            public int getQueueSize() { return queueSize; }

            public void setQueueSize(int queueSize) { this.queueSize = queueSize; }
        }
    }

    public static class Ribbon {

        private String[] displayOnActiveProfiles;

        public String[] getDisplayOnActiveProfiles() {
            return displayOnActiveProfiles;
        }

        public void setDisplayOnActiveProfiles(String[] displayOnActiveProfiles) {
            this.displayOnActiveProfiles = displayOnActiveProfiles;
        }
    }
}
