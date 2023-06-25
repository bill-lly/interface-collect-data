package com.robot.gs.ticket.http.udesk.util;

import com.google.common.base.Preconditions;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class EncryptUtil {
  private static final String DefaultAlgorithm = "sha1";
  private static final String interval = "&";

  private EncryptUtil() {
  }

  public static final String encrypt(final String email, final String apiToken,
                                     final Long timestamp, final String algorithm) {
    Preconditions.checkArgument(10 == String.valueOf(timestamp).length(),
        "timestamp isn't true");
    if (DefaultAlgorithm.equals(algorithm)) {
      return encryptSha1(
          intervalStr(Arrays.asList(email, apiToken, String.valueOf(timestamp))));
    }
    return null;
  }

  public static final String encrypt(final String email, final String apiToken,
                                     final Long timestamp) {
    return encrypt(email, apiToken, timestamp, DefaultAlgorithm);
  }

  public static final String encryptSha256(String input) {
    return Hashing.sha256()
        .hashString(input, StandardCharsets.UTF_8)
        .toString();
  }

  public static final String encryptSha1(String input) {
    return Hashing.sha1()
        .hashString(input, StandardCharsets.UTF_8)
        .toString();
  }

  public static final String intervalStr(List joinList) {
    return String.join(
        interval, joinList);
  }

  public static final List<String> concatMap(Map<String, String> paramMap) {
    return paramMap.entrySet().stream().map(e -> {
      return e.getKey() + "=" + e.getValue();
    }).collect(Collectors.toList());
  }


}
