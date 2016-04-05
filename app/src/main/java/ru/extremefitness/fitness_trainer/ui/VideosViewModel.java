package ru.extremefitness.fitness_trainer.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Arrays;

import ru.extremefitness.fitness_trainer.R;
import ru.extremefitness.fitness_trainer.models.RootContainer;
import ru.extremefitness.fitness_trainer.models.VideoInfoContainer;
import ru.extremefitness.fitness_trainer.network.LoaderRequestQueue;

/**
 * Created by Comp on 18.08.2015.
 */
public class VideosViewModel {

    private final VideosActivity context;
    private SwipeRefreshLayout rootView;
    private ListView listView;
    private VideoInfoContainer.VideoInfo[] baseList;
    private final ArrayList<VideoInfoContainer.VideoInfo> result = new ArrayList<>();
    private ImageLoader imageLoader;
    private VideoInfoAdapter mAdapter;
    private String query;
    private TextView emptyView;

    public void setError(final String error) {
        emptyView.setText(error);
    }

    enum ViewModes {
        PROGRESS {
            @Override
            void apply(VideosViewModel model) {
                model.showProgress();
            }
        },
        CONTENT {
            @Override
            void apply(VideosViewModel model) {
                model.showContent();
            }
        },
        ERROR {
            @Override
            void apply(VideosViewModel model) {
                model.showError();
            }
        }
        ;

        abstract void apply(final VideosViewModel model);
    }

    VideosViewModel(final VideosActivity context) {
        this.context = context;

        imageLoader = LoaderRequestQueue.getInstance(context).getImageLoader();

        prepareGui();
    }

    private void prepareGui() {
        rootView = (SwipeRefreshLayout) View.inflate(context, R.layout.screen_videoa, null);
        listView = (ListView) rootView.findViewById(android.R.id.list);
        mAdapter = new VideoInfoAdapter();
        listView.setAdapter(mAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int topRowVerticalPosition =
                        (listView == null || listView.getChildCount() == 0) ?
                                0 : listView.getChildAt(0).getTop();
                rootView.setEnabled(topRowVerticalPosition >= 0);
            }
        });

       emptyView = (TextView) rootView.findViewById(R.id.empty_text);

        rootView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        context.loadList();
                    }
                });
            }
        });

        assert null != listView;

    }

    View getView() {
        return rootView;
    }

    private void showProgress() {
        rootView.post(new Runnable() {
            @Override
            public void run() {
                rootView.setRefreshing(true);
            }
        });
        listView.setVisibility(View.GONE);

    }

    private void showContent() {
        rootView.setRefreshing(false);
        listView.setVisibility(View.VISIBLE);
    }

    private void showError() {
        rootView.setRefreshing(false);
        listView.setVisibility(View.GONE);
        showSearchEmpty(true);
    }

    private void showSearchEmpty(final boolean isShow) {
        if (isShow) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
    }

    void setContent(final RootContainer result) {
        this.baseList = result.getData();
        prepareResult();
        mAdapter.notifyDataSetChanged();
    }

    void update(final String newText) {
        query = newText;
        prepareResult();
        mAdapter.notifyDataSetChanged();
    }

    private void prepareResult() {
        result.clear();

        if (TextUtils.isEmpty(query)) {
            result.addAll(new ArrayList<>(Arrays.asList(baseList)));

        } else if (null != baseList && baseList.length>0) {
            for (int i = 0; i<baseList.length; i++) {
                if (isEqualsQuery(baseList[i].getTitle())) {
                    result.add(baseList[i]);
                }
            }
        }

        final boolean isShowEmpty = result.isEmpty() && baseList != null && baseList.length>0;
        showSearchEmpty(isShowEmpty);

    }

    private boolean isEqualsQuery(final String info) {

        if (TextUtils.isEmpty(info)) {
           return false;
        } else if (info.toLowerCase().contains(query.toLowerCase())) {
            return true;
        } else {
            return false;
        }
    }

    private class VideoInfoAdapter extends ArrayAdapter<VideoInfoContainer.VideoInfo> {

        private VideoInfoAdapter() {
            super(context, 0, result);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = View.inflate(context, R.layout.list_item, null);
                final Holder holder = new Holder();
                holder.imageView = (NetworkImageView) convertView.findViewById(R.id.network_image);
                holder.title = (TextView) convertView.findViewById(android.R.id.text1);
                holder.text = (TextView) convertView.findViewById(android.R.id.text2);

                convertView.setTag(holder);
            }

            final VideoInfoContainer.VideoInfo videoInfo = getItem(position);

            final Holder holder = (Holder) convertView.getTag();
            final String url = "http://img.youtube.com/vi/"+ videoInfo.getV() + "/hqdefault.jpg";
            imageLoader.get(url, ImageLoader.getImageListener(holder.imageView,
                    android.R.drawable.ic_menu_upload_you_tube, android.R.drawable.stat_notify_error));
            holder.imageView.setImageUrl(url, imageLoader);
            holder.title.setText(videoInfo.getTitle());

            final String text = videoInfo.getText();
            holder.text.setText(Html.fromHtml(text));

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent videoIntent = new Intent(Intent.ACTION_VIEW);
                    videoIntent.setData(Uri.parse("http://youtube.com/watch?v=" + videoInfo.getV()));
                    context.startActivity(videoIntent);
                }
            });

            return convertView;
        }
    }

    private class Holder {
        private NetworkImageView imageView;
        private TextView title;
        private TextView text;
    }
}
