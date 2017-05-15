package dekauliya.fyp.mathqa.Utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import dekauliya.fyp.mathqa.DataServices.MathQaServiceGenerator;
import dekauliya.fyp.mathqa.R;

import static dekauliya.fyp.mathqa.DataServices.MathQaServiceGenerator.BASE_URL;

/**
 * Created by dekauliya on 12/3/17.
 *
 * Utilities for network access.
 */
@EBean
public class NetworkUtils {
    @RootContext
    Context context;

    public void showIpDialog(){

        new MaterialDialog.Builder(context)
                .title("Reload / Change IP")
                .positiveText("Reload")
                .positiveColorRes(R.color.colorAccent)
                .negativeText("Cancel")
                .negativeColorRes(R.color.colorAccent)
                .input("Enter a proper IP Address",
                        BASE_URL, false,
                        new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                String newIPAddress = input.toString();
                                MathQaServiceGenerator.changeApiBaseUrl(newIPAddress);
                            }
                        }).show();
    }
}
