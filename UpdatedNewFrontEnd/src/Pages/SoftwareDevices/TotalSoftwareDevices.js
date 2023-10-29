import React, { useEffect, useRef, useState } from "react";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow } from "@mui/material";
import { Button, Space, Typography, Modal } from "antd";
import axios from "axios";
import { Link } from "react-router-dom";
import AutoGraphIcon from '@mui/icons-material/AutoGraph';
import DeleteIcon from '@mui/icons-material/Delete';

function TotalSoftwareDevices() {
  const [softwareList, setSoftwareList] = useState([]);
  const hasMounted = useRef(false);
  const [softwareToDelete, setSoftwareToDelete] = useState(null);
  const [isSoftwareFormVisible, setIsSoftwareFormVisible] = useState(false);
  const [deleteConfirmationVisible, setDeleteConfirmationVisible] = useState(false);

  useEffect(() => {
    if (!hasMounted.current) {
      axios.get('http://localhost:8080/api/allSoftware', {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      })
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

  const handleDeleteClick = (softwareId) => {
    setSoftwareToDelete(softwareId);
    setDeleteConfirmationVisible(true);
  };

  const handleDeleteConfirm = () => {
    if (softwareToDelete !== null) {
      axios.delete(`http://localhost:8080/api/deleteSoftware/${softwareToDelete}`, {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      })
        .then(() => {
          setSoftwareList((prevSoftwareList) =>
            prevSoftwareList.filter((software) => software.id !== softwareToDelete)
          );
        })
        .catch((error) => {
          console.error(error);
        });
    }
    setDeleteConfirmationVisible(false);
  };

  const handleDeleteCancel = () => {
    setDeleteConfirmationVisible(false);
  };

  let serialNumber = 1;

  const containerStyle = {
    padding: '20px',
    backgroundColor: '#f0f0f0',
  };

  return (
    <div style={containerStyle}>
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
                <TableCell>Analyse</TableCell>
                <TableCell>Delete</TableCell>
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
                       <AutoGraphIcon />
                      </Button>
                    </Link>
                  </TableCell>
                  <TableCell>
                    <Button type="primary" onClick={() => handleDeleteClick(software.id)}>
                      <DeleteIcon />
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </TableContainer>

        {/* Confirmation Modal */}
        <Modal
          title="Confirm Deletion"
          visible={deleteConfirmationVisible}
          onOk={handleDeleteConfirm}
          onCancel={handleDeleteCancel}
        >
          <p>Are you sure you want to delete this software?</p>
        </Modal>
    </div>
  );
}

export default TotalSoftwareDevices;
