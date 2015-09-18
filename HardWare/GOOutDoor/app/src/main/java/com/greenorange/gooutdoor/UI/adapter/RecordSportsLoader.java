package com.greenorange.gooutdoor.UI.adapter;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.Toast;

import com.greenorange.gooutdoor.UI.adapter.entity.RecoreSportsCategory;
import com.greenorange.gooutdoor.entity.SportsDBData;
import com.greenorange.gooutdoor.framework.Dao.Interface.SportsTYPE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JasWorkSpace on 15/9/16.
 */
public class RecordSportsLoader  extends AsyncTaskLoader<Boolean> {
        private RecyclerArrayAdapter mRecyclerArrayAdapter;
        public RecordSportsLoader(Context context, RecyclerArrayAdapter recyclerArrayAdapter) {
                super(context);
                mRecyclerArrayAdapter = recyclerArrayAdapter;
        }
        @Override
        public Boolean loadInBackground() {
            Toast.makeText(getContext(), "loadInBackground", Toast.LENGTH_SHORT).show();
                mRecyclerArrayAdapter.addAll(getTestRecoreSportsCategory());


                return false;
        }
    //////////////////////////////////////////////////////////////
    // for test
    private List<RecoreSportsCategory> getTestRecoreSportsCategory(){
        List<RecoreSportsCategory> list = new ArrayList<RecoreSportsCategory>();
        for(int i=0 ; i < 10; i++){
            list.add(new RecoreSportsCategory(getContext(),getSportsDBData(i, i * 3)));
            list.add(new RecoreSportsCategory(getContext(),getSportsDBData(i, i*3)));
        }
        return list;
    }
    private SportsDBData getSportsDBData(int year, int month){
        SportsDBData sportsDBData = new SportsDBData();
        sportsDBData.setSports_time_year(year);
        sportsDBData.setSports_time_month(month);
        sportsDBData.setSports_type(SportsTYPE.SPORT_TYPE_RUN);
        sportsDBData.setSports_total_time(35669);
        sportsDBData.setSports_time_minute(23);
        sportsDBData.setSports_time_hourofday(9);
        sportsDBData.setSports_total_distance(13232);
        sportsDBData.setSports_totle_calorie(34422324);
        return sportsDBData;
    }
}
