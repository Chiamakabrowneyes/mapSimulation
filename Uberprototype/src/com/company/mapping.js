
var map;

function initMap(routeLine, driverPosition) {
    map = new google.maps.Map(document.getElementById("map"), {
        center: new google.maps.LatLng(${centerLatitude}, ${centerLongitude}),
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

    var allFeatures = []
   
    for (let pointer = 0; pointer < ${length}; pointer++) {

        feature = {position: new google.maps(${latitude}, ${longitude}),
            type: "routeLine",
        }
        allFeatures.push(feature)
    }


    var features = [
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "routeLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "drivePosition",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "snapLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "drivePosition",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "routeLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "snapLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "routeLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "drivePosition",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "snapLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "drivePosition",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "routeLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "snapLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "routeLine",
        },
        {
            position: new google.maps.LatLng(${latitude}, ${longitude}),
            type: "drivePosition",
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

