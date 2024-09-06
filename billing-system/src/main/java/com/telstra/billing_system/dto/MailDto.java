package com.telstra.billing_system.dto;

import lombok.Data;

@Data
public class MailDto {

    private String to;
    private String subject;
    private String text;
}