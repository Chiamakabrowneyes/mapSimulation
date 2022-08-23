package com.company;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;

public class Main {

    public static ArrayList<double[]> longLatPairs = new ArrayList<>(
            Arrays.asList(new double[]{37.785191918157246, -122.41031518470686}, new double[]{37.78514142086996, -122.41071681197415}, new double[]{37.785076495735645, -122.41120667553504}, new double[]{37.785055016302586, -122.41152659601302}, new double[]{37.784993542378935, -122.41199330036702}, new double[]{37.78500838091691, -122.41210327093319}, new double[]{37.784084146293765, -122.41177604144362},new double[]{37.78413290207873, -122.41148904508798}, new double[]{37.7841625794973, -122.41126910395565}, new double[]{37.784188017275156, -122.41097942539109}, new double[]{37.78421133523049, -122.41063342043898},new double[]{37.78425585130665, -122.41024986456185}));

    double[] positionPoint = {37.905141420, -122.57071681};


    public static double toDegrees(double angle) {
        double RADIANS_TO_DEGREES = 57.29577951308232;
        return angle * RADIANS_TO_DEGREES;
    }

    public static double calcOrientation(double lat1, double lon1, double lat2, double lon2) {
        double factor = Math.cos((lat1 + lat2) * Math.PI / 360);
        return Math.atan2((lat2 - lat1), factor * (lon2 - lon1));
    }

    public static double headingSinceNorth(double[] point1, double[] point2) {

        double latitude1 = point1[0];
        double longitude1 = point1[1];

        double latitude2 = point2[0];
        double longitude2 = point2[1];

        // angle from the following function is -180 to 180 in counterclockwise, and 0 is east
        double angle =
                toDegrees(
                        calcOrientation(
                                latitude1,
                                longitude1,
                                latitude2,
                                longitude2));
        angle = 90 - angle;
        return angle < 0 ? 360 + angle : angle;
    }

    public static void comparePositionPointToRoute(ArrayList<double[]> routePositions, double[] positionPoint) {

        for(int i = 1; i < routePositions.size(); i++){
            //call heading since north function on the consecutive position points
            double headingBtwAngles = headingSinceNorth(routePositions.get(0), positionPoint);
            //review
            //headingBtwAngles  > 150
            //trim off

            System.out.println(headingBtwAngles);
        }
    }

