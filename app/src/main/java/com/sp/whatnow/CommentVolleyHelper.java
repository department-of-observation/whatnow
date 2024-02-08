package com.sp.whatnow;

import java.util.HashMap;

public class CommentVolleyHelper {
    static String region = "https://e1b1a278-51de-4e7f-92a7-706c145bdfb0-us-east1.apps.astra.datastax.com/api/rest";
    static String url = region + "/v2/keyspaces/forumsdb/forum_comments/";
    static String Cassandra_Token = "AstraCS:yLcZjsUmbjQlxBamCayBIdMK:8f2f0b3bf03e59d97542787e5988a138a85816945fe4a77350597e2c0cc52758";
    static HashMap getHeader() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Cassandra-Token", Cassandra_Token);
        headers.put("Accept", "application/json");
        return headers;
    }
}