import { Space, Typography } from "antd";
import { useEffect, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import axios from "axios";

function LessThanZeroDays() {
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

  // Function to calculate days left until the expiry date
  const calculateDaysLeft = (expiryDate) => {
    const currentDate = new Date();
    const expiry = new Date(expiryDate);
    const timeDifference = expiry - currentDate;
    const daysLeft = Math.floor(timeDifference / (1000 * 60 * 60 * 24)); // Convert milliseconds to days
    return daysLeft;
  };

  let serialNumber = 1; // Initialize the serial number

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Software Devices</Typography.Title>
      {softwareList.length > 0 ? (
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
                <TableCell>Days Left</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {softwareList.map((software) => {
                const daysLeft = calculateDaysLeft(software.expiryDate);
                // Show software if days left is less than 0
                if (daysLeft < 0) {
                  return (
                    <TableRow key={software.id}>
                      <TableCell>{serialNumber++}</TableCell>
                      <TableCell>{software.id}</TableCell>
                      <TableCell>{software.softwareName}</TableCell>
                      <TableCell>{software.typeOfPlan}</TableCell>
                      <TableCell>{software.manufacturer.name}</TableCell>
                      <TableCell>{software.usersCanUse}</TableCell>
                      <TableCell>{daysLeft < 0 ? "Expired" : daysLeft}</TableCell>
                    </TableRow>
                  );
                }
                return null; // Skip this software
              })}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography.Title level={2}>Empty</Typography.Title>
      )}
    </Space>
  );
}

export default LessThanZeroDays;
