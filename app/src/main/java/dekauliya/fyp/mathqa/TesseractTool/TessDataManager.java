package dekauliya.fyp.mathqa.TesseractTool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;

import com.orhanobut.logger.Logger;
import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by dekauliya on 25/9/16.
 */
@EBean
public class TessDataManager {
    private static final String TAG = "TESS_DATA_MANAGER";
    private static final String TESSDATA_DIR = "tessdata";
    private static boolean initialized;
    private static Context mContext;
    private static File mTessdataPath;
    private static File mDataPath;
    static TessDataManager instance;


    TessDataManager(){

    }

    public static TessDataManager getInstance(Context context){
        if (instance == null){
            instance = new TessDataManager();
            mContext = context;
        }
        mContext = context;
        return instance;
    }

    public void initTessTrainedData() {
        if (initialized){
            return;
        }
        mDataPath = getStorageDirectory(mContext);
        Logger.d(String.format("TDM DataPath: %s", mDataPath));
        mTessdataPath = prepareTessdataDirectory();
        copyTessDataFiles(TESSDATA_DIR);
    }

    private File prepareTessdataDirectory() {
        File tessDataDir = new File(mDataPath, TESSDATA_DIR);
        Logger.d(TAG + tessDataDir.toString());

        if (!tessDataDir.exists()) {
            if (!tessDataDir.mkdirs()) {
                Logger.e(TAG + "ERROR: Creation of directory " + tessDataDir + " failed, check " +
                        "does " +
                        "Android Manifest have permission to write to external storage.");
                return null;
            }else{
                Logger.i(TAG + "Created directory " + tessDataDir);
            }
        } else {
            initialized = true;
        }
        Logger.d(TAG + "TESS DATA DIR IS DIRECTORY: " + tessDataDir.isDirectory());

        return tessDataDir;
    }


    @Background
    void copyTessDataFiles(String path) {
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

                    Logger.d(TAG + "Copied " + fileName + "to tessdata");
                }
            }
        } catch (IOException e) {
            Logger.e(TAG + "Unable to copy files to tessdata " + e.toString());
        }
    }


    /**
     * Displays an error message dialog box to the user on the UI thread.
     *
     * @param title The title for the dialog box
     * @param message The error message to be displayed
     */
    static void showErrorMessage(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setOnCancelListener(new FinishListener((Activity) context))
                .setPositiveButton( "Done", new FinishListener((Activity) context))
                .show();
    }

    /** Finds the proper location on the SD card where we can save files. */
    public File getStorageDirectory(Context context) {
        //Log.d(TAG, "getStorageDirectory(): API level is " + Integer.valueOf(android.os.Build.VERSION.SDK_INT));

        String state = null;
        try {
            state = Environment.getExternalStorageState();
        } catch (RuntimeException e) {
            Logger.e(TAG + "Is the SD card visible?", e);
            showErrorMessage(context, "Error", "Required external mStorage (such as an SD card) " +
                    "is unavailable.");
        }

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            try {
                return context.getExternalFilesDir(Environment.MEDIA_MOUNTED);
            } catch (NullPointerException e) {
                // We get an error here if the SD card is visible, but full
                Logger.e(TAG + "External mStorage is unavailable");
                showErrorMessage(context, "Error", "Required external mStorage (such as an SD " +
                        "card) is full or unavailable.");
            }

        } else {
            try {
                Storage storage = SimpleStorage.getInternalStorage(context);
                File file = storage.getFile("");
                return file.getAbsoluteFile();
            }catch(Exception e) {
                // Something else is wrong. It may be one of many other states, but all we need
                // to know is we can neither read nor write
                Logger.e(TAG + "External or Internal storage is unavailable");
                showErrorMessage(context, "Error", "Required external mStorage (such as an SD " +
                        "card) is unavailable or corrupted.");
            }
        }
        return null;
    }

    public static String getDataPath(){

        return mDataPath.toString();
    }


}
