package com.first.capstone.services;

import java.util.List;
import java.util.Optional;


import org.springframework.stereotype.Service;

import com.first.capstone.entity.Location;
import com.first.capstone.respositories.LocationRepository;


@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location getOrCreateLocation(Location location) {
        Optional<Location> existingLocation = locationRepository.findByLocationName(location.getLocationName());

        if (existingLocation.isPresent()) {
            return existingLocation.get();
        } else {
    
            Location newLocation = new Location();
            newLocation.setLocationName(location.getLocationName());

            // Save the new location to the database
            return locationRepository.save(newLocation);
        }
    }

    // Implement other service methods as needed
}
