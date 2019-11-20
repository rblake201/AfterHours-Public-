package com.example.starterproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

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


public class MainActivity extends AppCompatActivity {

    private static final String sTag = "Gesture";
    private MapView mMapView;
    private GraphicsOverlay graphicsOverlay;
    private Callout mCallout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMapView = findViewById(R.id.mapView);
        ArcGISMap map = new ArcGISMap(Basemap.Type.TOPOGRAPHIC, 43.8231, -111.7924, 16);
        mMapView.setMap(map);

        //SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.TRIANGLE, Color.GREEN, 10);

        mMapView.setOnTouchListener(new DefaultMapViewOnTouchListener(this, mMapView){
            public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
                android.graphics.Point screenPoint = new android.graphics.Point(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));

                Point mapPoint = mMapView.screenToLocation((screenPoint));


                Point wgs84Point = (Point) GeometryEngine.project(mapPoint, SpatialReferences.getWgs84());

                createGraphics(motionEvent, wgs84Point);
                return true;
            }
            private void createGraphics(MotionEvent motionEvent, Point wgs84Point) {
                createGraphicsOverlay();
                createPointGraphics(motionEvent, wgs84Point);
            }


            private void createGraphicsOverlay() {
                graphicsOverlay = new GraphicsOverlay();
                mMapView.getGraphicsOverlays().add(graphicsOverlay);
            }

            private void createPointGraphics(MotionEvent motionEvent, Point wgs84Point) {

                SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
                pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
                Graphic pointGraphic = new Graphic(wgs84Point, pointSymbol);
                graphicsOverlay.getGraphics().add(pointGraphic);
            }
        });

    }


    private void createGraphics(MotionEvent motionEvent) {
        createGraphicsOverlay();
        createPointGraphics(motionEvent);
    }


    private void createGraphicsOverlay() {
        graphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(graphicsOverlay);
    }

    private void createPointGraphics(MotionEvent motionEvent) {


        Point point = new Point(motionEvent.getX(), motionEvent.getY(), SpatialReferences.getWgs84());
        SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.rgb(226, 119, 40), 10.0f);
        pointSymbol.setOutline(new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.BLUE, 2.0f));
        Graphic pointGraphic = new Graphic(point, pointSymbol);
        graphicsOverlay.getGraphics().add(pointGraphic);
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
