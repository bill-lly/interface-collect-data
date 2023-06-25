package com.robot.gs.ticket.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class UuidUtil {

  /**
   * Convert uuid string to binary.
   *
   * @param str uuid string
   * @return uuid bin
   */
  public static byte[] toBinary(String str) {
    UUID uuid = UUID.fromString(str);
    long msb = uuid.getMostSignificantBits();
    msb = msb << 48
          | ((msb >> 16) & 0xFFFFL) << 32
          | msb >>> 32;

    byte[] bytes = new byte[16];
    ByteBuffer.wrap(bytes)
        .order(ByteOrder.BIG_ENDIAN)
        .putLong(msb)
        .putLong(uuid.getLeastSignificantBits());
    return bytes;
  }

  /**
   * Convert uuid bin to string.
   *
   * @param bytes uuid bin
   * @return uuid string
   */
  public static String toString(byte[] bytes) {
    ByteBuffer buf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN);
    long msb = buf.getLong();
    msb = msb << 32
          | ((msb >> 32) & 0xFFFFL) << 16
          | msb >>> 48;
    long lsb = buf.getLong();
    return new UUID(msb, lsb).toString();
  }
}
