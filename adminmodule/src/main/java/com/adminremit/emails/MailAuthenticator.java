package com.adminremit.emails;

import org.apache.commons.lang3.StringUtils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

final class MailAuthenticator extends Authenticator {
    final private String username;
    final private String password;

    private MailAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(username, password);
    }

    public  static MailAuthenticator getInstance(final String username, final String password){
        if(StringUtils.isBlank(username)){
            throw new IllegalArgumentException("Username can not be blank");
        }
        if(StringUtils.isBlank(password)){
            throw new IllegalArgumentException("Password can not be blank");
        }
        return new MailAuthenticator(username, password);
    }
}
