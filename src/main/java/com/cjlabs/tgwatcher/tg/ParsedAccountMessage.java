package com.cjlabs.tgwatcher.tg;

public record ParsedAccountMessage(
        String phone,
        String accountNo,
        String accountName,
        String passwordPlain,
        String remark
) {
}
