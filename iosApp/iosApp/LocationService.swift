//
//  LocationService.swift
//  iosApp
//
//  Created by admin on 02/06/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import CoreLocation
class LocationService:NSObject,CLLocationManagerDelegate {
    private let locationManager = CLLocationManager()
    
    private var completion:((Result<CLLocationCoordinate2D,Error>) -> Void)?
    
    override init() {
        super.init()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }
    
    func requestLocation(completion: @escaping (Result<CLLocationCoordinate2D, Error>)-> Void) {
        self.completion = completion
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if let location = locations.first {
            locationManager.startUpdatingLocation()
            completion?(.success(location.coordinate))
            completion = nil
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        locationManager.stopUpdatingLocation()
        completion?(.failure(error))
        completion = nil
        
    }
    
    
}
