package com.ameschot.olibmmqtest.service;

import lombok.Getter;

import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class AccountService {
    @Getter
    private final Map<String, Float> accounts = new HashMap<>();

    public Float processAccount(String inAccount, Float inAmount){
        return accounts.compute(inAccount, (account, sum) -> sum == null ? inAmount : sum + inAmount);
    }
}
