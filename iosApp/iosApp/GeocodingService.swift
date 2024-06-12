//
//  GeocodingService.swift
//  iosApp
//
//  Created by admin on 03/06/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import CoreLocation

@objcMembers
class GeocodingService:NSObject{
    private let geocoder = CLGeocoder()
    
    func geocodeAddressString(_ address: String, completion: @escaping (Result<CLPlacemark, Error>) -> Void) {
        geocoder.geocodeAddressString(address) { placemarks, error in
            if let error = error {
                completion(.failure(error))
            } else if let placemarks = placemarks, let placemark = placemarks.first {
                completion(.success(placemark))
            } else {
                let error = NSError(domain: "GeocodingErrorDomain", code: -1, userInfo: [NSLocalizedDescriptionKey: "No matching location found for address: \(address)"])
                completion(.failure(error))
            }
        }
    }
    
    func reverseGeocodeLocation(_ location: CLLocation, completion: @escaping (Result<CLPlacemark, Error>) -> Void) {
        geocoder.reverseGeocodeLocation(location) { placemarks, error in
            if let error = error {
                completion(.failure(error))
            } else if let placemarks = placemarks, let placemark = placemarks.first {
                completion(.success(placemark))
            } else {
                let error = NSError(domain: "GeocodingErrorDomain", code: -1, userInfo: [NSLocalizedDescriptionKey: "No matching placemark found for location: \(location)"])
                completion(.failure(error))
            }
        }
    }
}
