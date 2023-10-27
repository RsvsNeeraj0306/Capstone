package com.first.capstone.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.first.capstone.dto.NetworkDeviceDTO;
import com.first.capstone.dto.ResponseDTO;
import com.first.capstone.entity.Manufacturer;
import com.first.capstone.entity.NetworkDevice;
import com.first.capstone.entity.NetworkDeviceAnalysis;
import com.first.capstone.entity.NetworkDeviceRMA;
import com.first.capstone.entity.NetworkDevicesHistory;
import com.first.capstone.respositories.NetworkDeviceAnalysisRepository;
import com.first.capstone.respositories.NetworkDeviceRMARepository;
import com.first.capstone.respositories.NetworkDeviceRepository;
import com.first.capstone.respositories.NetworkDevicesHistoryRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NetworkDeviceService {

    @Autowired
    private NetworkDeviceRepository networkDeviceRepository;

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private NetworkDevicesHistoryRepository networkDeviceHistoryRepository;

    @Autowired
    private NetworkDeviceAnalysisRepository networkDeviceAnalysisRepository;

    @Autowired
    private NetworkDeviceRMARepository networkDeviceRMARepository;

    private static final String ERROR_MESSAGE = "Network device not found";

    enum Action {
        ADD, UPDATE, DELETED, RMA, ANALYSIS
    }

    public NetworkDeviceService(NetworkDeviceRepository networkDeviceRepository,
            ManufacturerService manufacturerService, NetworkDevicesHistoryRepository networkDeviceHistoryRepository, NetworkDeviceAnalysisRepository networkDeviceAnalysisRepository, NetworkDeviceRMARepository networkDeviceRMARepository) {
        this.networkDeviceRepository = networkDeviceRepository;
        this.manufacturerService = manufacturerService;
        this.networkDeviceHistoryRepository = networkDeviceHistoryRepository;
        this.networkDeviceAnalysisRepository = networkDeviceAnalysisRepository;
        this.networkDeviceRMARepository = networkDeviceRMARepository;
    }

    public List<NetworkDevice> findAllNetworkDevices() {
        return networkDeviceRepository.findAll();
    }

    public NetworkDevice saveNetworkDevice(NetworkDevice networkDevice) {
        return networkDeviceRepository.save(networkDevice);
    }

    public NetworkDevice getOrCreaNetworkDevice(NetworkDevice networkDevice, Manufacturer manufacturer) {
        return networkDeviceRepository.findByIdAndHardwareName(manufacturer.getId(), networkDevice.getHardwareName())
                .orElseGet(() -> {
                    NetworkDevice newNetworkDevice = new NetworkDevice();
                    newNetworkDevice.setHardwareName(networkDevice.getHardwareName());
                    newNetworkDevice.setManufacturer(manufacturer);
                    newNetworkDevice.setPurchaseDate(networkDevice.getPurchaseDate());
                    newNetworkDevice.setSerialNumber(networkDevice.getSerialNumber());
                    newNetworkDevice.setWarrantyEndDate(networkDevice.getWarrantyEndDate());
                    newNetworkDevice.setLocation(networkDevice.getLocation());
                    newNetworkDevice.setQuantity(networkDevice.getQuantity());
                    newNetworkDevice.setCost(networkDevice.getCost());
                    return networkDeviceRepository.save(newNetworkDevice);
                });
    }

    public ResponseEntity<ResponseDTO> addNetworkDevice(@RequestBody NetworkDeviceDTO networkDeviceDTO) {
        Manufacturer manufacturer = manufacturerService.getOrCreateManufacturer(networkDeviceDTO.getManufacturer());

        NetworkDevice networkDevice = getOrCreaNetworkDevice(networkDeviceDTO.getNetworkDevice(), manufacturer);
        addNetworkDeviceHistory(networkDevice, Action.ADD.toString());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody("Network device added successfully");
        return ResponseEntity.ok().body(responseDTO);
    }

    public ResponseEntity<ResponseDTO> addNetworkDeviceHistory(@RequestBody NetworkDevice networkDevice,
            String action) {

        NetworkDevicesHistory networkDevicesHistory = new NetworkDevicesHistory();
        networkDevicesHistory.setDeviceName(networkDevice.getHardwareName());
        networkDevicesHistory.setPurchaseDate(networkDevice.getPurchaseDate());
        networkDevicesHistory.setWarrantyEndDate(networkDevice.getWarrantyEndDate());
        networkDevicesHistory.setSerialNumber(networkDevice.getSerialNumber());
        networkDevicesHistory.setCost(networkDevice.getCost());
        networkDevicesHistory.setQuantity(networkDevice.getQuantity());
        networkDevicesHistory.setAction(action);
        networkDevicesHistory.setDate(new java.sql.Date(System.currentTimeMillis()));

        networkDeviceHistoryRepository.save(networkDevicesHistory);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponseBody("Network device history added successfully");
        return ResponseEntity.ok().body(responseDTO);
    }

    public List<NetworkDevicesHistory> getNetworkDeviceHistory() {
        List<NetworkDevicesHistory> networkDevicesHistory = networkDeviceHistoryRepository.findAll();
        if (!networkDevicesHistory.isEmpty()) {
            return networkDevicesHistory;
        } else {
            return new ArrayList<>();
        }
    }

    public ResponseEntity<ResponseDTO> deleteNetworkDeviceById(Long id) {
        Optional<NetworkDevice> networkDevice = networkDeviceRepository.findById(id);
        if (networkDevice.isPresent()) {

            addNetworkDeviceHistory(networkDevice.get(), Action.DELETED.toString());
            networkDeviceRepository.deleteById(networkDevice.get().getId());

            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Network device deleted successfully");
            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody(ERROR_MESSAGE);
            return ResponseEntity.ok(responseDTO);
        }
    }

    public ResponseEntity<ResponseDTO> setNetworkDeviceAnalysis(NetworkDeviceDTO networkDeviceDTO) {
        Optional<NetworkDevice> networkDevice = networkDeviceRepository
                .findById(networkDeviceDTO.getNetworkDevice().getId());
        NetworkDeviceAnalysis networkDeviceAnalysis = networkDeviceDTO.getNetworkDeviceAnalysis();
        if (networkDevice.isPresent()) {
            NetworkDeviceAnalysis newNetworkDeviceAnalysis = new NetworkDeviceAnalysis();
            newNetworkDeviceAnalysis.setActiveDevice(networkDeviceAnalysis.getActiveDevice());
            newNetworkDeviceAnalysis.setAverageTimeUsage(networkDeviceAnalysis.getAverageTimeUsage());
            newNetworkDeviceAnalysis.setCompanyRating(networkDeviceAnalysis.getCompanyRating());
            newNetworkDeviceAnalysis.setNetworkDevice(networkDevice.get());
            networkDeviceAnalysisRepository.save(newNetworkDeviceAnalysis);
            addNetworkDeviceHistory(networkDevice.get(), Action.ANALYSIS.toString());
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody("Network device analysis added successfully");
            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setResponseBody(ERROR_MESSAGE);
            return ResponseEntity.ok(responseDTO);
        }
    }

    public ResponseEntity<NetworkDeviceRMA> setNetworkDeviceRMA(NetworkDeviceDTO networkDeviceDTO) {
        Optional<NetworkDevice> networkDevice = networkDeviceRepository
                .findById(networkDeviceDTO.getNetworkDevice().getId());
        NetworkDeviceRMA networkDeviceRMA = networkDeviceDTO.getNetworkDeviceRMA();
        if (networkDevice.isPresent()) {
            NetworkDeviceRMA newNetworkDeviceRMA = new NetworkDeviceRMA();
            newNetworkDeviceRMA.setActionType(networkDeviceRMA.getActionType());
            newNetworkDeviceRMA.setAmount(networkDeviceRMA.getAmount());
            newNetworkDeviceRMA.setDateOfAction(networkDeviceRMA.getDateOfAction());
            newNetworkDeviceRMA.setReason(networkDeviceRMA.getReason());
            newNetworkDeviceRMA.setNetworkDevice(networkDevice.get());
            networkDeviceRMARepository.save(newNetworkDeviceRMA);
            addNetworkDeviceHistory(networkDevice.get(), Action.RMA.toString());

            return ResponseEntity.ok(newNetworkDeviceRMA);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
