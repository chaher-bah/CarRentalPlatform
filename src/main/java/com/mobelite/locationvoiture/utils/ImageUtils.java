package com.mobelite.locationvoiture.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtils {

    public static byte[] compressImage(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);
        }
        try {
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4 * 1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception ignored) {
        }
        return outputStream.toByteArray();
    }

    public static List<byte[]> compressImages(List<byte[]> dataList) {
        List<byte[]> compressedList = new ArrayList<>();
        for (byte[] data : dataList) {
            compressedList.add(compressImage(data));
        }
        return compressedList;
    }

    public static List<byte[]> decompressImages(List<byte[]> dataList) {
        List<byte[]> decompressedList = new ArrayList<>();
        for (byte[] data : dataList) {
            decompressedList.add(decompressImage(data));
        }
        return decompressedList;
    }
    public static BufferedImage byteArrayToImage(byte[] imageData) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
            return ImageIO.read(bis);
        } catch (IOException e) {
            // Handle exception
            e.printStackTrace();
            return null;
        }
    }
}
