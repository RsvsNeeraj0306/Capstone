import React, { useEffect, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Button, Space, Typography } from "antd";
import axios from "axios";
import { Link } from "react-router-dom";

function LessThanZeroDays() {
  const [softwareList, setSoftwareList] = useState([]);
  const [isEmailSent, setIsEmailSent] = useState(false);

  useEffect(() => {
    // Fetch software data from your API or backend here
    axios.get('http://localhost:8080/api/allSoftware')
      .then((response) => {
        setSoftwareList(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const calculateDaysLeft = (expiryDate) => {
    const currentDate = new Date();
    const expiry = new Date(expiryDate);
    const timeDifference = expiry - currentDate;
    const daysLeft = Math.floor(timeDifference / (1000 * 60 * 60 * 24)); // Convert milliseconds to days
    return daysLeft;
  };

  // Function to handle the "Send Mail" button click
  const handleSendMailClick = () => {
    // Make an API request to your Gmail controller to send the mail
    axios.get('http://localhost:8080/api/sendMail')
      .then((response) => {
        // Handle the response as needed (e.g., show a success message)
        setIsEmailSent(true); // Set the email sent state
      })
      .catch((error) => {
        // Handle errors
        console.error(error);
      });
  };

  let serialNumber = 1;

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Software Devices</Typography.Title>
      {softwareList.length > 0 ? (
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
                <TableCell>Actions</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {softwareList.map((software) => {
                const daysLeft = calculateDaysLeft(software.expiryDate);
                if (daysLeft <= 0) {
                  return (
                    <TableRow key={software.id}>
                      <TableCell>{serialNumber++}</TableCell>
                      <TableCell>{software.id}</TableCell>
                      <TableCell>{software.softwareName}</TableCell>
                      <TableCell>{software.typeOfPlan}</TableCell>
                      <TableCell>{software.manufacturer.name}</TableCell>
                      <TableCell>{software.quantity}</TableCell>
                      <TableCell>{daysLeft < 0 ? "Expired" : daysLeft}</TableCell>
                      <TableCell>
                        <Link to="/SoftwareDevices">
                          <Button type="primary" style={{ marginRight: "10px" }}>
                            Update
                          </Button>
                        </Link>
                        <Button type="primary" onClick={handleSendMailClick}>
                          Send Mail
                        </Button>
                      </TableCell>
                    </TableRow>
                  );
                }
                return null;
              })}
            </TableBody>
          </Table>
        </TableContainer>
      ) : (
        <Typography.Title level={2}>Empty</Typography.Title>
      )}
      {isEmailSent && <div>Email has been sent!</div>}
    </Space>
  );
}

export default LessThanZeroDays;
