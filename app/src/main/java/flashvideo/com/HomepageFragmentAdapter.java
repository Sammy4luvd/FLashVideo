package flashvideo.com;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Dalafiari Samuel on 12/10/2016.
 */

public class HomepageFragmentAdapter extends FragmentStatePagerAdapter {

    public HomepageFragmentAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new VideoListFragment();
            case 1:

                return new HistoryFragment();
            case 2:
                return new HistoryFragment();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Videos";

            case 1:

                return "Favourite";
            case 2:

                return "History";
        }
        return null;
    }

}
