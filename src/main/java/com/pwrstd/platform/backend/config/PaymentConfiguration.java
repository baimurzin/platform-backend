package com.pwrstd.platform.backend.config;

import com.braintreegateway.BraintreeGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

@Configuration
public class PaymentConfiguration {

    private final static Logger log = LoggerFactory.getLogger(PaymentConfiguration.class);


    private final BraintreeConfig braintreeConfig;

    public PaymentConfiguration(BraintreeConfig braintreeConfig) {
        this.braintreeConfig = braintreeConfig;
    }


    @Bean
    public BraintreeGateway braintreeGateway() {
        Map<String, String> mapping = System.getenv();
        BraintreeGateway braintreeGateway = null;

        try {
            braintreeGateway = new BraintreeGateway(
                    mapping.get("BT_ENVIRONMENT"),
                    mapping.get("BT_MERCHANT_ID"),
                    mapping.get("BT_PUBLIC_KEY"),
                    mapping.get("BT_PRIVATE_KEY")
            );
        } catch (NullPointerException e) {
            if (braintreeConfig.atLeastOneNull()) {
                log.warn("Could not load Braintree configuration from config file or system environment.");
                throw new BraintreeGatewayInitializationException();
            }
            braintreeGateway = new BraintreeGateway(
                    braintreeConfig.getEnvironment(),
                    braintreeConfig.getMerchantId(),
                    braintreeConfig.getPublicKey(),
                    braintreeConfig.getPrivateKey()
            );
        }

        return braintreeGateway;
    }

    @Configuration
    @PropertySource("classpath:braintree.config.properties")
    @ConfigurationProperties(prefix = "braintree")
    public static class BraintreeConfig {
        private String environment;
        private String merchantId;
        private String publicKey;
        private String privateKey;


        public boolean atLeastOneNull() {
            return Stream.of(environment, merchantId, publicKey, privateKey)
                    .anyMatch(Objects::isNull);
        }

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }

    static class BraintreeGatewayInitializationException extends RuntimeException {

        public BraintreeGatewayInitializationException() {
            super("Cannot initialize payment provider Braintree. No configuration found.");
        }
    }
}
