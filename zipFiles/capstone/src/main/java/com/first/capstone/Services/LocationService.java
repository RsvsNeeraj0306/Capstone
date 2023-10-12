package com.first.capstone.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.first.capstone.Entity.Location;
import com.first.capstone.Respositories.LocationRepository;
import com.first.capstone.dto.LocationDTO;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
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
