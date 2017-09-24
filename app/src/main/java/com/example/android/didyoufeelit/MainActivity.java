/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.didyoufeelit;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.net.URL;

/**
 * Displays the perceived strength of a single earthquake event based on responses from people who
 * felt the earthquake.
 */
public class MainActivity extends AppCompatActivity {

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        // Perform the HTTP request for earthquake data and process the response.
//        Event earthquake = Utils.fetchEarthquakeData(USGS_REQUEST_URL);
//
//        // Update the information displayed to the user.
//        updateUi(earthquake);

        //-- here we r creating an object of inner class which has extended the Asynch class
        //-- calling the execute method and providing the string for the first parameter of asynch class to be implemented in the doin background method
        EarthquakeAsynchTask earthquakeAsynchTask = new EarthquakeAsynchTask();

      /**  <p>This method is typically used with {@link #THREAD_POOL_EXECUTOR} to
                * allow multiple tasks to run in parallel on a pool of threads managed by
                * AsyncTask, however you can also use your own {@link Executor} for custom
                * behavior. **/

        earthquakeAsynchTask.execute(USGS_REQUEST_URL);
    }

    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(Event earthquake) {
        TextView titleTextView = (TextView) findViewById(R.id.title);
        titleTextView.setText(earthquake.title);

        TextView tsunamiTextView = (TextView) findViewById(R.id.number_of_people);
        tsunamiTextView.setText(getString(R.string.num_people_felt_it, earthquake.numOfPeople));

        TextView magnitudeTextView = (TextView) findViewById(R.id.perceived_magnitude);
        magnitudeTextView.setText(earthquake.perceivedStrength);
    }


    //here we r creating inner class
    //this class extends the AsyncTask class...
    //we implement method of this abstract class..to make EarthquakeAsynchTask to concrete class
    //3 classes r taken here as parameters
    //first one string here, ..if many links r there i think it will be URL...
    // Second one is progress....here we have not implemented this method ..so taken parameter as Void...else it will be integer might be
    // Third one is input param for the onPostExecute method...so..here we r showing the result on the screen of Event class varaibles
    //so we have taken Event as input

    private class EarthquakeAsynchTask extends AsyncTask<String,Void, Event>{


        @Override
        protected Event doInBackground(String... urls) {

            //-- below lines i started writing by thinking many urls r there...but in this project we r using only one String

//            int count = urls.length;
//            long totalSize= 0;
//
//            for (int i=0; i<count; i++){
//
//                totalSize +=
//
//            }

            //here we r putting the condition...so that if we dont have any input string then ..the app will not crash
            if (urls.length <1 || urls[0]== null){

                return null;
            }


            // here we have some steps which r skipped because of presence of only one string
            //creating instance of Event class and then entering the result of fetchEarthquakeData into it..
            Event result = Utils.fetchEarthquakeData(urls[0]);
            return  result;



        }

        @Override
        protected void onPostExecute(Event result) {
//            super.onPostExecute(event);

            //i have one doubt here ...if return type is void ..we can use only return
            if (result==null){
                return;
            }
            //-- here we  r filling the layout with the result...
            //we have put all the view of layout in updateUi ..so that we call it through method and update the screen
            updateUi(result);


        }
    }




}
