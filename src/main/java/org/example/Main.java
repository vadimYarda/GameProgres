package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(100, 30, 5, 12.3);
        GameProgress gameProgress2 = new GameProgress(32, 15, 1, 3.4);
        GameProgress gameProgress3 = new GameProgress(87, 30, 7, 16.1);
        List<String> savePathList = Arrays.asList(
                "Games/savegames/save1.dat",
                "Games/savegames/save2.dat",
                "Games/savegames/save3.dat"
        );
        saveGame(savePathList.get(0), gameProgress1);
        saveGame(savePathList.get(1), gameProgress2);
        saveGame(savePathList.get(2), gameProgress3);
        zipFiles("Games/savegames/zip.zip", savePathList);

        for (String savePath : savePathList) {
            try {
                Files.delete(Path.of(savePath));
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
     public static void zipFiles(String filePath, List<String> savePathList) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(filePath))) {
            for (String savePath : savePathList) {
                try (FileInputStream fis = new FileInputStream(savePath)) {
                    String[] splitPath = savePath.split("/");
                    ZipEntry entry = new ZipEntry(splitPath[splitPath.length - 1]);
                    zipOutputStream.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zipOutputStream.write(buffer);
                    zipOutputStream.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }





    }

}