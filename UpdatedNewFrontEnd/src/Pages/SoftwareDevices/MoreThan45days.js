import { Space, Typography } from "antd";
import { useEffect, useState } from "react";
import { Button, Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import axios from "axios";
import { Link } from "react-router-dom";

function MoreThan45Days() {
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
      <TableContainer sx={{ maxHeight: 440 }}>
       <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              <TableCell>Serial Number</TableCell>
              <TableCell>ID</TableCell>
              <TableCell>Software Name</TableCell>
              <TableCell>Type of Plan</TableCell>
              <TableCell>Publisher Name</TableCell>
              <TableCell>Users Can Use</TableCell>
              <TableCell>Days Left</TableCell>
              <TableCell>Action</TableCell> {/* New column for Action button */}
            </TableRow>
          </TableHead>
          <TableBody>
            {softwareList.map((software) => {
              const daysLeft = calculateDaysLeft(software.expiryDate);
              if (daysLeft > 45) {
                return (
                  <TableRow key={software.id}>
                    <TableCell>{serialNumber++}</TableCell>
                    <TableCell>{software.id}</TableCell>
                    <TableCell>{software.softwareName}</TableCell>
                    <TableCell>{software.typeOfPlan}</TableCell>
                    <TableCell>{software.manufacturer.name}</TableCell>
                    <TableCell>{software.quantity}</TableCell>
                    <TableCell>{daysLeft}</TableCell>
                    <TableCell>
                      <Link to="/SoftwareChangePlanForm">
                        <Button variant="contained" color="primary">
                          Update
                        </Button>
                      </Link>
                    </TableCell>
                  </TableRow>
                );
              }
              return null; // Skip this software
            })}
          </TableBody>
        </Table>
      </TableContainer>
    </Space>
  );
}

export default MoreThan45Days;