    //create a new line for a new component
    public static void writeLatLong(Hashtable<Double, Double> dict, String header, boolean newComponent) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("LatLong.txt", true));

        //iterate through the dictionary
        //write  the  latitude. Then write the longitude
        if (newComponent){
            writer.write("\n" + header+ " : ");
        }
        else{
            writer.write(header+ " : ");
        }
        final int[] counter = {0};

        dict.forEach((key, value) -> {

            try {
                counter[0]++;
                writer.write(key + " , " + value +  " ; ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.close();
    }

    public static void writeLatLong(Hashtable<Double, Double> dict, String header, boolean newComponent, List heading) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter("LatLong.txt", true));

        //iterate through the dictionary
        //write  the  latitude. Then write the longitude
        if (newComponent){
            writer.write("\n" + header+ " : ");
        }
        else{
            writer.write(header+ " : ");
        }
        final int[] counter = {0};

        dict.forEach((key, value) -> {

            try {
                counter[0]++;
                writer.write(key + " , " + value + " , " + heading.get(counter[0]) + " ; ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.close();
    }


    public static RouteLineWithDriverPosition readRouteLine() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/chiamaka/IdeaProjects/Uberprototype/LatLong.txt"));
        String line;
        boolean processingRouteLine = false;


        //variables for routeLine:
        List<Point> routeLine = new ArrayList<Point>();

        //variables for driverPositions:
        List<PointWithHeading> driverPosition = new ArrayList<PointWithHeading>();
        Double readLatitude = null;
        Double readLongitude = null;
        Double  readHeading = null;


        while ((line = reader.readLine()) != null){
            String parts[] = line.split(" ");


            for (String i : parts) {
                if (i.equals("route-line")) {
                    processingRouteLine = true;
                }

                if (i.equals("driver-position")){
                    processingRouteLine = false;
                }



                try{double readValue = 0;
                    readValue = Double.valueOf(i);


                    if (processingRouteLine) {
                        // is this a lat or longitude?

                        if (readLatitude == null) {
                            readLatitude = readValue;


                        } else if (readLongitude == null) {
                            readLongitude = readValue;


                        }
                        if ((readLatitude != null) && (readLongitude != null)) {

                            Point point = new Point(readLatitude, readLongitude);

                            routeLine.add(point);

                            readLatitude = null;
                            readLongitude = null;
                        }

                    }

                    else {

                        if (readLatitude == null){
                            readLatitude = readValue;


                        } else if(readLongitude == null){
                            readLongitude = readValue;


                        } else if (readHeading == null){
                            readHeading = readValue;


                        }
                        if (readLatitude != null && readLongitude != null && readHeading != null) {
                            Point driverPoint = new Point(readLatitude, readLongitude );
                            PointWithHeading driverPointWithHeading = new PointWithHeading(driverPoint, readHeading );
                            driverPosition.add(driverPointWithHeading);

                            readLatitude = null;
                            readLongitude = null;
                            readHeading = null;

                        }
                }
                } catch (NumberFormatException e){
                    //Include error message here
                }
            }
        }
        reader.close();
        RouteLineWithDriverPosition routeLineWithDriverPosition = new RouteLineWithDriverPosition(routeLine, driverPosition);
        GenerateMap map = new GenerateMap( routeLine, driverPosition);

        return routeLineWithDriverPosition;
    }

    public static class RouteLineWithDriverPosition{
        List<Point> routeLine;
        List<PointWithHeading> driverPosition;

        public RouteLineWithDriverPosition(List<Point> routeLine, List<PointWithHeading> driverPosition){
            this.routeLine = routeLine;
            this.driverPosition = driverPosition;
        }
    }
    
    public static class GenerateMap {
        public GenerateMap(List<Point> routeLine, List<PointWithHeading> driverPosition) {
            int len = routeLine.size();

            for (Point point : routeLine){
                Double pointLat = point.getLatitude();
                Double pointLong = point.getLongitude();

                System.out.println(pointLat);
                System.out.println(pointLong);
            }

        }
    }

    public static class Point{
        Double latitude;
        Double longitude;


        public Point(Double latitude, Double longitude){
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public void setLatitude(Double latitude){
            this.latitude = latitude;
        }

        public void setLongitude(Double longitude){
            this.longitude = longitude;
        }

        public Double getLatitude(){
            return latitude;
        }

        public Double getLongitude(){
            return longitude;
        }
    }


    public static class PointWithHeading{
        Point point;
        Double heading;

        public PointWithHeading(Point point, Double heading){
            this.point = point;
            this.heading = heading;
        }

        public void setPoint(Point point){ this.point = point; }

        public void setHeading(Double heading) {this.heading = heading; }

        public Point getPoint(){return point; }

        public Double getHeading(){return heading; }
    }


    public static void main(String[] args) throws Exception {
        Hashtable<Double, Double> dictLatLong = new Hashtable();

        dictLatLong.put(37.7851393642658, -122.41073660722026);
        dictLatLong.put(37.78514784341398, -122.41088681092039);

        dictLatLong.put(37.78502913525075, -122.41161637174964);
        dictLatLong.put(37.78497826026528, -122.41193823682134);
        /*dictLatLong.put(37.78490194772141, -122.41257123809527);
        dictLatLong.put(37.78490194772141, -122.41257123809527);
        dictLatLong.put(37.78465944792209, -122.4127626811441);
        dictLatLong.put(37.78443102305325, -122.41271383130527);
        dictLatLong.put(37.78421224927755, -122.41271383130527);
        dictLatLong.put(37.78398060575038, -122.41265276900671);
        dictLatLong.put(37.78398382302655, -122.4123678116135);
        dictLatLong.put(37.78396130209033, -122.4121072791397);
        dictLatLong.put(37.784054603067155, -122.41171648042904);
        dictLatLong.put(37.78409321033343, -122.41126461941978);*/

        Hashtable<Double, Double> arrLatLong = new Hashtable();
        arrLatLong.put(37.78513, -122.41073);
        arrLatLong.put(37.785147, -122.41088);

        arrLatLong.put(37.78502, -122.411616);
        arrLatLong.put(37.78497, -122.411938);
        /*arrLatLong.put(37.78490, -122.412571);
        arrLatLong.put(37.78486, -122.412840);
        arrLatLong.put(37.78465, -122.412762);
        arrLatLong.put(37.78443, -122.412713);
        arrLatLong.put(37.78421, -122.412713);
        arrLatLong.put(37.78398, -122.412652);
        arrLatLong.put(37.78398, -122.412367);
        arrLatLong.put(37.78396, -122.412107);
        arrLatLong.put(37.78405, -122.411716);
        arrLatLong.put(37.78409, -122.411264);*/

        List<Double> headings = List.of(41.1, 42.4, 43.3, 44.4);
        //writeLatLong(arrLatLong, "driver-position", true, headings);
        //writeLatLong(dictLatLong, "route-line", true);

        System.out.println(readRouteLine());




    }

}
