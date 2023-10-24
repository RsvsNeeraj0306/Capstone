import React, { useState, useEffect } from 'react';
import { Space, Typography, Tabs } from "antd";
import { Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import ManufacturerForm from './ManufacturerForm';
import UpdateManufacturerForm from './UpdateManufacturerForm';


const Manufacturer = () => {
  const [manufacturers, setManufacturers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('manufacturer');
  const [selectedManufacturerId, setSelectedManufacturerId] = useState(null);

  const handleChangeTab = (key) => {
    setActiveTab(key);
  };

  const handleUpdateManufacturer = (id) => {
    setSelectedManufacturerId(id);
    setActiveTab('updateManufacturerForm');
  };

  useEffect(() => {
    // Fetch the manufacturer data from your API
    fetch('http://localhost:8080/api/allManufacture')
      .then((response) => response.json())
      .then((data) => {
        setManufacturers(data);
        setLoading(false);
      })
      .catch((error) => console.error('Error fetching manufacturers: ', error));
  }, []);

  let serialNumber = 1;

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4} className="manufacturer-title">Manufacture details</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab} className="manufacturer-tabs">
        <Tab key="manufacturer" tab="Total Manufacturer" />
        <Tab key="manufacturerForm" tab="Add Manufacturer" />
        <Tab key="updateManufacturerForm" tab="Update Manufacturer" />
      </Tabs>
      {activeTab === 'manufacturer' && (
        <TableContainer sx={{ maxHeight: 440 }} className="manufacturer-table-container">
          <Table stickyHeader aria-label="sticky table" className="manufacturer-table">
            <TableHead>
              <TableRow>
                <TableCell>Serial Number</TableCell>
                <TableCell>Manufacturer ID</TableCell>
                <TableCell>Manufacturer Name</TableCell>
                <TableCell>Field of Work</TableCell>
                <TableCell>Company Website Link</TableCell>
                <TableCell>Email ID</TableCell>
                <TableCell>Action</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {manufacturers.map((manufacturer) => (
                <TableRow key={manufacturer.id}>
                  <TableCell>{serialNumber++}</TableCell>
                  <TableCell>{manufacturer.id}</TableCell>
                  <TableCell>{manufacturer.name}</TableCell>
                  <TableCell>{manufacturer.fieldOfWork}</TableCell>
                  <TableCell>{manufacturer.companyWebsiteLink}</TableCell>
                  <TableCell>{manufacturer.emailId}</TableCell>
                  <TableCell>
                    <button variant="contained" color="primary"
                    onClick={() => handleUpdateManufacturer(manufacturer.id)} className="manufacturer-update-button">
                      Update
                    </button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
      {activeTab === 'manufacturerForm' && <ManufacturerForm />}
      {activeTab === 'updateManufacturerForm' && <UpdateManufacturerForm selectedManufacturerId={selectedManufacturerId} />}
    </Space>
  );
};

export default Manufacturer;