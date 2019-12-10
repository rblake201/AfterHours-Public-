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

        databaseReference = FirebaseDatabase.getInstance().getReference("Event");
        eventList = new ArrayList<>();

        mMapView = findViewById(R.id.mapView);
        ArcGISMap map = new ArcGISMap(Basemap.Type.NATIONAL_GEOGRAPHIC, 43.8231, -111.7924, 16);
        mMapView.setMap(map);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Event event = ds.getValue(Event.class);
                    eventList.add(event);

                    Point mapPoint = new Gson().fromJson(ds.getValue(Event.class).coordinates, Point.class);
                    //Point wgs84Point = (Point) GeometryEngine.project(mapPoint, SpatialReferences.getWgs84());
                    // add the point with a symbol to graphics overlay and add overlay to map view
                    graphicsOverlay = new GraphicsOverlay();
                    mMapView.getGraphicsOverlays().add(graphicsOverlay);
                    SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
                    pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
                    Graphic pointGraphic = new Graphic(mapPoint, pointSymbol);

                    graphicsOverlay.getGraphics().add(pointGraphic);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView){
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                android.graphics.Point screenPoint = new android.graphics.Point(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));

                Point mapPoint = mMapView.screenToLocation((screenPoint));

                Point wgs84Point = (Point) GeometryEngine.project(mapPoint, SpatialReferences.getWgs84());

                //createGraphics(motionEvent, wgs84Point);
                Intent startIntent = new Intent(getApplicationContext(), EventInfoActivity.class);
                startIntent.putExtra("point", mapPoint.toJson());
                startActivity(startIntent);

                return true;
            }

            /*private void createGraphics(MotionEvent motionEvent, Point wgs84Point) {
                createGraphicsOverlay();
                createPointGraphics(motionEvent, wgs84Point);
            }


            private void createGraphicsOverlay() {
                if(!mMapView.getGraphicsOverlays().isEmpty()) {
                    //mMapView.getGraphicsOverlays().clear();
                }
                else if(mMapView.getGraphicsOverlays().isEmpty()) {
                    graphicsOverlay = new GraphicsOverlay();
                    mMapView.getGraphicsOverlays().add(graphicsOverlay);
                }
            }

            private void createPointGraphics(MotionEvent motionEvent, Point wgs84Point) {

                SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
                pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
                Graphic pointGraphic = new Graphic(wgs84Point, pointSymbol);

                graphicsOverlay.getGraphics().add(pointGraphic);

            }*/

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
