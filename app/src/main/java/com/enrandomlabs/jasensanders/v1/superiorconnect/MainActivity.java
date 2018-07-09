package com.enrandomlabs.jasensanders.v1.superiorconnect;

import android.content.BroadcastReceiver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ConnectFragment.OnFragmentInteractionListener {
    private static final String CLASS_NAME = MainActivity.class.getSimpleName();
    private static final String LOG_TAG = CLASS_NAME;
    public static final String SERVICE_EVENT_SEND = "SERVICE_EVENT_SEND_" + CLASS_NAME;
    public static final String SERVICE_EXTRA_RESPONSE = "SERVICE_EVENT_RESPONSE_" + CLASS_NAME;



    //Saved Instance variables
    private Fragment mCurrentFragment;
    public static final String CURRENT_FRAGMENT = "CURRENT_FRAGMENT_" + CLASS_NAME;

    //Message Receiver and Navigation Keys
    public static final String NAV_EVENT_SEND_NOTE = "NAV_EVENT_NOTE_com.enrandomlabs.jasensanders.v1.superiorconnect";
    public static final String NAV_EVENT_SEND_EWASTE = "NAV_EVENT_EWASTE_com.enrandomlabs.jasensanders.v1.superiorconnect" ;
    public static final String NAV_EVENT_SEND_ESTIMATE = "NAV_EVENT_ESTIMATE_com.enrandomlabs.jasensanders.v1.superiorconnect";
    public static final String NAV_EVENT_MAIN = "NAV_EVENT_MAIN_com.enrandomlabs.jasensanders.v1.superiorconnect";
    public static final String NAV_EVENT_CONNECT = "NAV_EVENT_CONNECT_com.enrandomlabs.jasensanders.v1.superiorconnect";
    public static final String NAV_EVENT_INFO = "NAV_EVENT_INFO_com.enrandomlabs.jasensanders.v1.superiorconnect";

    //Receiver
    private BroadcastReceiver mMessageReceiver;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    onFragmentChange(NAV_EVENT_MAIN);
                    return true;
                case R.id.navigation_dashboard:

                    onFragmentChange(NAV_EVENT_CONNECT);
                    return true;
                case R.id.navigation_notifications:

                    onFragmentChange(NAV_EVENT_INFO);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup correct fragment
        if(savedInstanceState != null){

            //If restoring from previous, swap to current fragment
            mCurrentFragment  = getSupportFragmentManager().getFragment(savedInstanceState, CURRENT_FRAGMENT);
            swapFragment(mCurrentFragment);

        }else{

            //Otherwise Navigate to MainFragment
            onFragmentChange(NAV_EVENT_MAIN);
        }


        //Change font of the default title
        int titleId = getResources().getIdentifier("action_bar_title", "id",
                "android");
        TextView titleTextView = (TextView) findViewById(titleId);
        titleTextView.setTypeface(Typeface.SERIF);

        //Setup bottom navigation
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Using Callbacks for navigation for now.
        //registerMessageReceiver();
    }

//    /** MessageReceiver for Nav events */
//    private class MessageReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//
//            onFragmentChange(action);
//
//        }
//    }

    /**This is the implementation of the interface declared in the
     * ConnectFragment.
     * @param navKey final String of navigation key from MainActivity
     */
    @Override
    public void onFragmentInteraction(final String navKey) {
        onFragmentChange(navKey);
    }

    /** Fragment change process function
     * All fragment change happens through this function, both local and callback. */
    private void onFragmentChange(String intentAction){

        if(intentAction != null) {
            switch (intentAction) {

                case NAV_EVENT_SEND_NOTE: {
                    NoteFragment fragment = NoteFragment.newInstance(null, null);
                    mCurrentFragment = fragment;
                    swapFragment(fragment);
                    break;
                }
                case NAV_EVENT_SEND_EWASTE: {
                    EwasteFragment fragment = EwasteFragment.newInstance(null,null);
                    mCurrentFragment = fragment;
                    swapFragment(fragment);
                    break;
                }
                case NAV_EVENT_SEND_ESTIMATE: {
                    EstimateFragment fragment = EstimateFragment.newInstance(null, null);
                    mCurrentFragment = fragment;
                    swapFragment(fragment);
                    break;
                }
                case NAV_EVENT_MAIN:{
                    MainFragment fragment = MainFragment.newInstance(null, null);
                    mCurrentFragment = fragment;
                    swapFragment(fragment);
                    break;
                }
                case NAV_EVENT_CONNECT:{
                    ConnectFragment fragment = ConnectFragment.newInstance(null, null);
                    mCurrentFragment = fragment;
                    swapFragment(fragment);
                    break;
                }
                case NAV_EVENT_INFO:{
                    InfoFragment fragment = InfoFragment.newInstance(null, null);
                    mCurrentFragment = fragment;
                    swapFragment(fragment);
                    break;
                }
            }
        }


    }

    /** Fragment swap sub-routine */
    private void swapFragment(Fragment destination){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_container, destination)
                .commit();

    }

//    /** Register receiver and add all Nav filter triggers */
//    private void registerMessageReceiver(){
//
//        //If there isn't a messageReceiver yet make one.
//        if(mMessageReceiver == null) {
//            mMessageReceiver = new MessageReceiver();
//        }
//
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(NAV_EVENT_MAIN);
//        filter.addAction(NAV_EVENT_CONNECT);
//        filter.addAction(NAV_EVENT_INFO);
//        filter.addAction(NAV_EVENT_SEND_NOTE);
//        filter.addAction(NAV_EVENT_SEND_EWASTE);
//        filter.addAction(NAV_EVENT_SEND_ESTIMATE);
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
//
//    }
//
//    /** Unregister receiver and log errors */
//    private void unRegisterMessageReceiver(){
//
//        if(mMessageReceiver != null){
//            try {
//                LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
//            }catch(Exception e){
//
//                Log.e(LOG_TAG, "messageReciever unregisterd already", e);
//            }
//        }
//    }

    /** Save the current fragment for lifecycle events */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(mCurrentFragment != null) {
            getSupportFragmentManager().putFragment(outState, CURRENT_FRAGMENT, mCurrentFragment);
        }

    }

    /** Called when pausing the activity
     * unregister receiver onPause */
    @Override
    public void onPause() {
        //unRegisterMessageReceiver();
        super.onPause();
    }

    /** Called when returning to the activity
     * re-register receiver onResume */
    @Override
    public void onResume() {
        super.onResume();
        //registerMessageReceiver();

    }

}
