package Classes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class clsLogger {
    private clsCommon objClsCommon = new clsCommon();
    private final String sTimeStamp = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
    private final Path currentRelativePath = Paths.get("");
    private final String sFilePathDir = currentRelativePath.toAbsolutePath().toString() + "/Log/";
    private final String sEQFileName = "EQ_" + sTimeStamp;
    private final String sDefaultEQFilePath = sFilePathDir + sEQFileName + "_00.txt";
    //File Size in 5MB = 5000KB
    private final int iFileSize = 5000;

    public void getLogger(String sMessageType, String sMessage, int iLogType, int iDebugLevel)
    {
        try{
            String sLogTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            String sFilePath = "";
            File[] files = null;
            File f = null;
            FileWriter fw = null;

            objClsCommon.chkCreateFolder(sFilePathDir);
            sFilePath = sFilePathDir + getAvailabelFileName(sDefaultEQFilePath);

            f = new File(sFilePath);
            fw = new FileWriter(f, true);
            fw.write( sLogTimeStamp + " [" + sMessageType +"]: " + sMessage + System.getProperty( "line.separator" ));
            fw.close();
            f = null;
        } catch (SecurityException | IOException ex){
            ex.printStackTrace();
        }
    }

    public String getAvailabelFileName(String sFilePath)
    {
        String sAvaFileName = "";
        Boolean isUsedFile = false;
        String sChkFilename = "";
        String sTmpFilename = "";
        String[] sArryTmp = null;
        int iTemp = 0;
        File[] files = null;
        List<String> sFileList = new ArrayList<>();

        File f = new File(sFilePath);
        sChkFilename = f.getName();
        sArryTmp = sChkFilename.split("\\.");
        sChkFilename = sArryTmp[0];
        sArryTmp = sChkFilename.split("\\_");

        for (int i = 0; i < sArryTmp.length-1; i++)
        {
            if (i == 0)
            {
                sChkFilename = sArryTmp[i];
            }else
            {
                sChkFilename += "_" + sArryTmp[i];
            }
        }

        if (getFileSize(f, 1) > iFileSize)
        {
            files = new File(sFilePathDir).listFiles();
            for(File file : files) {
                if( (file.getName().toLowerCase().indexOf(sChkFilename.toLowerCase()) != -1) && !(file.getName().toLowerCase().equals((sChkFilename + "_00.txt").toLowerCase()) ) )
                {
                    sFileList.add(file.getName());
                }
            }

            if (sFileList.size() > 0)
            {
                for (String sFileName : sFileList )
                {
                    f = new File(sFilePathDir + sFileName);
                    if (getFileSize(f, 1) < iFileSize)
                    {
                        isUsedFile = true;
                        sAvaFileName = sFileName;
                        break;
                    }
                }

                if (isUsedFile == false)
                {
                    sTmpFilename = f.getName();
                    sArryTmp = sTmpFilename.split("\\.");
                    sTmpFilename = sArryTmp[0];
                    sArryTmp = sTmpFilename.split("\\_");
                    sTmpFilename = sArryTmp[sArryTmp.length -1];
                    iTemp = Integer.parseInt(sTmpFilename) + 1;
                    sAvaFileName = sChkFilename + "_" + String.format("%02d", iTemp) +".txt";
                }
            }
            else
            {
                sAvaFileName += sChkFilename + "_01.txt";
            }
        }
        else
        {
            sAvaFileName += sChkFilename + "_00.txt";
        }

        return sAvaFileName;
    }

    public double getFileSize(File F, int iFileTypeSize) //Get File Size: 0=MB, 1=KB, Other:Byte
    {
        double dFileSize = 0;

        if (iFileTypeSize == 0)
        {
            dFileSize = (F.length() / (1024*1024));
        }
        else if(iFileTypeSize == 1)
        {
            dFileSize = (F.length() / 1024);
        }
        else {
            dFileSize = F.length();
        }

        return dFileSize;
    }
}


