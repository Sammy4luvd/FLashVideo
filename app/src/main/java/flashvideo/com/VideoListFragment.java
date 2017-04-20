package flashvideo.com;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dalafiari Samuel on 12/10/2016.
 */

public class VideoListFragment extends Fragment {
    public static String VIDEO_EXTRA_TAG = "video_link";
    public static String VIDEO_TITLE_TAG = "video_title";
    List<VideoObject> videoObjectList = new ArrayList<>();
    int video_cursor_column_index;
    String[] thumbColumns = {MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID};
    private Cursor videoCursor;
    private int videoCount;
    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialiseProjectorAndCursor();
    }

    public void initialiseProjectorAndCursor() {
        String[] proj = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION
        };

        videoCursor = this.getActivity().managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, proj, null, null, null);
        videoCount = videoCursor.getCount();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.videos_list_fragment, container, false);


        listView = (ListView) rootView.findViewById(R.id.video_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                video_cursor_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                videoCursor.moveToPosition(position);
                String file_name = videoCursor.getString(video_cursor_column_index);


                video_cursor_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
                videoCursor.moveToPosition(position);

                StringBuilder shrinkTitle = new StringBuilder();
                shrinkTitle.append(videoCursor.getString(video_cursor_column_index));
                shrinkTitle.setLength(40);
                String title = String.valueOf(shrinkTitle);


                Intent videoPlayer = new Intent(getActivity(), VideoPlayerActivity.class);
                videoPlayer.putExtra(VideoListFragment.VIDEO_EXTRA_TAG, file_name);
                videoPlayer.putExtra(VideoListFragment.VIDEO_TITLE_TAG, title);
                startActivity(videoPlayer);

            }
        });


        listView.setAdapter(new VideoListAdapter(getActivity().getApplicationContext()));


        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class VideoListAdapter extends BaseAdapter {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        public VideoListAdapter(Context c) {

        }


        @Override
        public int getCount() {
            return videoCount;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            String id = null;
            if (convertView == null)
                convertView = layoutInflater.inflate(R.layout.video_file, parent, false);

            video_cursor_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME);
            videoCursor.moveToPosition(position);
            TextView title = (TextView) convertView.findViewById(R.id.video_title);
            StringBuilder shrinkTitle = new StringBuilder();
            shrinkTitle.append(videoCursor.getString(video_cursor_column_index));
            shrinkTitle.setLength(28);
            title.setText(shrinkTitle.toString());


            video_cursor_column_index = videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
            TextView size = (TextView) convertView.findViewById(R.id.video_size);

            int sizePath = Integer.parseInt(videoCursor.getString(video_cursor_column_index));
            int divided = sizePath / VideoObject.KB_BENCHMARK;
            if (divided < VideoObject.KB_BENCHMARK) {

                size.setText(divided + "KB");
            } else {

                long dividedMB = divided / VideoObject.KB_BENCHMARK;
                size.setText(dividedMB + "MB");
            }


            String thumbPath = null;

            int duration = videoCursor.getInt(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            TextView length = (TextView) convertView.findViewById(R.id.video_length);
            length.setText(duration + "sec.");

            try {

                int videoID = videoCursor.getInt(videoCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                Cursor videoThumbnail = getActivity().managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, thumbColumns,
                        MediaStore.Video.Thumbnails.VIDEO_ID + "=" + videoID, null, null);

                if (videoThumbnail.moveToFirst()) {
                    thumbPath = videoThumbnail.getString(videoThumbnail.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                    Log.i("ThumbsPath", thumbPath);
                }
                ImageView imageView = (ImageView) convertView.findViewById(R.id.video_thumbnail);
                imageView.setImageURI(Uri.parse(thumbPath));


            } catch (Exception e) {

                Log.d("ThumbnailError", e.getMessage());

            }


            //videoThumbnail.close();


            return convertView;
        }
    }

}
