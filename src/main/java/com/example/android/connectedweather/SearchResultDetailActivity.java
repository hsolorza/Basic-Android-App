package com.example.android.connectedweather;

/**
 * Created by soloh on 5/7/2017.
 */
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.view.Menu;

import com.example.android.connectedweather.utils.ForecastUtils;

public class SearchResultDetailActivity extends AppCompatActivity {
    private TextView mSearchResultDateTV;
    private TextView mSearchResultDescriptionTV;
    private TextView mSearchResultTempTV;
    private ForecastUtils.SearchResult msearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_detail);

        mSearchResultDateTV = (TextView)findViewById(R.id.tv_search_result_date);
        mSearchResultDescriptionTV = (TextView)findViewById(R.id.tv_search_result_description);
        mSearchResultTempTV = (TextView)findViewById(R.id.tv_search_result_temp);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(ForecastUtils.SearchResult.EXTRA_SEARCH_RESULT)) {
            msearchResult = (ForecastUtils.SearchResult)intent.getSerializableExtra(ForecastUtils.SearchResult.EXTRA_SEARCH_RESULT);
            mSearchResultDateTV.setText(msearchResult.date);
            mSearchResultDescriptionTV.setText(msearchResult.description);
            mSearchResultTempTV.setText(msearchResult.tempurature.toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case R.id.action_view_map:
                 viewRepoOnWeb();
                 return true;
             case R.id.action_share:
                 shareRepo();
                 return true;
             default:
                 return super.onOptionsItemSelected(item);
         }
    }

    public void shareRepo() {
        if (msearchResult != null) {
           String shareText = msearchResult.date + " " +
                              msearchResult.description + " " +
                              msearchResult.tempurature;
           ShareCompat.IntentBuilder.from(this)
                       .setType("text/plain")
                       .setText(shareText)
                       .setChooserTitle(R.string.action_share)
                       .startChooser();
        }
    }

    public void viewRepoOnWeb() {
       if (msearchResult != null) {
            Uri repoUri = Uri.parse("geo:44.6,-123.3");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, repoUri);
            if (webIntent.resolveActivity(getPackageManager()) != null) {
               startActivity(webIntent);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weather_detail, menu);
        return true;
      }
}
