import { Space, Typography } from "antd";
import { useEffect, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import axios from "axios";

function TotalSoftwareDevices() {
  const [softwareList, setSoftwareList] = useState([]);

  useEffect(() => {
    // Fetch software data from your API or backend here
    axios.get('http://localhost:8080/api/allSoftware')  // Adjust the API endpoint as needed
      .then((response) => {
        setSoftwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  let serialNumber = 1; // Initialize the serial number

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Software Devices</Typography.Title>
      <TableContainer>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Serial Number</TableCell>
              <TableCell>ID</TableCell>
              <TableCell>Software Name</TableCell>
              <TableCell>Type of Plan</TableCell>
              <TableCell>Manufacturer Name</TableCell>
              <TableCell>Users Can Use</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {softwareList.map((software) => (
              <TableRow key={software.id}>
                <TableCell>{serialNumber++}</TableCell>
                <TableCell>{software.id}</TableCell>
                <TableCell>{software.softwareName}</TableCell>
                <TableCell>{software.typeOfPlan}</TableCell>
                <TableCell>{software.manufacturer.name}</TableCell>
                <TableCell>{software.usersCanUse}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Space>
  );
}

export default TotalSoftwareDevices;
