package utils;

import lombok.Getter;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.http.message.StatusLine;

import java.io.IOException;
import java.util.*;

public class ClientWrapper {
    private String url;
    public ClientWrapper(String url) {
        this.url = url;
    }

    @Getter
    public static class Result {
        final int status;
        final String content;

        Result(final int status, final String content) {
            this.status = status;
            this.content = content;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "status=" + status +
                    ", content='" + content + '\'' +
                    '}';
        }
    }

    private String buildUrl(String endpoint, Map<String, String> params) {
        if (params == null || params.isEmpty() || params.size() == 0) {
            return endpoint;
        }

        String paramsStr = params.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((a, b) -> a + "&" + b)
                .orElse("");
        System.out.println(endpoint + "?" + paramsStr);
        return endpoint + "?" + paramsStr;
    }

    private UrlEncodedFormEntity buildParamsBodyReq(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return new UrlEncodedFormEntity(nameValuePairs);
    }

    public Result get() {
        return this.get(new HashMap<>());
    }

    public Result get(Map<String, String> params) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            String fullUrl = buildUrl(this.url, params);
            System.out.println("-" + fullUrl);
            HttpGet httpGet = new HttpGet(fullUrl);
            System.out.println("Sending get req to " + httpGet.getPath());
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                StatusLine statusLine = new StatusLine(response);
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity);
                return new Result(statusLine.getStatusCode(), body);
            } catch (ParseException e) {
                System.out.println("Failed executing");
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            System.out.println("Failed init HttpGet");
            e.printStackTrace();
        }
        return null;
    }

    public Result post() {
        return this.post(new HashMap<>());
    }

    public Result post(Map<String, String> params) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(this.url);
            httpPost.setEntity(buildParamsBodyReq(params));
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                StatusLine statusLine = new StatusLine(response);
                HttpEntity entity = response.getEntity();
                String body = EntityUtils.toString(entity);
                return new Result(statusLine.getStatusCode(), body);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Wrapper on the way!");
    }
}