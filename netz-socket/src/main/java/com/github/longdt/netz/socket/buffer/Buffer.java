package com.github.longdt.netz.socket.buffer;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public interface Buffer {

    /**
     * Returns a {@code String} representation of the Buffer with the {@code UTF-8 }encoding
     */
    String toString();

    /**
     * Returns a {@code String} representation of the Buffer with the encoding specified by {@code enc}
     */
    String toString(String enc);

    /**
     * Returns a {@code String} representation of the Buffer with the encoding specified by {@code enc}
     */
    String toString(Charset enc);

    /**
     * Returns the {@code byte} at position {@code pos} in the Buffer.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 1} is greater than the length of the Buffer.
     */
    byte getByte(int pos);

    /**
     * Returns the unsigned {@code byte} at position {@code pos} in the Buffer, as a {@code short}.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 1} is greater than the length of the Buffer.
     */
    short getUnsignedByte(int pos);

    /**
     * Returns the {@code int} at position {@code pos} in the Buffer.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 4} is greater than the length of the Buffer.
     */
    int getInt(int pos);

    /**
     * Gets a 32-bit integer at the specified absolute {@code index} in this buffer with Little Endian Byte Order.
     *
     * @throws IndexOutOfBoundsException if the specified {@code index} is less than {@code 0} or {@code index + 4} is greater than {@code this.capacity}
     */
    int getIntLE(int pos);

    /**
     * Returns the unsigned {@code int} at position {@code pos} in the Buffer, as a {@code long}.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 4} is greater than the length of the Buffer.
     */
    long getUnsignedInt(int pos);

    /**
     * Returns the unsigned {@code int} at position {@code pos} in the Buffer, as a {@code long} in Little Endian Byte Order.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 4} is greater than the length of the Buffer.
     */
    long getUnsignedIntLE(int pos);

    /**
     * Returns the {@code long} at position {@code pos} in the Buffer.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 8} is greater than the length of the Buffer.
     */
    long getLong(int pos);

    /**
     * Gets a 64-bit long integer at the specified absolute {@code index} in this buffer in Little Endian Byte Order.
     *
     * @throws IndexOutOfBoundsException if the specified {@code index} is less than {@code 0} or {@code index + 8} is greater than the length of the Buffer.
     */
    long getLongLE(int pos);

    /**
     * Returns the {@code double} at position {@code pos} in the Buffer.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 8} is greater than the length of the Buffer.
     */
    double getDouble(int pos);

    /**
     * Returns the {@code float} at position {@code pos} in the Buffer.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 4} is greater than the length of the Buffer.
     */
    float getFloat(int pos);

    /**
     * Returns the {@code short} at position {@code pos} in the Buffer.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 2} is greater than the length of the Buffer.
     */
    short getShort(int pos);

    /**
     * Gets a 16-bit short integer at the specified absolute {@code index} in this buffer in Little Endian Byte Order.
     *
     * @throws IndexOutOfBoundsException if the specified {@code index} is less than {@code 0} or {@code index + 2} is greater than the length of the Buffer.
     */
    short getShortLE(int pos);

    /**
     * Returns the unsigned {@code short} at position {@code pos} in the Buffer, as an {@code int}.
     *
     * @throws IndexOutOfBoundsException if the specified {@code pos} is less than {@code 0} or {@code pos + 2} is greater than the length of the Buffer.
     */
    int getUnsignedShort(int pos);

    /**
     * Gets an unsigned 16-bit short integer at the specified absolute {@code index} in this buffer in Little Endian Byte Order.
     *
     * @throws IndexOutOfBoundsException if the specified {@code index} is less than {@code 0} or {@code index + 2} is greater than the length of the Buffer.
     */
    int getUnsignedShortLE(int pos);

    /**
     * Gets a 24-bit medium integer at the specified absolute {@code index} in this buffer.
     *
     * @throws IndexOutOfBoundsException if the specified {@code index} is less than {@code 0} or {@code index + 3} is greater than the length of the Buffer.
     */
    int getMedium(int pos);

    /**
     * Gets a 24-bit medium integer at the specified absolute {@code index} in this buffer in the Little Endian Byte Order.
     *
     * @throws IndexOutOfBoundsException if the specified {@code index} is less than {@code 0} or {@code index + 3} is greater than the length of the Buffer.
     */
    int getMediumLE(int pos);

    /**
     * Gets an unsigned 24-bit medium integer at the specified absolute {@code index} in this buffer.
     *
     * @throws IndexOutOfBoundsException if the specified {@code index} is less than {@code 0} or {@code index + 3} is greater than the length of the Buffer.
     */
    int getUnsignedMedium(int pos);

    /**
     * Gets an unsigned 24-bit medium integer at the specified absolute {@code index} in this buffer in Little Endian Byte Order.
     *
     * @throws IndexOutOfBoundsException if the specified {@code index} is less than {@code 0} or {@code index + 3} is greater than the length of the Buffer.
     */
    int getUnsignedMediumLE(int pos);

    /**
     * Returns a copy of the entire Buffer as a {@code byte[]}
     */
    byte[] getBytes();

    /**
     * Returns a copy of a sub-sequence the Buffer as a {@code byte[]} starting at position {@code start}
     * and ending at position {@code end - 1}
     */
    byte[] getBytes(int start, int end);

    /**
     * Transfers the content of the Buffer into a {@code byte[]}.
     *
     * @param dst the destination byte array
     * @throws IndexOutOfBoundsException if the content of the Buffer cannot fit into the destination byte array
     */
    Buffer getBytes(byte[] dst);

    /**
     * Transfers the content of the Buffer into a {@code byte[]} at the specific destination.
     *
     * @param dst the destination byte array
     * @throws IndexOutOfBoundsException if the content of the Buffer cannot fit into the destination byte array
     */
    Buffer getBytes(byte[] dst, int dstIndex);

    /**
     * Transfers the content of the Buffer starting at position {@code start} and ending at position {@code end - 1}
     * into a {@code byte[]}.
     *
     * @param dst the destination byte array
     * @throws IndexOutOfBoundsException if the content of the Buffer cannot fit into the destination byte array
     */
    Buffer getBytes(int start, int end, byte[] dst);

    /**
     * Transfers the content of the Buffer starting at position {@code start} and ending at position {@code end - 1}
     * into a {@code byte[]} at the specific destination.
     *
     * @param dst the destination byte array
     * @throws IndexOutOfBoundsException if the content of the Buffer cannot fit into the destination byte array
     */
    Buffer getBytes(int start, int end, byte[] dst, int dstIndex);

    /**
     * Returns a copy of a sub-sequence the Buffer as a {@link Buffer} starting at position {@code start}
     * and ending at position {@code end - 1}
     */
    Buffer getBuffer(int start, int end);

    /**
     * Returns a copy of a sub-sequence the Buffer as a {@code String} starting at position {@code start}
     * and ending at position {@code end - 1} interpreted as a String in the specified encoding
     */
    String getString(int start, int end, String enc);

    /**
     * Returns a copy of a sub-sequence the Buffer as a {@code String} starting at position {@code start}
     * and ending at position {@code end - 1} interpreted as a String in UTF-8 encoding
     */
    String getString(int start, int end);

    /**
     * Appends the specified {@code Buffer} to the end of this Buffer. The buffer will expand as necessary to accommodate
     * any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendBuffer(Buffer buff);

    /**
     * Appends the specified {@code Buffer} starting at the {@code offset} using {@code len} to the end of this Buffer. The buffer will expand as necessary to accommodate
     * any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendBuffer(Buffer buff, int offset, int len);

    /**
     * Appends the specified {@code byte[]} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendBytes(byte[] bytes);

    /**
     * Appends the specified number of bytes from {@code byte[]} to the end of the Buffer, starting at the given offset.
     * The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendBytes(byte[] bytes, int offset, int len);

    /**
     * Appends the specified {@code byte} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendByte(byte b);

    /**
     * Appends the specified unsigned {@code byte} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendUnsignedByte(short b);

    /**
     * Appends the specified {@code int} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendInt(int i);

    /**
     * Appends the specified {@code int} to the end of the Buffer in the Little Endian Byte Order. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendIntLE(int i);

    /**
     * Appends the specified unsigned {@code int} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendUnsignedInt(long i);

    /**
     * Appends the specified unsigned {@code int} to the end of the Buffer in the Little Endian Byte Order. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendUnsignedIntLE(long i);

    /**
     * Appends the specified 24bit {@code int} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendMedium(int i);

    /**
     * Appends the specified 24bit {@code int} to the end of the Buffer in the Little Endian Byte Order. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendMediumLE(int i);

    /**
     * Appends the specified {@code long} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendLong(long l);

    /**
     * Appends the specified {@code long} to the end of the Buffer in the Little Endian Byte Order. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendLongLE(long l);

    /**
     * Appends the specified {@code short} to the end of the Buffer.The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendShort(short s);

    /**
     * Appends the specified {@code short} to the end of the Buffer in the Little Endian Byte Order.The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendShortLE(short s);

    /**
     * Appends the specified unsigned {@code short} to the end of the Buffer.The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendUnsignedShort(int s);

    /**
     * Appends the specified unsigned {@code short} to the end of the Buffer in the Little Endian Byte Order.The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendUnsignedShortLE(int s);

    /**
     * Appends the specified {@code float} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendFloat(float f);

    /**
     * Appends the specified {@code double} to the end of the Buffer. The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.
     */
    Buffer appendDouble(double d);

    /**
     * Appends the specified {@code String} to the end of the Buffer with the encoding as specified by {@code enc}.<p>
     * The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together.<p>
     */
    Buffer appendString(String str, String enc);

    /**
     * Appends the specified {@code String str} to the end of the Buffer with UTF-8 encoding.<p>
     * The buffer will expand as necessary to accommodate any bytes written.<p>
     * Returns a reference to {@code this} so multiple operations can be appended together<p>
     */
    Buffer appendString(String str);

    /**
     * Sets the {@code byte} at position {@code pos} in the Buffer to the value {@code b}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setByte(int pos, byte b);

    /**
     * Sets the unsigned {@code byte} at position {@code pos} in the Buffer to the value {@code b}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setUnsignedByte(int pos, short b);

    /**
     * Sets the {@code int} at position {@code pos} in the Buffer to the value {@code i}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setInt(int pos, int i);

    /**
     * Sets the {@code int} at position {@code pos} in the Buffer to the value {@code i} in the Little Endian Byte Order.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setIntLE(int pos, int i);

    /**
     * Sets the unsigned {@code int} at position {@code pos} in the Buffer to the value {@code i}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setUnsignedInt(int pos, long i);

    /**
     * Sets the unsigned {@code int} at position {@code pos} in the Buffer to the value {@code i} in the Little Endian Byte Order.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setUnsignedIntLE(int pos, long i);

    /**
     * Sets the 24bit {@code int} at position {@code pos} in the Buffer to the value {@code i}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setMedium(int pos, int i);

    /**
     * Sets the 24bit {@code int} at position {@code pos} in the Buffer to the value {@code i}. in the Little Endian Byte Order<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setMediumLE(int pos, int i);

    /**
     * Sets the {@code long} at position {@code pos} in the Buffer to the value {@code l}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setLong(int pos, long l);

    /**
     * Sets the {@code long} at position {@code pos} in the Buffer to the value {@code l} in the Little Endian Byte Order.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setLongLE(int pos, long l);

    /**
     * Sets the {@code double} at position {@code pos} in the Buffer to the value {@code d}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setDouble(int pos, double d);

    /**
     * Sets the {@code float} at position {@code pos} in the Buffer to the value {@code f}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setFloat(int pos, float f);

    /**
     * Sets the {@code short} at position {@code pos} in the Buffer to the value {@code s}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setShort(int pos, short s);

    /**
     * Sets the {@code short} at position {@code pos} in the Buffer to the value {@code s} in the Little Endian Byte Order.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setShortLE(int pos, short s);

    /**
     * Sets the unsigned {@code short} at position {@code pos} in the Buffer to the value {@code s}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setUnsignedShort(int pos, int s);

    /**
     * Sets the unsigned {@code short} at position {@code pos} in the Buffer to the value {@code s} in the Little Endian Byte Order.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setUnsignedShortLE(int pos, int s);

    /**
     * Sets the bytes at position {@code pos} in the Buffer to the bytes represented by the {@code Buffer b}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setBuffer(int pos, Buffer b);

    /**
     * Sets the bytes at position {@code pos} in the Buffer to the bytes represented by the {@code Buffer b} on the given {@code offset} and {@code len}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setBuffer(int pos, Buffer b, int offset, int len);

    /**
     * Sets the bytes at position {@code pos} in the Buffer to the bytes represented by the {@code ByteBuffer b}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setBytes(int pos, ByteBuffer b);

    /**
     * Sets the bytes at position {@code pos} in the Buffer to the bytes represented by the {@code byte[] b}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setBytes(int pos, byte[] b);

    /**
     * Sets the given number of bytes at position {@code pos} in the Buffer to the bytes represented by the {@code byte[] b}.<p></p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setBytes(int pos, byte[] b, int offset, int len);

    /**
     * Sets the bytes at position {@code pos} in the Buffer to the value of {@code str} encoded in UTF-8.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setString(int pos, String str);

    /**
     * Sets the bytes at position {@code pos} in the Buffer to the value of {@code str} encoded in encoding {@code enc}.<p>
     * The buffer will expand as necessary to accommodate any value written.
     */
    Buffer setString(int pos, String str, String enc);

    /**
     * Returns the length of the buffer, measured in bytes.
     * All positions are indexed from zero.
     */
    int length();

    /**
     * Returns a copy of the entire Buffer.
     */
    Buffer copy();

    /**
     * Returns a slice of this buffer. Modifying the content
     * of the returned buffer or this buffer affects each other's content
     * while they maintain separate indexes and marks.
     */
    Buffer slice();

    /**
     * Returns a slice of this buffer. Modifying the content
     * of the returned buffer or this buffer affects each other's content
     * while they maintain separate indexes and marks.
     */
    Buffer slice(int start, int end);

    /**
     * Returns the Buffer as a Netty {@code ByteBuf}.
     *
     * <p> The returned buffer is a duplicate that maintain its own indices.
     */
    ByteBuffer[] getByteBuffers();
}
