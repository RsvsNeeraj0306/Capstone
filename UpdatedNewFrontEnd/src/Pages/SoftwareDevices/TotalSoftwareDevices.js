import React, { useEffect, useRef, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Button, Space, Typography, Modal } from "antd";
import axios from "axios";
import { Link } from "react-router-dom";
import SoftwareForm from './SoftwareForm'; // Import SoftwareForm

function TotalSoftwareDevices() {
  const [softwareList, setSoftwareList] = useState([]);
  const hasMounted = useRef(false);
  const [softwareToDelete, setSoftwareToDelete] = useState(null);
  const [isSoftwareFormVisible, setIsSoftwareFormVisible] = useState(false); // State variable to control the visibility of the form

  useEffect(() => {
    if (!hasMounted.current) {
      axios.get('http://localhost:8080/api/allSoftware',{headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }})
        .then((response) => {
          setSoftwareList(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
    hasMounted.current = true;
  }, []);

  const handleOpenSoftwareForm = () => {
    setIsSoftwareFormVisible(true);
  };



  let serialNumber = 1;

  return (
    <Space size={20} direction="vertical">
      <Typography.Title level={4}>Software Devices</Typography.Title>
      <Link to="/SoftwareForm">
        <Button type="primary" style={{ marginRight: "10px" }}>
          Add Software
        </Button>
      </Link>
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
              <TableCell>Actions</TableCell>
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
                <TableCell>{software.quantity}</TableCell>
                <TableCell>
                  <Link to="/SoftwareAnalysisForm">
                    <Button type="primary" style={{ marginRight: "10px" }}>
                      Analyse
                    </Button>
                  </Link>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Space>
  );
}

export default TotalSoftwareDevices;
