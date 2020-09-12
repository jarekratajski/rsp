package rsp.server;

import rsp.util.Tuple2;

import java.util.List;

public class HttpResponse {
    public final int status;
    public final List<Tuple2<String,String>> headers;
    public final String body;

    public HttpResponse(int status,
                        List<Tuple2<String,String>> headers,
                        String body) {
        this.status = status;
        this.headers = headers;
        this.body = body;
    }

    public static class Headers {
        public static Tuple2<String,String> setCookie(String name, String value, String path, int maxAge ) {
            return new Tuple2<>("Set-Cookie",
                                String.format("%s=%s; Path=%s; Max-Age=%d; SameSite=Lax",
                                              name, value, path, maxAge ));
        }
    }
}
