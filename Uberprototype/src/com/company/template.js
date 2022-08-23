
var map;

function initMap(routeLine, driverPosition) {
    map = new google.maps.Map(document.getElementById("map"), {
        center: new google.maps.LatLng(37.78490194772141, -122.41257123809527),
        zoom: 16,
    });


    var svgRoute = {
        path: "M10.453 14.016l6.563-6.609-1.406-1.406-5.156 5.203-2.063-2.109-1.406 1.406zM12 2.016q2.906 0 4.945 2.039t2.039 4.945q0 1.453-0.727 3.328t-1.758 3.516-2.039 3.070-1.711 2.273l-0.75 0.797q-0.281-0.328-0.75-0.867t-1.688-2.156-2.133-3.141-1.664-3.445-0.75-3.375q0-2.906 2.039-4.945t4.945-2.039z",
        fillColor: "blue",
        fillOpacity: 0.6,
        strokeWeight: 0,
        rotation: 0,
        scale: 2,
        anchor: new google.maps.Point(15, 30),
    };

    var svgDrive = {
        path: "M10.453 14.016l6.563-6.609-1.406-1.406-5.156 5.203-2.063-2.109-1.406 1.406zM12 2.016q2.906 0 4.945 2.039t2.039 4.945q0 1.453-0.727 3.328t-1.758 3.516-2.039 3.070-1.711 2.273l-0.75 0.797q-0.281-0.328-0.75-0.867t-1.688-2.156-2.133-3.141-1.664-3.445-0.75-3.375q0-2.906 2.039-4.945t4.945-2.039z",
        fillColor: "red",
        fillOpacity: 0.6,
        strokeWeight: 0,
        rotation: 0,
        scale: 2,
        anchor: new google.maps.Point(15, 30),
    };

    var svgSnap = {
        path: "M10.453 14.016l6.563-6.609-1.406-1.406-5.156 5.203-2.063-2.109-1.406 1.406zM12 2.016q2.906 0 4.945 2.039t2.039 4.945q0 1.453-0.727 3.328t-1.758 3.516-2.039 3.070-1.711 2.273l-0.75 0.797q-0.281-0.328-0.75-0.867t-1.688-2.156-2.133-3.141-1.664-3.445-0.75-3.375q0-2.906 2.039-4.945t4.945-2.039z",
        fillColor: "Green",
        fillOpacity: 0.6,
        strokeWeight: 0,
        rotation: 0,
        scale: 2,
        anchor: new google.maps.Point(15, 30),
    };


    var icons = {
        routeLine: {
            icon: svgRoute},
        drivePosition: {
            icon: svgDrive},
        snapLine: {
            icon: svgSnap}
    };


    var features = [
        {
            position: new google.maps.LatLng(37.7851393642658, -122.41073660722026),
            type: "routeLine",
        },
        {
            position: new google.maps.LatLng(37.78514784341398, -122.41088681092039),
            type: "drivePosition",
        },
        {
            position: new google.maps.LatLng(37.78502913525075, -122.41161637174964),
            type: "snapLine",
        },
        {
            position: new google.maps.LatLng(37.78497826026528, -122.41193823682134),
            type: "drivePosition",
        },
        {
            position: new google.maps.LatLng(37.78490194772141, -122.41257123809527),
            type: "routeLine",
        },
        {
            position: new google.maps.LatLng(37.78490194772141, -122.41257123809527),
            type: "snapLine",
        }
    ];

    // Create markers.
    for (var i = 0; i < features.length; i++) {
        var marker = new google.maps.Marker({
            position: features[i].position,
            icon: icons[features[i].type].icon,
            map: map,
        });
    }
}

initMap();

