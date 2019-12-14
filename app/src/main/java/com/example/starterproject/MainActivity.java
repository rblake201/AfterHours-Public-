package com.example.starterproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.esri.arcgisruntime.arcgisservices.LabelDefinition;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.symbology.TextSymbol;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Callout;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String sTag = "Gesture";
    private MapView mMapView;
    private GraphicsOverlay graphicsOverlay;
    private Callout mCallout;
    DatabaseReference databaseReference;
    private List<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create firebase object
        databaseReference = FirebaseDatabase.getInstance().getReference("Event");
        eventList = new ArrayList<>();

        //Creates a MapView starting over Rexburg
        mMapView = findViewById(R.id.mapView);
        ArcGISMap map = new ArcGISMap(Basemap.Type.NATIONAL_GEOGRAPHIC, 43.8231, -111.7924, 16);
        mMapView.setMap(map);

        //Checks firebase to see when data is changed
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                int i = 0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){  //loops through the data in firebase
                    Event event = ds.getValue(Event.class);
                    eventList.add(event);  //adds firebase data to the eventList

                    Geometry mPoint = new Geometry() {}.fromJson(eventList.get(i).coordinates); //changes json info in firebase to a point
                    System.out.println(mPoint);

                    //displays event information to the map view
                    TextSymbol eventTitleSymbol =
                            new TextSymbol(
                                    10, "   " + eventList.get(i).title + "\n"
                                    + eventList.get(i).date + "\n"
                                    + eventList.get(i).time + "\n"
                                    + eventList.get(i).description,
                                    Color.argb(255, 0, 0, 230),
                                    TextSymbol.HorizontalAlignment.LEFT, TextSymbol.VerticalAlignment.TOP);

                    //creates and add graphics overlay
                    graphicsOverlay = new GraphicsOverlay();
                    mMapView.getGraphicsOverlays().add(graphicsOverlay);
                    SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
                    pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
                    Graphic pointGraphic = new Graphic(mPoint, pointSymbol);
                    Graphic eventTitle = new Graphic(mPoint, eventTitleSymbol);

                    graphicsOverlay.getGraphics().add(pointGraphic);
                    graphicsOverlay.getGraphics().add(eventTitle);
                    i++;
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //listens for a touch to the screen and gets the location of the event
        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView){
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                android.graphics.Point screenPoint = new android.graphics.Point(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));

                Point mapPoint = mMapView.screenToLocation((screenPoint));

                Intent startIntent = new Intent(getApplicationContext(), EventInfoActivity.class);
                startIntent.putExtra("point", mapPoint.toJson()); //passes the point information
                startActivity(startIntent);

                return true;
            }

        });

    }



    @Override
    protected void onPause(){
        mMapView.pause();
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mMapView.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.dispose();
    }

}
