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
class GeocodingService {
    private let geocoder = CLGeocoder()
    
    func geocodeAddressString(_ address: String, completion: @escaping (Result<CLLocationCoordinate2D, Error>)-> Void) {
        geocoder.geocodeAddressString(address) { placemarks, error in
            if let error = error {
                completion(.failure(error))
            } else if let placemarks = placemarks, let placemark = placemarks.first,let location = placemark.location{
                completion(.success(location.coordinate))
            } else {
                completion(.failure(NSError(domain: "Error geocoding address:\(address)", code: -1,userInfo: [NSLocalizedDescriptionKey:"No matching location found"])))
            }
            
            
        }
    }
}
