package com.boltie.backend.services;

import com.google.gson.Gson;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class OvenStreamKeyGenService {

    private final String SECRET_KEY = "aKq#1kj";
    private final String POLICY_NAME = "policy";
    private final String SIGNATURE_NAME = "signature";

    private final HmacUtils hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_1, SECRET_KEY);
    private final Gson gson = new Gson();
    private final Base64.Encoder bEncode = java.util.Base64.getEncoder();

    public String generate(String rtmpUrl, Long url_expire, Long url_activate, Long stream_expire, String allow_ip) {
        Map<String, Object> policyMap = new HashMap<String, Object>();
        policyMap.put("url_expire", url_expire);

        if (url_activate != null)
            policyMap.put("url_activate", url_activate);
        if (stream_expire != null)
            policyMap.put("stream_expire", stream_expire);
        if (StringUtils.isNotBlank(allow_ip))
            policyMap.put("allow_ip", allow_ip);

        String policy = gson.toJson(policyMap);
        String encodePolicy = encode(policy);

        String streamUrl = MessageFormat.format("{0}?{1}={2}", rtmpUrl, POLICY_NAME, encodePolicy);
        String signature = makeDigest(streamUrl);

        return MessageFormat.format("{0}&{1}={2}", streamUrl, SIGNATURE_NAME, signature);
    }

    public String encode(String message) {
        return encode(message.getBytes(StandardCharsets.UTF_8));
    }

    private String encode(byte[] message) {
        String enc = bEncode.encodeToString(message);
        return StringUtils.stripEnd(enc, "=")
                .replaceAll("\\+", "-")
                .replaceAll("/", "_");
    }

    private String makeDigest(String message) {
        return encode(hmacUtils.hmac(message));
    }

}
