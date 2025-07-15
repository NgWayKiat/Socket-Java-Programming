package Classes;

import java.io.File;
import java.io.IOException;

public class clsCommon {
    public void chkCreateFolder(String sFilePathDir)
    {
        //Folder Path
        File dir = new File(sFilePathDir);

        //Check Folder Creation
        if(!dir.isDirectory())
        {
            dir.mkdirs();
        }
    }
}
