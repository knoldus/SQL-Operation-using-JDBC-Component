package com.camel;

import java.util.List;

public class QueryResultHandler {

    public void printResult(List list) {

        for (int i = 0; i< list.size(); i++) {
            System.out.println(list.get(i));
        }

    }


}