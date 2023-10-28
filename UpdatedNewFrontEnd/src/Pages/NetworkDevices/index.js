import { Space, Typography,Tabs, Button } from "antd";
import { useEffect, useState } from "react";
import {Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, paper, TablePagination, Paper } from "@mui/material";
import axios from "axios";
import AutoGraphIcon from '@mui/icons-material/AutoGraph';
import BuildIcon from '@mui/icons-material/Build';



import NetworkDeviceForm from "./NetworkDeviceForm";
import { Link } from "react-router-dom";

function NetworkDevices() {
  const [NetworkDeviceList, setHardwareList] = useState([]);
  const [activeTab, setActiveTab] = useState("NetworkDevices");

  const handleChangeTab = (key) => {
    setActiveTab(key);
  }

  const containerStyle = {
    padding: '20px', // Add padding to the container
    backgroundColor: '#f0f0f0', // Add a background color
  };

  useEffect(() => {
    // Fetch hardware data from your API or backend here
    axios.get('http://localhost:8080/api/allHardware',{headers: {
      'Authorization': 'Bearer ' + localStorage.getItem('token')
    }})
      .then((response) => {
        setHardwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  let serialNumber = 1; // Initialize the serial number

  return (
    <div style={containerStyle}>

    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Network Devices</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tab key="NetworkDevices" tab="Total Network Devices" />
        <Tab key="NetworkDeviceForm" tab="Add Network Device" />
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
              <TableCell>Manufacturer Name</TableCell>
              <TableCell>Analyse</TableCell>
              <TableCell>RMA</TableCell>
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
                <TableCell>
                  <Link to="/NetworkDeviceAnalysisForm">
                    <Button type="primary" style={{ marginRight: "10px" }}>
                     <AutoGraphIcon />
                    </Button>
                  </Link>
                </TableCell>
                <TableCell>
                  <Link to="/NetworkDeviceRMAForm">
                    <Button type="primary" style={{ marginRight: "10px" }}>
                      <BuildIcon />
                    </Button>
                  </Link>
                </TableCell>
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
    </div>
  );
}

export default NetworkDevices;
