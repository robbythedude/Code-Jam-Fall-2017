'use strict';

const Hapi = require('hapi');
var jsonfile = require('jsonfile');
var unirest = require('unirest');

// Create a server with a host and port
const server = new Hapi.Server();
var url = "https://graph.api.smartthings.com/api/smartapps/installations/e863e435-1c5b-44bb-92f6-8b55da0d4e8c/";
server.connection({ 
    port: process.env.PORT || 8081
});

// Add the route
server.route({
    method : 'POST',
    path : '/{policyNumber}/devices/{deviceId}/events',
    handler : function(request, reply){
        var policyNumber = encodeURIComponent(request.params.policyNumber);
        var deviceId = encodeURIComponent(request.params.deviceId);
        var request = request.payload;

        var event = {
            policyNumber : policyNumber,
            deviceId : deviceId,
            device : request
        };
        var file = "events.json";
        var events = jsonfile.readFileSync(file);

        events.events.push(event);

        jsonfile.writeFile(file, events,function(err){
            return reply();
        });
    }
});

server.route({
    method : 'GET',
    path : '/{policyNumber}/devices/{deviceId}/events',
    handler : function(request, reply){
        var policyNumber = encodeURIComponent(request.params.policyNumber);
        var deviceId = encodeURIComponent(request.params.deviceId);
        var file = "events.json";
        var events = jsonfile.readFileSync(file);
        var returnEvents = [];
        for(var x = 0; x < events.events.length; x++){
            if(deviceId.trim().toLowerCase() == events.events[x].deviceId.toLowerCase().trim()){
                
                returnEvents.push(events.events[x]);
            }
        }
        return reply(returnEvents);
    }
});

server.route({
    method: 'GET',
    path:'/{policyNumber}/devices', 
    handler: function (request, reply) {
        var name = encodeURIComponent(request.params.policyNumber);
        unirest.get(url + "devices")
            .headers({'Authorization': 'Bearer cf1978fe-ce08-4b84-a53a-f9f76ca1cf8d'})
            .send()
            .end(function (response) {
                var file = "devices.json";
                var devices = [];

                for(var x = 0; x < response.body.length; x++){
                    devices.push(response.body[x]);
                }
                jsonfile.writeFile(file, devices);

                return reply(response.body);
            });
        }
}); 

// Start the server
server.start((err) => {

    if (err) {
        throw err;
    }
    console.log('Server running at:', server.info.uri);
});