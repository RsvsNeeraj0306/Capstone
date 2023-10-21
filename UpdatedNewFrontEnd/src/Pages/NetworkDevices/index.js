import { Space, Typography,Tabs } from "antd";
import { useEffect, useState } from "react";
import {Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, paper, TablePagination, Paper } from "@mui/material";
import axios from "axios";




import NetworkDeviceForm from "./NetworkDeviceForm";

function NetworkDevices() {
  const [NetworkDeviceList, setHardwareList] = useState([]);
  const [activeTab, setActiveTab] = useState("NetworkDevices");

  const handleChangeTab = (key) => {
    setActiveTab(key);
  }

  useEffect(() => {
    // Fetch hardware data from your API or backend here
    axios.get('http://localhost:8080/api/allHardware')
      .then((response) => {
        setHardwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  let serialNumber = 1; // Initialize the serial number

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Hardware Devices</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tab key="NetworkDevices" tab="Total Hardware" />
        <Tab key="NetworkDeviceForm" tab="Add Hardware" />
      </Tabs>
      {activeTab === "NetworkDevices" && (
       <TableContainer sx={{ maxHeight: 440 }}>
       <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
            <TableCell>Serial Number</TableCell>
              <TableCell>Device Name</TableCell>
              <TableCell>Purchase Date</TableCell>
              <TableCell>Warranty Period</TableCell>
              <TableCell>Manufacturer ID</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {NetworkDeviceList.map((NetworkDevice) => (
              <TableRow key={NetworkDevice.id}>
                <TableCell>{serialNumber++}</TableCell>
                <TableCell>{NetworkDevice.hardwareName}</TableCell>
                <TableCell>{NetworkDevice.purchaseDate}</TableCell>
                <TableCell>{NetworkDevice.warrantyEndDate}</TableCell>
                <TableCell>{NetworkDevice.manufacturer.name}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      
      )}
      {activeTab === "NetworkDeviceForm" && (
        <NetworkDeviceForm />
      )}
    </Space>
  );
}

export default NetworkDevices;
