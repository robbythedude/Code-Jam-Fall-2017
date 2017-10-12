/**
 *  NPAnalytics
 *
 *  Copyright 2017 Team SmartThings
 *
 */
definition(
    name: "NPAnalytics",
    namespace: "npanalytics",
    author: "Team SmartThings",
    description: "Data monitoring of IoT devices in the home",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
	section("Turn on this power switch") {
		input "theSwitch", "capability.switch", required: true 
	}
    section("Turn on when motion detected:") {
        input "theSensor", "capability.contactSensor", required: true
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
	subscribe(theSensor, "contact.open", motionDetectedHandler)
    subscribe(theSensor, "contact.closed", motionStoppedHandler)
}

def motionDetectedHandler(evt) {
	log.debug "motion detected : $evt"
    theSwitch.on()
}

def motionStoppedHandler(evt) {
    log.debug "motionStoppedHandler called: $evt"
    theSwitch.off()
}

// TODO: implement event handlers