package com.capgemini.expense;

import javax.servlet.http.HttpServletResponse;

public class ServiceUtils {
    public static void prepareOptionsResponse(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }

    public static void prepareResponse(HttpServletResponse res) {
        res.setHeader("Access-Control-Allow-Origin", "*");
    }
}
