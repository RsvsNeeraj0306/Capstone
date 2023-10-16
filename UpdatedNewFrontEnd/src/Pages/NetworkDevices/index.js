import { Space, Typography } from "antd";
import { useEffect, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import axios from "axios";

function NetworkDevices() {
  const [NetworkDeviceList, setHardwareList] = useState([]);

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

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Hardware Devices</Typography.Title>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Device Name</TableCell>
              <TableCell>Purchase Date</TableCell>
              <TableCell>Warranty Period</TableCell>
              <TableCell>Serial Number</TableCell>
              <TableCell>Manufacturer Name</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {NetworkDeviceList.map((NetworkDevice) => (
              <TableRow key={NetworkDevice.id}>
                <TableCell>{NetworkDevice.hardwareName}</TableCell>
                <TableCell>{NetworkDevice.purchaseDate}</TableCell>
                <TableCell>{NetworkDevice.warrantyEndDate}</TableCell>
                <TableCell>{NetworkDevice.serialNumber}</TableCell>
                <TableCell>{NetworkDevice.manufacturer.name}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Space>
  );
}

export default NetworkDevices;
