import React, { useEffect, useState } from 'react';
import { Space, Typography, Tabs} from "antd";
import { Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material"; // Import Tabs and Tab from Material-UI



import ManufacturerForm from './ManufacturerForm';


const Manufacturer = () => {
  const [manufacturers, setManufacturers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('manufacturer');

  const handleChangeTab = (key) => {
    setActiveTab(key);
  }

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

  let serialNumber = 1; // Initialize the serial number

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Manufacture details</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tab key="manufacturer" tab="Total Manufacturer" />
        <Tab key="manufacturerForm" tab="Add Manufacturer" />
      </Tabs>
      {activeTab === "manufacturer" && (
         <TableContainer sx={{ maxHeight: 440 }}>
         <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell>Serial Number</TableCell>
                <TableCell>Manufacturer ID</TableCell>
                <TableCell>Manufacturer Name</TableCell>
                <TableCell>Field of Work</TableCell>
                <TableCell>Company Website Link</TableCell>
                <TableCell>Email ID</TableCell>
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
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
      {activeTab === "manufacturerForm" && <ManufacturerForm />}
    </Space>
  );
};

export default Manufacturer;
