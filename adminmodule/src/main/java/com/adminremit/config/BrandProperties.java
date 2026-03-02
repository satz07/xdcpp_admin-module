package com.adminremit.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.brand")
public class BrandProperties {

    /**
     * Main logo (e.g. header logo), URL like /img/xdc-payments-logo.png
     */
    private String logo;

    /**
     * Favicon for light mode, URL like /img/xdc-primary-icon.png
     */
    private String favicon;

    /**
     * Favicon for dark mode, URL like /img/xdc-inverted-primary-icon.png
     */
    private String darkFavicon;

    /**
     * Apple touch icon (optional), URL like /img/xdc-primary-icon.png
     */
    private String appleTouchIcon;

    /**
     * Logo / image on the login screen.
     */
    private String loginImage;

    /**
     * Brand display name, e.g. "XDC Payments" or "Panion".
     */
    private String name;

    /**
     * Footer copyright text.
     */
    private String footerText;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getDarkFavicon() {
        return darkFavicon;
    }

    public void setDarkFavicon(String darkFavicon) {
        this.darkFavicon = darkFavicon;
    }

    public String getAppleTouchIcon() {
        return appleTouchIcon;
    }

    public void setAppleTouchIcon(String appleTouchIcon) {
        this.appleTouchIcon = appleTouchIcon;
    }

    public String getLoginImage() {
        return loginImage;
    }

    public void setLoginImage(String loginImage) {
        this.loginImage = loginImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }
}

