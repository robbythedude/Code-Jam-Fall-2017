//
//  NPFramework.swift
//  NPFramework
//
//  Created by Robert Steiner on 10/12/17.
//

import Foundation

enum DeviceEvent {
    case yes  //temp
}

enum DeviceState {
    case yes  //temp
}

protocol NPFrameWorkProtocol {
    func getAllDevices(policyNumber: Int, completionBlock: @escaping ([String]?) -> Void)
    func getDevicState(deviceID: String) -> DeviceState
    func getDeviceEvents(deviceID: String) -> [DeviceEvent]
}

class NPFrameWork : NPFrameWorkProtocol {
    
    static let sharedInstance = NPFrameWork()
    
    let endPointURL = "http://nphapi-env.mp7a3rmpmn.us-west-2.elasticbeanstalk.com/"
    
    init (){}
    
    func getAllDevices(policyNumber: Int, completionBlock: @escaping ([String]?) -> Void){
        var builtURL = NPFrameWork.sharedInstance.endPointURL + policyNumber.description + "/devices"
        makeGetCall(builtURL: builtURL){
            (output) in
            
            var collectionOfNames: [String] = []
            
            guard output != nil else {
                completionBlock(nil)
                return
            }
            
            if let array = output as? [Any]{
                for obj in array {
                    var name = (obj as AnyObject)["name"] as! String
                    collectionOfNames.append(name)
                }
                completionBlock(collectionOfNames)
            }
        }
    }
    func getDevicState(deviceID: String) -> DeviceState {
        return DeviceState.yes
    }
    func getDeviceEvents(deviceID: String) -> [DeviceEvent] {
        return [DeviceEvent.yes]
    }
    
    private func makeGetCall(builtURL: String, completionBlock: @escaping (Any?) -> Void) {
        
        // Set up the URL request
        guard let url = URL(string: builtURL) else {
            print("Error: cannot create URL")
            completionBlock(nil)
            return
        }
        
        let urlRequest = URLRequest(url: url)
        
        // set up the session
        let config = URLSessionConfiguration.default
        let session = URLSession(configuration: config)
        
        // make the request
        let task = session.dataTask(with: urlRequest) {
            (data, response, error) in
            // check for any errors
            
            guard error == nil else {
                print("error calling GET on \(builtURL)")
                completionBlock(nil)
                return
            }
            // make sure we got data
            guard let responseData = data else {
                print("Error: did not receive data")
                completionBlock(nil)
                return
            }
            // parse the result as JSON, since that's what the API provides
            do {
                guard let jsonOBJ1 = try JSONSerialization.jsonObject(with: responseData, options:[]) as? Any else {
                    print("error trying to convert data to JSON")
                    completionBlock(nil)
                    return
                }
                print(jsonOBJ1)
                completionBlock(jsonOBJ1)
                
            } catch  {
                print("error trying to convert data to JSON")
                completionBlock(nil)
                return
            }
        }
        
        task.resume()
    }
    
}
