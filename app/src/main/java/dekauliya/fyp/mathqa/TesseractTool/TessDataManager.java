package dekauliya.fyp.mathqa.TesseractTool;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by dekauliya on 25/9/16.
 */

public class TessDataManager {
    private static final String TAG = "TESS_DATA_MANAGER";
    private static final String TESSDATA_DIR = "tessdata";
    private static boolean initialized;
    private static Context mContext;
    private static File mTessdataPath;
    private static File mDataPath;

    public static String getDataPath(){
        return mDataPath.toString();
    }

    public static void initTessTrainedData(Context context) {
        if (initialized){
            return;
        }
        mContext = context;

        mDataPath = getStorageDir();
        mTessdataPath = prepareTessdataDirectory();

        copyTessDataFiles(TESSDATA_DIR);
    }


    private static File prepareTessdataDirectory() {
        File tessDataDir = new File(mDataPath, TESSDATA_DIR);
        Logger.d(TAG, tessDataDir.toString());

        if (!tessDataDir.exists()) {
            if (!tessDataDir.mkdirs()) {
                Logger.e(TAG, "ERROR: Creation of directory " + tessDataDir + " failed, check does " +
                        "Android Manifest have permission to write to external storage.");
                return null;
            }else{
                Logger.i(TAG, "Created directory " + tessDataDir);
            }
        } else {
            initialized = true;
        }

        return tessDataDir;
    }

    public static File getStorageDir(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            return mContext.getExternalFilesDir("");
        }
        return new File(Environment.getExternalStorageDirectory().toString(),
                "Android" + File.separator + "data" + File.separator + mContext.getPackageName() +
                File.separator + "files" + File.separator);
    }

    private static void copyTessDataFiles(String path) {
        try {
            String fileList[] = mContext.getAssets().list(path);

            for (String fileName : fileList) {
                // open file within the assets folder
                // if it is not already there copy it to the sdcard
                File pathToDataFile = new File(mTessdataPath, fileName);
                if (!pathToDataFile.exists()) {

                    InputStream in = mContext.getAssets().open(path + "/" + fileName);
                    OutputStream out = new FileOutputStream(pathToDataFile);

                    // Transfer bytes from in to out
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();

                    Logger.d(TAG, "Copied " + fileName + "to tessdata");
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to copy files to tessdata " + e.toString());
        }
    }

}
