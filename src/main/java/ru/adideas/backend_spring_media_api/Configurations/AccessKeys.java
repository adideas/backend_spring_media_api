package ru.adideas.backend_spring_media_api.Configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@ConfigurationProperties("jwt.secret")
public class AccessKeys {
    @Value("${jwt.secret.access}")
    private String access;

    @Value("${jwt.secret.refresh}")
    private String refresh;

    public SecretKey getAccessKey() {
        return new SecretKeySpec(this.getAccessBinary(), "HmacSHA256");
    }

    public byte[] getAccessBinary() {
        return Base64.getDecoder().decode(this.getAccess());
    }

    public String getAccess() {
        return access;
    }

    public SecretKey getRefreshKey() {
        return new SecretKeySpec(this.getRefreshBinary(), "HmacSHA256");
    }

    public byte[] getRefreshBinary() {
        return Base64.getDecoder().decode(this.getRefresh());
    }

    public String getRefresh() {
        return refresh;
    }
}
