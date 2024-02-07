package com.sp.whatnow;

import java.util.HashMap;

public class ForumVolleyHelper {
    static String region = "<region>";
    static String url = region + "/v2/keyspaces/forumsdb/forums_table/";
    static String Cassandra_Token = "<token>";
    static int lastID = 0;
    static HashMap getHeader() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Cassandra-Token", Cassandra_Token);
        headers.put("Accept", "application/json");
        return headers;
    }
}
