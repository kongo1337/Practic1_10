/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.pracrica1_10;

/**
 *
 * @author KonGo
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Pracrica1_10 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Введите путь к исходному файлу: ");
        String inputFilePath = scanner.nextLine();

        
        System.out.print("Введите байт гаммы в формате Hex-string: ");
        String gammaHexString = scanner.nextLine();

        try {
            
            byte[] gammaBytes = hexStringToByteArray(gammaHexString);

            
            FileInputStream inputStream = new FileInputStream(inputFilePath);
            
            
            String outputFilePath = generateOutputFilePath(inputFilePath);
            FileOutputStream outputStream = new FileOutputStream(outputFilePath);

            int data;
            int gammaIndex = 0;
            while ((data = inputStream.read()) != -1) {
                
                int modifiedData = data ^ gammaBytes[gammaIndex % gammaBytes.length];
                outputStream.write(modifiedData);
                gammaIndex++;
            }

            
            inputStream.close();
            outputStream.close();

            System.out.println("Файл успешно модифицирован и сохранен в " + outputFilePath);
        } catch (IOException e) {
            System.out.println("Произошла ошибка при обработке файла: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    
    public static byte[] hexStringToByteArray(String s) throws IllegalArgumentException {
        int len = s.length();
        if (len % 2 == 1) {
            throw new IllegalArgumentException("Некорректный формат Hex-string");
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    
    public static String generateOutputFilePath(String inputFilePath) {
        int lastIndex = inputFilePath.lastIndexOf('.');
        String extension = "";
        if (lastIndex != -1) {
            extension = inputFilePath.substring(lastIndex);
        }
        return inputFilePath.replaceFirst("[.][^.]+$", "_modified" + extension);
    }
}
    

