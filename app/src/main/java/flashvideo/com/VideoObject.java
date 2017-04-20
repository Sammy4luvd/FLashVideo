package flashvideo.com;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

/**
 * Created by Dalafiari Samuel on 12/10/2016.
 */

public class VideoObject {

    public static int KB_BENCHMARK = 1024;


    private String title;
    private Uri link;
    private Bitmap thumbnail;

    public VideoObject(String title) {

        this.title = title;
        this.link = link;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Uri getLink() {
        return link;
    }

    public void setLink(Uri link) {
        this.link = link;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }
}
