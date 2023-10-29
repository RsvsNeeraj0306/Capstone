import { Space, Typography,Tabs, Button } from "antd";
import { useEffect, useState } from "react";
import {Tab, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, paper, TablePagination, Paper } from "@mui/material";
import axios from "axios";
import AutoGraphIcon from '@mui/icons-material/AutoGraph';
import BuildIcon from '@mui/icons-material/Build';
import { DeleteOutlined } from '@ant-design/icons';



import NetworkDeviceForm from './NetworkDeviceForm';
import { Link } from 'react-router-dom';
import { toast } from "react-toastify";

function NetworkDevices() {
  const [NetworkDeviceList, setHardwareList] = useState([]);
  const [activeTab, setActiveTab] = useState('NetworkDevices');

  const handleChangeTab = (key) => {
    setActiveTab(key);
  };

  const containerStyle = {
    padding: '20px',
    backgroundColor: '#f0f0f0',
  };

  useEffect(() => {
    axios
      .get('http://localhost:8080/api/allHardware', {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
      })
      .then((response) => {
        setHardwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const handleDelete = (id) => {
    axios
      .delete(`http://localhost:8080/api/deleteNetworkDevice/${id}`, {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token'),
        },
      })
      .then((response) => {
        if (response.status !== 204) {
          toast.success('Network device deleted successfully');
          setHardwareList((prevList) => prevList.filter((device) => device.id !== id));
        } else {
          toast.error('Failed to delete network device');
        }
      })
      .catch((error) => {
        console.error(error);
        toast.error('Error deleting network device');
      });
  };

  let serialNumber = 1;

  return (
    <div style={containerStyle}>
      <Typography.Title level={4}>Network Devices</Typography.Title>
      <Tabs activeKey={activeTab} onChange={handleChangeTab}>
        <Tab key="NetworkDevices" tab="Total Network Devices" />
        <Tab key="NetworkDeviceForm" tab="Add Network Device" />
      </Tabs>
      {activeTab === 'NetworkDevices' && (
        <TableContainer sx={{ maxHeight: 440 }}>
          <Table stickyHeader aria-label="sticky table">
            <TableHead>
              <TableRow>
                <TableCell>Serial Number</TableCell>
                <TableCell>Device ID</TableCell>
                <TableCell>Device Name</TableCell>
                <TableCell>Purchase Date</TableCell>
                <TableCell>Asset Number</TableCell>
                <TableCell>Warranty Period</TableCell>
                <TableCell>Manufacturer Name</TableCell>
                <TableCell>Analyze</TableCell>
                <TableCell>RMA</TableCell>
                <TableCell>Delete</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {NetworkDeviceList.map((NetworkDevice) => (
                <TableRow key={NetworkDevice.id}>
                  <TableCell>{serialNumber++}</TableCell>
                  <TableCell>{NetworkDevice.id}</TableCell>
                  <TableCell>{NetworkDevice.hardwareName}</TableCell>
                  <TableCell>{NetworkDevice.purchaseDate}</TableCell>
                  <TableCell>{NetworkDevice.serialNumber}</TableCell>
                  <TableCell>{NetworkDevice.warrantyEndDate}</TableCell>
                  <TableCell>{NetworkDevice.manufacturer.name}</TableCell>
                  <TableCell>
                    <Link to="/NetworkDeviceAnalysisForm">
                      <Button type="primary" style={{ marginRight: '10px' }}>
                        <AutoGraphIcon />
                      </Button>
                    </Link>
                  </TableCell>
                  <TableCell>
                    <Link to="/NetworkDeviceRMAForm">
                      <Button type="primary" style={{ marginRight: '10px' }}>
                        <BuildIcon />
                      </Button>
                    </Link>
                  </TableCell>
                  <TableCell>
                    <Button
                      type="primary"
                      onClick={() => handleDelete(NetworkDevice.id)}
                      danger
                    >
                      <DeleteOutlined />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>
      )}
      {activeTab === 'NetworkDeviceForm' && <NetworkDeviceForm />}
    </div>
  );
}

export default NetworkDevices;
