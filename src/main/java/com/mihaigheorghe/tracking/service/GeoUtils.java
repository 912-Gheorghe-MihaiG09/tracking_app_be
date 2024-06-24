package com.mihaigheorghe.tracking.service;

import org.springframework.stereotype.Service;


public class GeoUtils {

    private static final double EARTH_RADIUS = 6371e3; // Radius of the earth in meters

    /**
     * Calculates the distance between two points in meters using the Haversine formula.
     * @param lat1 Latitude of the first point.
     * @param lon1 Longitude of the first point.
     * @param lat2 Latitude of the second point.
     * @param lon2 Longitude of the second point.
     * @return Distance between the two points in meters.
     */
    static public double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    /**
     * Checks if a point is inside a circle defined by a center point and radius.
     * @param pointLat Latitude of the point.
     * @param pointLon Longitude of the point.
     * @param centerLat Latitude of the center.
     * @param centerLon Longitude of the center.
     * @param radius Radius of the circle in meters.
     * @return true if the point is inside the circle, false otherwise.
     */
    static public boolean isPointInCircle(double pointLat, double pointLon, double centerLat, double centerLon, double radius) {
        double distance = haversineDistance(pointLat, pointLon, centerLat, centerLon);
        return distance <= radius;
    }
}
