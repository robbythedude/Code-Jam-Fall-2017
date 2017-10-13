/**
 *  NP Web Service
 *
 *  Copyright 2017 M
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "NP Web Service",
    namespace: "npwebservice",
    author: "M",
    description: "web service to query smart things devices",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Allow external service to control these things...") {
		input "switches", "capability.switch", multiple: true, required: true
        input "sensors", "capability.contactSensor", required: true
	}
}

mappings {
  path("/switches") {
    action: [
      GET: "listSwitches"
    ]
  }
  path("/switches/:command") {
    action: [
      PUT: "updateSwitches"
    ]
  }
  path("/devices"){
  	action: [
    	GET: "listSensors"
    ]
  }
}

def listSensors(){
	def resp = []
        
  sensors.each {
           resp << [name: it.displayName, status: it.currentValue("contact"), temperature: it.currentValue("temperature"),
           manufacturer: it.getManufacturerName(), lastEventDate: it.getLastActivity()]
         }
         
    switches.each {
      resp << [name: it.displayName, status: it.currentValue("switch"), power: it.currentValue("power"), 
      manufacturer: it.getManufacturerName(), lastEventDate: it.getLastActivity()]
      log.debug "${switches.ping()}"
    }
          
   //sensors.each{
   	//	def evts = it.events()
      //  evts.each{
        //	resp << [name: it.getDescription()]
        //}
   //}
   
	//def atts = sensors.supportedAttributes
    
    //atts.each{
    //	resp << [name: it.name]
   // }
     
    return resp
}

def listSwitches() {
     def resp = []
   // switches.each {
     // resp << [name: it.displayName, status: it.currentValue("switch"), power: it.currentValue("power"), 
      //manufacturer: it.getManufacturerName(), lastEventDate: it.getLastActivity()]
      //log.debug "${switches.ping()}"
    //}
    
    switches.each {
        def supportedCaps = it.capabilities
        supportedCaps.each {cap ->
            log.debug "Capability name: ${cap.name}"
            cap.commands.each {comm ->
                log.debug "-- Command name: ${comm.name}"
            }
		}
        supportedCaps.each {cap ->
            log.debug "Capability name: ${cap.name}"
            cap.attributes.each {atr ->
                log.debug "-- attribute name: ${atr.name}"
            }
		}
    }
    
    def supportedCommands = switches.supportedCommands

    // logs each command's arguments
    supportedCommands.each {
        log.debug "arguments for swithLevel command ${it.name}: ${it.arguments}"
    }
    
    def atts = sensors.supportedAttributes
    
    atts.each{
    	resp << [name: it.name]
    }   
    
    return resp
}


void updateSwitches() {
    // use the built-in request object to get the command parameter
    def command = params.command

    // all switches have the command
    // execute the command on all switches
    // (note we can do this on the array - the command will be invoked on every element
    switch(command) {
        case "on":
            switches.on()
            break
        case "off":
            switches.off()
            break
        default:
            httpError(400, "$command is not a valid command for all switches specified")
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(sensors, "contact.open", motionDetectedHandler)
    subscribe(sensors, "contact.closed", motionStoppedHandler)
}

def motionDetectedHandler(evt) {
	log.debug "motion detected : $evt"
}

def motionStoppedHandler(evt) {
    log.debug "motionStoppedHandler called: $evt"
}


// TODO: implement event handlers