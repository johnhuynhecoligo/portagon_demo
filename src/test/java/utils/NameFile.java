package utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class NameFile {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM_HH:mm");
    public String setPhotoName(String fileName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String filename = "";
        // result.getName() get name of scenario then set into name of screenshot file
        filename = "screenshot/" + fileName + "_" + timeStamp + ".png";
        return filename;
    }

    public String setFileName(String fileName) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        String filename = "";
        // result.getName() get name of scenario then set into name of screenshot file
        filename = fileName+"_"+timeStamp+".zip";
        return filename;
    }
}
