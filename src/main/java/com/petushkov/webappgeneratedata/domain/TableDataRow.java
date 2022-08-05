package com.petushkov.webappgeneratedata.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableDataRow {

    private long index;

    private String identifier;

    private String fullName;

    private String address;

    private String phone;

//    private List<Long> indexes = new ArrayList<>();
//
//    private List<Long> identifiers = new ArrayList<>();
//
//    private List<String> fullNames = new ArrayList<>();
//
//    private List<String> addresses = new ArrayList<>();
//
//    private List<String> phones = new ArrayList<>();

//    public void addIndex(Long index) {
//        this.indexes.add(index);
//    }
//
//    public void addIdentifier(Long identifier) {
//        this.identifiers.add(identifier);
//    }
//
//    public void addFullName(String fullName) {
//        this.fullNames.add(fullName);
//    }
//
//    public void addAddress(String address) {
//        this.addresses.add(address);
//    }
//
//    public void addPhone(String phone) {
//        this.phones.add(phone);
//    }
}
