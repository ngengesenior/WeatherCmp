//
//  LocationService.swift
//  iosApp
//
//  Created by admin on 02/06/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import CoreLocation
@objcMembers
class LocationService:NSObject,CLLocationManagerDelegate {
    private let locationManager = CLLocationManager()
    private let geocodingService = GeocodingService()
    private var completion:((Result<CLPlacemark,Error>) -> Void)?
    
    
    override init() {
        super.init()
        locationManager.delegate = self
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
    }
    
    func requestCurrentLocation(completion: @escaping (Result<CLPlacemark, Error>)-> Void) {
        self.completion = completion
        locationManager.requestWhenInUseAuthorization()
        locationManager.startUpdatingLocation()
    }
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if let location = locations.first {
            locationManager.startUpdatingLocation()
            geocodingService.reverseGeocodeLocation(location) {result in
                switch result {
                case .success(let placemark):self.completion?(.success(placemark))
                    
                case .failure(let error): self.completion?(.failure(error))
                }
                self.completion = nil
            
            }
        }
    }
    
    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        locationManager.stopUpdatingLocation()
        completion?(.failure(error))
        completion = nil
        
    }
    
    
}
