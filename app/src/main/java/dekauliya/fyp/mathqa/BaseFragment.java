package dekauliya.fyp.mathqa;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.vlonjatg.progressactivity.ProgressFrameLayout;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import dekauliya.fyp.mathqa.Utils.DrawableType;
import dekauliya.fyp.mathqa.Utils.DrawableUtils;

@EFragment
public class BaseFragment extends Fragment {

    @ViewById(R.id.progress_activity)
    public ProgressFrameLayout progressActivity;

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Try again button clicked", Toast.LENGTH_LONG).show();
        }
    };

    public void showErrorPage() {
        progressActivity.showError(
                DrawableUtils.getDrawable(DrawableType.NO_WIFI, getActivity(),  R.color.colorAccent),
                getString(R.string.no_internet),
                getString(R.string.no_internet_desc),
                getString(R.string.error_try_again),
                errorClickListener);
    }

    public void showEmptyPage(String emptyTitle){
        progressActivity.showEmpty(
                DrawableUtils.getDrawable(DrawableType.NO_DATA, getActivity(), R.color.colorAccent),
                emptyTitle,
                "");
    }
}
